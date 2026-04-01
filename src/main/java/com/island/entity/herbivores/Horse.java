package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Horse extends Herbivore {

    public Horse() {
        super("Horse");
    }

    @Override
    protected Animal createOffspring() {
        return new Horse();
    }

    @Override
    public String getEmoji() {
        return "🐴";
    }
}
