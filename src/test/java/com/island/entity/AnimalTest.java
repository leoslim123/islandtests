package com.island.entity;

import com.island.entity.predators.Wolf;
import com.island.entity.predators.Bear;
import com.island.entity.herbivores.Rabbit;
import com.island.entity.herbivores.Caterpillar;
import com.island.island.Island;
import com.island.island.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testWolfCreation() {
        Wolf w = new Wolf();
        assertEquals("Wolf", w.getType());
        assertEquals(50.0, w.weight);
        assertEquals(30, w.maxPerCell);
        assertEquals(3, w.speed);
        assertEquals(8.0, w.foodNeeded);
        assertTrue(w.isAlive());
    }

    @Test
    void testInitialFood() {
        Wolf w = new Wolf();
        assertEquals(w.foodNeeded * 0.5, w.currentFood);
    }

    @Test
    void testSetPosition() {
        Rabbit r = new Rabbit();
        r.setPosition(7, 3);
        assertEquals(7, r.getX());
        assertEquals(3, r.getY());
    }

    @Test
    void testStarveReducesFood() {
        Wolf w = new Wolf();
        double before = w.currentFood;
        w.starve();
        assertTrue(w.currentFood < before);
    }

    @Test
    void testStarveKills() {
        Wolf w = new Wolf();
        for (int i = 0; i < 10; i++) {
            w.starve();
        }
        assertFalse(w.isAlive());
    }

    @Test
    void testSetAlive() {
        Rabbit r = new Rabbit();
        assertTrue(r.isAlive());
        r.setAlive(false);
        assertFalse(r.isAlive());
    }

    @Test
    void testEmoji() {
        assertEquals("🐺", new Wolf().getEmoji());
        assertEquals("🐇", new Rabbit().getEmoji());
        assertEquals("🐻", new Bear().getEmoji());
    }

    @Test
    void testHerbivoreEatPlant() {
        Location loc = new Location(0, 0);
        loc.setPlantCount(50);
        Island island = new Island(5, 5);
        Rabbit r = new Rabbit();
        r.currentFood = 0;
        r.eat(loc, island);
        assertTrue(loc.getPlantCount() <= 50);
    }

    @Test
    void testHerbivoreFullNoEat() {
        Location loc = new Location(0, 0);
        loc.setPlantCount(50);
        Island island = new Island(5, 5);
        Rabbit r = new Rabbit();
        r.currentFood = r.foodNeeded;
        r.eat(loc, island);
        assertEquals(50, loc.getPlantCount());
    }

    @Test
    void testReproduceNeedsMate() {
        Location loc = new Location(0, 0);
        Rabbit r1 = new Rabbit();
        loc.addAnimal(r1);
        assertNull(r1.reproduce(loc));
    }

    @Test
    void testReproduceWithMate() {
        Location loc = new Location(0, 0);
        Rabbit r1 = new Rabbit();
        Rabbit r2 = new Rabbit();
        loc.addAnimal(r1);
        loc.addAnimal(r2);
        Animal child = r1.reproduce(loc);
        assertNotNull(child);
        assertEquals("Rabbit", child.getType());
    }

    @Test
    void testCaterpillarNoMove() {
        Caterpillar c = new Caterpillar();
        assertEquals(0, c.speed);
    }
}
