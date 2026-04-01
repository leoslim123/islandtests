package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Deer extends Herbivore {

    public Deer() {
        super("Deer");
    }

    @Override
    protected Animal createOffspring() {
        return new Deer();
    }

    @Override
    public String getEmoji() {
        return "🦌";
    }
}
