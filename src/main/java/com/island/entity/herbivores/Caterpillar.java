package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Caterpillar extends Herbivore {

    public Caterpillar() {
        super("Caterpillar");
    }

    @Override
    protected Animal createOffspring() {
        return new Caterpillar();
    }

    @Override
    public String getEmoji() {
        return "🐛";
    }
}
