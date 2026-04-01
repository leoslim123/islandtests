package com.island.entity.herbivores;

import com.island.config.SimulationConfig;
import com.island.entity.Animal;
import com.island.entity.Herbivore;
import com.island.island.Island;
import com.island.island.Location;

import java.util.List;

public class Boar extends Herbivore {

    public Boar() {
        super("Boar");
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
                if (eaten > 0 && currentFood >= foodNeeded) return;
            }
        }

        int plantProb = SimulationConfig.getEatProbability(type, "Plant");
        if (plantProb > 0) {
            tryEatPlant(location, plantProb);
        }
    }

    @Override
    protected Animal createOffspring() {
        return new Boar();
    }

    @Override
    public String getEmoji() {
        return "🐗";
    }
}
