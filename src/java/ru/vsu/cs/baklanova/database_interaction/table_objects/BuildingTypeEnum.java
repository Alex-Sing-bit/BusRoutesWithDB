package ru.vsu.cs.baklanova.database_interaction.table_objects;

import java.util.Random;

public enum BuildingTypeEnum {
    RESIDENTIAL("Жилое"),
    PUBLIC("Общественное"),
    INDUSTRIAL("Производственное");

    private final String ruTranslation;
    private static final Random rand = new Random();
    BuildingTypeEnum(String ruTranslation) {
        this.ruTranslation = ruTranslation;
    }

    public String getRuTranslation() {
        return ruTranslation;
    }

    public static BuildingTypeEnum random() {
        int ind = rand.nextInt(0, BuildingTypeEnum.values().length);
        return  BuildingTypeEnum.values()[ind];
    }
}
