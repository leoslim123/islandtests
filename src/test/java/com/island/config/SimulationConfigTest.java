package com.island.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationConfigTest {

    @Test
    void testWolfParams() {
        double[] params = SimulationConfig.getParams("Wolf");
        assertEquals(50.0, params[0]);
        assertEquals(30, (int) params[1]);
        assertEquals(3, (int) params[2]);
        assertEquals(8.0, params[3]);
    }

    @Test
    void testGetWeight() {
        assertEquals(500.0, SimulationConfig.getWeight("Bear"));
        assertEquals(2.0, SimulationConfig.getWeight("Rabbit"));
        assertEquals(0.05, SimulationConfig.getWeight("Mouse"));
    }

    @Test
    void testGetSpeed() {
        assertEquals(3, SimulationConfig.getSpeed("Wolf"));
        assertEquals(0, SimulationConfig.getSpeed("Caterpillar"));
        assertEquals(4, SimulationConfig.getSpeed("Horse"));
    }

    @Test
    void testEatProbability() {
        assertEquals(60, SimulationConfig.getEatProbability("Wolf", "Rabbit"));
        assertEquals(0, SimulationConfig.getEatProbability("Wolf", "Wolf"));
        assertEquals(80, SimulationConfig.getEatProbability("Bear", "Boa"));
        assertEquals(100, SimulationConfig.getEatProbability("Horse", "Plant"));
    }

    @Test
    void testUnknownAnimalParams() {
        double[] params = SimulationConfig.getParams("Unicorn");
        assertNotNull(params);
        assertEquals(1.0, params[0]);
    }

    @Test
    void testEatProbabilityUnknown() {
        assertEquals(0, SimulationConfig.getEatProbability("Wolf", "Dragon"));
        assertEquals(0, SimulationConfig.getEatProbability("Unknown", "Rabbit"));
    }
}
