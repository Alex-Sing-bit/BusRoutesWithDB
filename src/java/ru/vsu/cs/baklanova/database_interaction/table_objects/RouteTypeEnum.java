package ru.vsu.cs.baklanova.database_interaction.table_objects;

import java.util.Random;

public enum RouteTypeEnum {
    URBAN("Городской"),
    SUBURBAN("Пригородный"),
    INTERCITY("Междугородний");

    private final String ruTranslation;
    private static final Random rand = new Random();
    RouteTypeEnum(String ruTranslation) {
        this.ruTranslation = ruTranslation;
    }

    public String getRuTranslation() {
        return ruTranslation;
    }

    public static RouteTypeEnum random() {
        int ind = rand.nextInt(0, RouteTypeEnum.values().length);
        return  RouteTypeEnum.values()[ind];
    }
}
