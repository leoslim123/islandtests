package com.island;

import com.island.config.SimulationConfig;
import com.island.entity.Animal;
import com.island.entity.predators.Wolf;
import com.island.entity.herbivores.Rabbit;
import com.island.island.Island;
import com.island.island.Location;
import com.island.statistics.StatisticsCollector;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FunctionalTest {

    @Test
    void testFullLifecycle() {
        Island island = new Island(SimulationConfig.ISLAND_WIDTH, SimulationConfig.ISLAND_HEIGHT);
        island.populate();

        long animalsBefore = 0;
        int plantsBefore = 0;
        for (Location loc : island.getAllLocations()) {
            animalsBefore += loc.getAnimals().size();
            plantsBefore += loc.getPlantCount();
        }
        assertTrue(animalsBefore > 0);
        assertTrue(plantsBefore > 0);

        for (Location loc : island.getAllLocations()) {
            List<Animal> animals = new ArrayList<>(loc.getAnimals());
            for (Animal a : animals) {
                if (a.isAlive()) a.eat(loc, island);
            }
            List<Animal> offspring = new ArrayList<>();
            for (Animal a : animals) {
                if (a.isAlive()) {
                    Animal child = a.reproduce(loc);
                    if (child != null) {
                        child.setPosition(loc.getX(), loc.getY());
                        offspring.add(child);
                    }
                }
            }
            for (Animal child : offspring) loc.addAnimal(child);
            for (Animal a : animals) {
                if (a.isAlive()) a.move(loc, island);
            }
            for (Animal a : animals) {
                if (a.isAlive()) a.starve();
            }
            loc.removeDeadAnimals();
        }

        long animalsAfter = 0;
        for (Location loc : island.getAllLocations()) {
            animalsAfter += loc.getAnimals().stream().filter(Animal::isAlive).count();
        }
        assertTrue(animalsAfter > 0);
    }

    @Test
    void testPredatorPreyEcosystem() {
        Island island = new Island(SimulationConfig.ISLAND_WIDTH, SimulationConfig.ISLAND_HEIGHT);
        Location loc = island.getLocation(1, 1);

        loc.setPlantCount(100);

        Wolf wolf = new Wolf();
        wolf.setPosition(1, 1);
        loc.addAnimal(wolf);

        int rabbitCount = 10;
        for (int i = 0; i < rabbitCount; i++) {
            Rabbit r = new Rabbit();
            r.setPosition(1, 1);
            loc.addAnimal(r);
        }

        assertEquals(11, loc.getAnimals().size());

        wolf.eat(loc, island);

        long aliveRabbits = loc.getAnimals().stream()
                .filter(a -> a.getType().equals("Rabbit") && a.isAlive())
                .count();
        assertTrue(aliveRabbits <= rabbitCount);
        assertTrue(wolf.isAlive());

        loc.removeDeadAnimals();
        long remaining = loc.getAnimals().stream()
                .filter(Animal::isAlive).count();
        assertTrue(remaining >= 1);
    }
}
