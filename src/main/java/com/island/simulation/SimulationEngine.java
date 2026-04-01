package com.island.simulation;

import com.island.config.SimulationConfig;
import com.island.entity.Animal;
import com.island.island.Island;
import com.island.island.Location;
import com.island.statistics.StatisticsCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SimulationEngine {

    private final Island island;
    private final StatisticsCollector statistics;

    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(3);

    private final ExecutorService workerPool;

    public SimulationEngine(Island island, StatisticsCollector statistics) {
        this.island = island;
        this.statistics = statistics;
        int processors = Runtime.getRuntime().availableProcessors();
        this.workerPool = new ThreadPoolExecutor(
                processors, processors * 2,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }

    public void start() {
        long interval = SimulationConfig.TICK_INTERVAL_MS;

        scheduler.scheduleAtFixedRate(this::growPlants, interval, interval, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(this::runAnimalLifecycle, interval, interval, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(statistics::printStatistics, interval, interval, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduler.shutdownNow();
        workerPool.shutdownNow();
    }

    private void growPlants() {
        for (Location loc : island.getAllLocations()) {
            loc.growPlants(SimulationConfig.PLANTS_GROW_PER_TICK, SimulationConfig.MAX_PLANTS_PER_CELL);
        }
    }

    private void runAnimalLifecycle() {
        List<Location> locations = island.getAllLocations();
        List<Future<?>> futures = new ArrayList<>(locations.size());

        for (Location location : locations) {
            futures.add(workerPool.submit(() -> processLocation(location)));
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (ExecutionException e) {
                System.err.println("Ошибка при обработке локации: " + e.getMessage());
            }
        }
    }

    private void processLocation(Location location) {
        List<Animal> animals = new ArrayList<>(location.getAnimals());

        for (Animal animal : animals) {
            if (animal.isAlive()) {
                animal.eat(location, island);
            }
        }

        List<Animal> offspring = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.isAlive()) {
                Animal child = animal.reproduce(location);
                if (child != null) {
                    child.setPosition(location.getX(), location.getY());
                    offspring.add(child);
                }
            }
        }
        for (Animal child : offspring) {
            location.addAnimal(child);
        }

        for (Animal animal : animals) {
            if (animal.isAlive()) {
                animal.move(location, island);
            }
        }

        for (Animal animal : animals) {
            if (animal.isAlive()) {
                animal.starve();
            }
        }

        location.removeDeadAnimals();
    }
}
