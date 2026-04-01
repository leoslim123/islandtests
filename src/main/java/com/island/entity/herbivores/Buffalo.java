package com.island.entity.herbivores;

import com.island.entity.Animal;
import com.island.entity.Herbivore;

public class Buffalo extends Herbivore {

    public Buffalo() {
        super("Buffalo");
    }

    @Override
    protected Animal createOffspring() {
        return new Buffalo();
    }

    @Override
    public String getEmoji() {
        return "🐃";
    }
}
