package com.island.entity.predators;

import com.island.entity.Animal;
import com.island.entity.Predator;

public class Wolf extends Predator {

    public Wolf() {
        super("Wolf");
    }

    @Override
    protected Animal createOffspring() {
        return new Wolf();
    }

    @Override
    public String getEmoji() {
        return "🐺";
    }
}
