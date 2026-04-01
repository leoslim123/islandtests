package com.island.entity.predators;

import com.island.entity.Animal;
import com.island.entity.Predator;

public class Bear extends Predator {

    public Bear() {
        super("Bear");
    }

    @Override
    protected Animal createOffspring() {
        return new Bear();
    }

    @Override
    public String getEmoji() {
        return "🐻";
    }
}
