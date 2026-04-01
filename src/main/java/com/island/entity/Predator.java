package com.island.entity;

import com.island.config.SimulationConfig;
import com.island.island.Island;
import com.island.island.Location;

import java.util.List;

public abstract class Predator extends Animal {

    protected Predator(String type) {
        super(type);
    }

    @Override
    public void eat(Location location, Island island) {
        if (currentFood >= foodNeeded) return;

        List<Animal> animals = location.getAnimals();
        for (Animal prey : animals) {
            if (!prey.isAlive() || prey == this) continue;
            int prob = SimulationConfig.getEatProbability(type, prey.getType());
            if (prob > 0) {
                double eaten = tryEat(prey, prob);
                if (eaten > 0 && currentFood >= foodNeeded) break;
            }
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
