package com.island.island;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    @Test
    void testIslandSize() {
        Island island = new Island(10, 5);
        assertEquals(10, island.getWidth());
        assertEquals(5, island.getHeight());
    }

    @Test
    void testAllLocationsCreated() {
        Island island = new Island(10, 5);
        var locs = island.getAllLocations();
        assertEquals(50, locs.size());
    }

    @Test
    void testGetLocation() {
        Island island = new Island(10, 5);
        Location loc = island.getLocation(3, 2);
        assertNotNull(loc);
        assertEquals(3, loc.getX());
        assertEquals(2, loc.getY());
    }

    @Test
    void testPopulatePlants() {
        Island island = new Island(5, 5);
        island.populate();
        int totalPlants = 0;
        for (Location loc : island.getAllLocations()) {
            totalPlants += loc.getPlantCount();
        }
        assertTrue(totalPlants > 0);
    }

    @Test
    void testPopulateAnimals() {
        Island island = new Island(10, 10);
        island.populate();
        long totalAnimals = 0;
        for (Location loc : island.getAllLocations()) {
            totalAnimals += loc.getAnimals().size();
        }
        assertTrue(totalAnimals > 0);
    }
}
