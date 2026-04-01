package com.island.entity;

import com.island.config.SimulationConfig;
import com.island.island.Island;
import com.island.island.Location;

import java.util.List;

public abstract class Herbivore extends Animal {

    protected Herbivore(String type) {
        super(type);
    }

    @Override
    public void eat(Location location, Island island) {
        if (currentFood >= foodNeeded) return;
        int prob = SimulationConfig.getEatProbability(type, "Plant");
        if (prob > 0) {
            tryEatPlant(location, prob);
        }
    }

    @Override
    public Animal reproduce(Location location) {
        List<Animal> animals = location.getAnimals();
        long sameSpecies = animals.stream()
                .filter(a -> a != this && a.getType().equals(type) && a.isAlive())
                .count();
        if (sameSpecies >= 1 && location.getAnimalCount(type) < maxPerCell) {
            return createOffspring();
        }
        return null;
    }

    protected abstract Animal createOffspring();
}
