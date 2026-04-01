package com.island.island;

import com.island.entity.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Location {

    private final int x;
    private final int y;

    private final CopyOnWriteArrayList<Animal> animals = new CopyOnWriteArrayList<>();

    private final AtomicInteger plantCount = new AtomicInteger(0);

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public List<Animal> removeDeadAnimals() {
        List<Animal> dead = new ArrayList<>();
        animals.removeIf(a -> {
            if (!a.isAlive()) {
                dead.add(a);
                return true;
            }
            return false;
        });
        return dead;
    }

    public long getAnimalCount(String type) {
        return animals.stream().filter(a -> a.getType().equals(type) && a.isAlive()).count();
    }

    public int getPlantCount() {
        return plantCount.get();
    }

    public void growPlants(int amount, int max) {
        plantCount.updateAndGet(current -> Math.min(current + amount, max));
    }

    public void removePlant() {
        plantCount.updateAndGet(current -> Math.max(0, current - 1));
    }

    public void setPlantCount(int count) {
        plantCount.set(count);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
