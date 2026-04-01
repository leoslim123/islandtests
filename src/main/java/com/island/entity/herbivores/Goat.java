package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Goat extends Herbivore {

    public Goat() {
        super("Goat");
    }

    @Override
    protected Animal createOffspring() {
        return new Goat();
    }

    @Override
    public String getEmoji() {
        return "🐐";
    }
}
