package com.island.entity;

import com.island.config.SimulationConfig;
import com.island.island.Island;
import com.island.island.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Animal {

    protected final String type;
    protected final double weight;
    protected final int maxPerCell;
    protected final int speed;
    protected final double foodNeeded;

    protected double currentFood;
    protected final AtomicBoolean alive = new AtomicBoolean(true);

    protected int x;
    protected int y;

    protected Animal(String type) {
        this.type = type;
        this.weight = SimulationConfig.getWeight(type);
        this.maxPerCell = SimulationConfig.getMaxPerCell(type);
        this.speed = SimulationConfig.getSpeed(type);
        this.foodNeeded = SimulationConfig.getFoodNeeded(type);
        this.currentFood = foodNeeded * 0.5;
    }

    public abstract void eat(Location location, Island island);

    public abstract Animal reproduce(Location location);

    public void move(Location currentLocation, Island island) {
        if (speed == 0) return;

        int steps = ThreadLocalRandom.current().nextInt(1, speed + 1);
        int newX = x;
        int newY = y;

        for (int i = 0; i < steps; i++) {
            int dir = ThreadLocalRandom.current().nextInt(4);
            int nx = newX;
            int ny = newY;
            switch (dir) {
                case 0 -> ny = newY - 1;
                case 1 -> ny = newY + 1;
                case 2 -> nx = newX - 1;
                case 3 -> nx = newX + 1;
            }
            if (nx >= 0 && nx < SimulationConfig.ISLAND_WIDTH &&
                ny >= 0 && ny < SimulationConfig.ISLAND_HEIGHT) {
                newX = nx;
                newY = ny;
            }
        }

        if (newX != x || newY != y) {
            Location target = island.getLocation(newX, newY);
            if (target.getAnimalCount(type) < maxPerCell) {
                currentLocation.removeAnimal(this);
                this.x = newX;
                this.y = newY;
                target.addAnimal(this);
            }
        }
    }

    public boolean starve() {
        currentFood -= foodNeeded * 0.25;
        if (currentFood < 0) {
            alive.set(false);
            return true;
        }
        return false;
    }

    protected double tryEat(Animal prey, int probability) {
        if (!prey.isAlive()) return 0;
        if (ThreadLocalRandom.current().nextInt(100) < probability) {
            prey.alive.set(false);
            double eaten = Math.min(prey.weight, foodNeeded - currentFood);
            currentFood = Math.min(currentFood + eaten, foodNeeded);
            return eaten;
        }
        return 0;
    }

    protected double tryEatPlant(Location location, int probability) {
        if (location.getPlantCount() == 0) return 0;
        if (ThreadLocalRandom.current().nextInt(100) < probability) {
            double plantWeight = SimulationConfig.getWeight("Plant");
            double eaten = Math.min(plantWeight, foodNeeded - currentFood);
            if (eaten > 0) {
                location.removePlant();
                currentFood = Math.min(currentFood + eaten, foodNeeded);
                return eaten;
            }
        }
        return 0;
    }

    public boolean isAlive() {
        return alive.get();
    }

    public void setAlive(boolean alive) {
        this.alive.set(alive);
    }

    public String getType() {
        return type;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract String getEmoji();
}
