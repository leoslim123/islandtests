package com.island.entity.predators;

import com.island.entity.Animal;
import com.island.entity.Predator;

public class Boa extends Predator {

    public Boa() {
        super("Boa");
    }

    @Override
    protected Animal createOffspring() {
        return new Boa();
    }

    @Override
    public String getEmoji() {
        return "🐍";
    }
}
