package ru.vsu.cs.baklanova.database_interaction.table_objects;

import java.util.Random;

public enum StreetTypeEnum {
    AVENUE("Проспект"),
    STREET("Улица"),
    LANE("Переулок"),
    SQUARE("Площадь");

    private final String ruTranslation;

    private static final Random rand = new Random();
    StreetTypeEnum(String ruTranslation) {
        this.ruTranslation = ruTranslation;
    }

    public String getRuTranslation() {
        return ruTranslation;
    }

    public static StreetTypeEnum random() {
        int ind = rand.nextInt(0, StreetTypeEnum.values().length);
        return  StreetTypeEnum.values()[ind];
    }
}
