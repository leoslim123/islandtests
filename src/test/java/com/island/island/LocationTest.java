package com.island.island;

import com.island.entity.predators.Wolf;
import com.island.entity.herbivores.Rabbit;
import com.island.entity.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    Location loc;

    @BeforeEach
    void setUp() {
        loc = new Location(5, 10);
    }

    @Test
    void testCoordinates() {
        assertEquals(5, loc.getX());
        assertEquals(10, loc.getY());
    }

    @Test
    void testAddAndGetAnimals() {
        Wolf w = new Wolf();
        loc.addAnimal(w);
        assertEquals(1, loc.getAnimals().size());
        assertSame(w, loc.getAnimals().get(0));
    }

    @Test
    void testRemoveAnimal() {
        Rabbit r = new Rabbit();
        loc.addAnimal(r);
        assertTrue(loc.removeAnimal(r));
        assertEquals(0, loc.getAnimals().size());
    }

    @Test
    void testAnimalCountByType() {
        loc.addAnimal(new Wolf());
        loc.addAnimal(new Wolf());
        loc.addAnimal(new Rabbit());
        assertEquals(2, loc.getAnimalCount("Wolf"));
        assertEquals(1, loc.getAnimalCount("Rabbit"));
        assertEquals(0, loc.getAnimalCount("Bear"));
    }

    @Test
    void testPlantGrowth() {
        loc.setPlantCount(50);
        assertEquals(50, loc.getPlantCount());
        loc.growPlants(10, 200);
        assertEquals(60, loc.getPlantCount());
    }

    @Test
    void testPlantGrowthMax() {
        loc.setPlantCount(195);
        loc.growPlants(10, 200);
        assertEquals(200, loc.getPlantCount());
    }

    @Test
    void testRemovePlant() {
        loc.setPlantCount(5);
        loc.removePlant();
        assertEquals(4, loc.getPlantCount());
    }

    @Test
    void testRemovePlantZero() {
        loc.setPlantCount(0);
        loc.removePlant();
        assertEquals(0, loc.getPlantCount());
    }

    @Test
    void testRemoveDeadAnimals() {
        Wolf w1 = new Wolf();
        Wolf w2 = new Wolf();
        w2.setAlive(false);
        loc.addAnimal(w1);
        loc.addAnimal(w2);

        var dead = loc.removeDeadAnimals();
        assertEquals(1, dead.size());
        assertSame(w2, dead.get(0));
        assertEquals(1, loc.getAnimals().size());
    }

    @Test
    void testGetAnimalsUnmodifiable() {
        loc.addAnimal(new Wolf());
        assertThrows(UnsupportedOperationException.class, () -> {
            loc.getAnimals().add(new Rabbit());
        });
    }
}
