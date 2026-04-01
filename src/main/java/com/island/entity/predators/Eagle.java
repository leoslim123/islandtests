package com.island.entity.predators;

import com.island.entity.Animal;
import com.island.entity.Predator;

public class Eagle extends Predator {

    public Eagle() {
        super("Eagle");
    }

    @Override
    protected Animal createOffspring() {
        return new Eagle();
    }

    @Override
    public String getEmoji() {
        return "🦅";
    }
}
