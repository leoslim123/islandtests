package com.island.statistics;

import com.island.entity.Animal;
import com.island.entity.Plant;
import com.island.entity.herbivores.*;
import com.island.entity.predators.*;
import com.island.island.Island;
import com.island.island.Location;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StatisticsCollector {

    private final Island island;
    private final AtomicLong tick = new AtomicLong(0);

    private static final Map<String, String[]> SPECIES_INFO = new LinkedHashMap<>();

    static {
        SPECIES_INFO.put("Wolf",        new String[]{"🐺", "Волк"});
        SPECIES_INFO.put("Boa",         new String[]{"🐍", "Удав"});
        SPECIES_INFO.put("Fox",         new String[]{"🦊", "Лиса"});
        SPECIES_INFO.put("Bear",        new String[]{"🐻", "Медведь"});
        SPECIES_INFO.put("Eagle",       new String[]{"🦅", "Орёл"});
        SPECIES_INFO.put("Horse",       new String[]{"🐴", "Лошадь"});
        SPECIES_INFO.put("Deer",        new String[]{"🦌", "Олень"});
        SPECIES_INFO.put("Rabbit",      new String[]{"🐇", "Кролик"});
        SPECIES_INFO.put("Mouse",       new String[]{"🐭", "Мышь"});
        SPECIES_INFO.put("Goat",        new String[]{"🐐", "Коза"});
        SPECIES_INFO.put("Sheep",       new String[]{"🐑", "Овца"});
        SPECIES_INFO.put("Boar",        new String[]{"🐗", "Кабан"});
        SPECIES_INFO.put("Buffalo",     new String[]{"🐃", "Буйвол"});
        SPECIES_INFO.put("Duck",        new String[]{"🦆", "Утка"});
        SPECIES_INFO.put("Caterpillar", new String[]{"🐛", "Гусеница"});
    }

    public StatisticsCollector(Island island) {
        this.island = island;
    }

    public void printStatistics() {
        long currentTick = tick.incrementAndGet();

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String type : SPECIES_INFO.keySet()) {
            counts.put(type, 0);
        }

        int totalPlants = 0;

        for (Location loc : island.getAllLocations()) {
            for (Animal a : loc.getAnimals()) {
                if (a.isAlive()) {
                    counts.merge(a.getType(), 1, Integer::sum);
                }
            }
            totalPlants += loc.getPlantCount();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("        🏝️  ОСТРОВ — ТАКТ %-5d               \n", currentTick));

        for (Map.Entry<String, String[]> entry : SPECIES_INFO.entrySet()) {
            String type = entry.getKey();
            String emoji = entry.getValue()[0];
            String name = entry.getValue()[1];
            int count = counts.getOrDefault(type, 0);
            sb.append(String.format("  %s %-12s : %-8d               \n", emoji, name, count));
        }

        sb.append(String.format("  🌿 %-12s : %-8d               \n", "Растения", totalPlants));

        System.out.println(sb);
    }

    public long getTick() {
        return tick.get();
    }
}
