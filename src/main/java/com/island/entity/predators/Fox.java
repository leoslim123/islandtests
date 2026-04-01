package com.island.entity.predators;

import com.island.entity.Animal;
import com.island.entity.Predator;

public class Fox extends Predator {

    public Fox() {
        super("Fox");
    }

    @Override
    protected Animal createOffspring() {
        return new Fox();
    }

    @Override
    public String getEmoji() {
        return "🦊";
    }
}
