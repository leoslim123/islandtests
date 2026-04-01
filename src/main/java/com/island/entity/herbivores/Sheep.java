package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Sheep extends Herbivore {

    public Sheep() {
        super("Sheep");
    }

    @Override
    protected Animal createOffspring() {
        return new Sheep();
    }

    @Override
    public String getEmoji() {
        return "🐑";
    }
}
