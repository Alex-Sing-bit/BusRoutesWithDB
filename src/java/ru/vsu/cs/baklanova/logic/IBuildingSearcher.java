package ru.vsu.cs.baklanova.logic;

import ru.vsu.cs.baklanova.database_interaction.table_objects.Building;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Street;

public interface IBuildingSearcher {
    int getBuildingId(String streetName, int buildingNumber);

    Building getBuildingById(int id);

    Street getStreetById(int id);
}
