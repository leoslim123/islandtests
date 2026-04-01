package com.island.config;

import java.util.HashMap;
import java.util.Map;

public class SimulationConfig {

    public static final int ISLAND_WIDTH = 100;
    public static final int ISLAND_HEIGHT = 20;

    public static final int MAX_TICKS = 0;

    public static final long TICK_INTERVAL_MS = 500;

    public static final int MAX_PLANTS_PER_CELL = 200;

    public static final int PLANTS_GROW_PER_TICK = 10;

    public static final int INITIAL_BEARS_MAX = 5;
    public static final int INITIAL_PLANTS_PER_CELL = 50;
    public static final int INITIAL_ANIMALS_PER_SPECIES = 20;

    public static final Map<String, double[]> ANIMAL_PARAMS = new HashMap<>();

    static {
        ANIMAL_PARAMS.put("Wolf",       new double[]{50,    30,   3,        8});
        ANIMAL_PARAMS.put("Boa",        new double[]{15,    30,   1,        3});
        ANIMAL_PARAMS.put("Fox",        new double[]{8,     30,   2,        2});
        ANIMAL_PARAMS.put("Bear",       new double[]{500,   5,    2,        80});
        ANIMAL_PARAMS.put("Eagle",      new double[]{6,     20,   3,        1});
        ANIMAL_PARAMS.put("Horse",      new double[]{400,   20,   4,        60});
        ANIMAL_PARAMS.put("Deer",       new double[]{300,   20,   4,        50});
        ANIMAL_PARAMS.put("Rabbit",     new double[]{2,     150,  2,        0.45});
        ANIMAL_PARAMS.put("Mouse",      new double[]{0.05,  500,  1,        0.01});
        ANIMAL_PARAMS.put("Goat",       new double[]{60,    140,  3,        10});
        ANIMAL_PARAMS.put("Sheep",      new double[]{70,    140,  3,        15});
        ANIMAL_PARAMS.put("Boar",       new double[]{400,   50,   2,        50});
        ANIMAL_PARAMS.put("Buffalo",    new double[]{700,   10,   3,        100});
        ANIMAL_PARAMS.put("Duck",       new double[]{1,     200,  4,        0.15});
        ANIMAL_PARAMS.put("Caterpillar",new double[]{0.01,  1000, 0,        0});
    }

    public static final Map<String, Map<String, Integer>> EAT_PROBABILITY = new HashMap<>();

    static {
        addRow("Wolf",       new String[]{"Wolf","Boa","Fox","Bear","Eagle","Horse","Deer","Rabbit","Mouse","Goat","Sheep","Boar","Buffalo","Duck","Caterpillar","Plant"},
                             new int[]   {0,      0,    0,    0,     0,     10,    15,    60,     80,    60,   70,   15,    10,     40,    0,           0});

        addRow("Boa",        new String[]{"Wolf","Boa","Fox","Bear","Eagle","Horse","Deer","Rabbit","Mouse","Goat","Sheep","Boar","Buffalo","Duck","Caterpillar","Plant"},
                             new int[]   {0,      0,   15,    0,     0,      0,     0,    20,     40,     0,    0,    0,     0,    10,     0,           0});

        addRow("Fox",        new String[]{"Wolf","Boa","Fox","Bear","Eagle","Horse","Deer","Rabbit","Mouse","Goat","Sheep","Boar","Buffalo","Duck","Caterpillar","Plant"},
                             new int[]   {0,      0,    0,    0,     0,      0,     0,    70,     90,     0,    0,    0,     0,    60,    40,           0});

        addRow("Bear",       new String[]{"Wolf","Boa","Fox","Bear","Eagle","Horse","Deer","Rabbit","Mouse","Goat","Sheep","Boar","Buffalo","Duck","Caterpillar","Plant"},
                             new int[]   {0,     80,    0,    0,     0,     40,    80,    80,     90,    70,   70,   50,    20,    10,     0,           0});

        addRow("Eagle",      new String[]{"Wolf","Boa","Fox","Bear","Eagle","Horse","Deer","Rabbit","Mouse","Goat","Sheep","Boar","Buffalo","Duck","Caterpillar","Plant"},
                             new int[]   {0,      0,   10,    0,     0,      0,     0,    90,     90,     0,    0,    0,     0,    80,     0,           0});

        addRow("Horse",      new String[]{"Plant"}, new int[]{100});
        addRow("Deer",       new String[]{"Plant"}, new int[]{100});
        addRow("Rabbit",     new String[]{"Plant"}, new int[]{100});
        addRow("Mouse",      new String[]{"Caterpillar","Plant"}, new int[]{90, 100});
        addRow("Goat",       new String[]{"Plant"}, new int[]{100});
        addRow("Sheep",      new String[]{"Plant"}, new int[]{100});
        addRow("Boar",       new String[]{"Mouse","Caterpillar","Plant"}, new int[]{50, 90, 100});
        addRow("Buffalo",    new String[]{"Plant"}, new int[]{100});
        addRow("Duck",       new String[]{"Caterpillar","Plant"}, new int[]{90, 100});
        addRow("Caterpillar",new String[]{"Plant"}, new int[]{100});
    }

    private static void addRow(String eater, String[] prey, int[] probabilities) {
        Map<String, Integer> row = new HashMap<>();
        for (int i = 0; i < prey.length; i++) {
            row.put(prey[i], probabilities[i]);
        }
        EAT_PROBABILITY.put(eater, row);
    }

    public static int getEatProbability(String eaterType, String preyType) {
        Map<String, Integer> row = EAT_PROBABILITY.get(eaterType);
        if (row == null) return 0;
        return row.getOrDefault(preyType, 0);
    }

    public static double[] getParams(String animalType) {
        return ANIMAL_PARAMS.getOrDefault(animalType, new double[]{1, 1, 0, 0});
    }

    public static double getWeight(String animalType) { return getParams(animalType)[0]; }
    public static int getMaxPerCell(String animalType) { return (int) getParams(animalType)[1]; }
    public static int getSpeed(String animalType)      { return (int) getParams(animalType)[2]; }
    public static double getFoodNeeded(String animalType) { return getParams(animalType)[3]; }
}
