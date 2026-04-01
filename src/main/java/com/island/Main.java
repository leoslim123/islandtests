package com.island;

import com.island.config.SimulationConfig;
import com.island.island.Island;
import com.island.simulation.SimulationEngine;
import com.island.statistics.StatisticsCollector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("🏝️  Симуляция жизни на острове запускается");
        System.out.println("   Размер острова: " + SimulationConfig.ISLAND_WIDTH + "×" + SimulationConfig.ISLAND_HEIGHT);
        System.out.println("   Интервал такта: " + SimulationConfig.TICK_INTERVAL_MS + " мс");

        Island island = new Island(SimulationConfig.ISLAND_WIDTH, SimulationConfig.ISLAND_HEIGHT);
        island.populate();

        StatisticsCollector statistics = new StatisticsCollector(island);
        SimulationEngine engine = new SimulationEngine(island, statistics);

        statistics.printStatistics();

        engine.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {;
            engine.stop();
        }));

        if (SimulationConfig.MAX_TICKS > 0) {
            long waitMs = SimulationConfig.MAX_TICKS * SimulationConfig.TICK_INTERVAL_MS + 1000;
            Thread.sleep(waitMs);
            engine.stop();
            System.out.println("✅ Симуляция завершена. Всего тактов: " + statistics.getTick());
        } else {
            Thread.currentThread().join();
        }
    }
}
