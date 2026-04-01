package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Rabbit extends Herbivore {

    public Rabbit() {
        super("Rabbit");
    }

    @Override
    protected Animal createOffspring() {
        return new Rabbit();
    }

    @Override
    public String getEmoji() {
        return "🐇";
    }
}
