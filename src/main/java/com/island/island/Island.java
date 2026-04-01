package com.island.island;

import com.island.config.SimulationConfig;
import com.island.entity.Animal;
import com.island.entity.herbivores.*;
import com.island.entity.predators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Island {

    private final int width;
    private final int height;
    private final Location[][] locations;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location(x, y);
            }
        }
    }

    public void populate() {
        // Растения
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y].setPlantCount(SimulationConfig.INITIAL_PLANTS_PER_CELL);
            }
        }

        // Животные
        int count = SimulationConfig.INITIAL_ANIMALS_PER_SPECIES;
        spawnAnimals(() -> new Wolf(),        "Wolf",        count);
        spawnAnimals(() -> new Boa(),         "Boa",         count);
        spawnAnimals(() -> new Fox(),         "Fox",         count);
        spawnAnimals(() -> new Bear(),        "Bear",        Math.min(count, SimulationConfig.INITIAL_BEARS_MAX));
        spawnAnimals(() -> new Eagle(),       "Eagle",       count);
        spawnAnimals(() -> new Horse(),       "Horse",       count);
        spawnAnimals(() -> new Deer(),        "Deer",        count);
        spawnAnimals(() -> new Rabbit(),      "Rabbit",      count * 5);
        spawnAnimals(() -> new Mouse(),       "Mouse",       count * 5);
        spawnAnimals(() -> new Goat(),        "Goat",        count);
        spawnAnimals(() -> new Sheep(),       "Sheep",       count);
        spawnAnimals(() -> new Boar(),        "Boar",        count);
        spawnAnimals(() -> new Buffalo(),     "Buffalo",     count);
        spawnAnimals(() -> new Duck(),        "Duck",        count * 3);
        spawnAnimals(() -> new Caterpillar(), "Caterpillar", count * 10);
    }

    private void spawnAnimals(java.util.function.Supplier<Animal> factory, String type, int total) {
        int maxPerCell = SimulationConfig.getMaxPerCell(type);
        for (int i = 0; i < total; i++) {
            int x = ThreadLocalRandom.current().nextInt(width);
            int y = ThreadLocalRandom.current().nextInt(height);
            Location loc = locations[x][y];
            if (loc.getAnimalCount(type) < maxPerCell) {
                Animal animal = factory.get();
                animal.setPosition(x, y);
                loc.addAnimal(animal);
            }
        }
    }

    public Location getLocation(int x, int y) {
        return locations[x][y];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public List<Location> getAllLocations() {
        List<Location> all = new ArrayList<>(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                all.add(locations[x][y]);
            }
        }
        return all;
    }
}
