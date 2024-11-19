package ru.vsu.cs.baklanova.logic;

import ru.vsu.cs.baklanova.database_interaction.table_objects.Building;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Street;
import ru.vsu.cs.baklanova.database_interaction.repository.IBuildingRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IStreetRepository;

public class BuildingSearcher implements IBuildingSearcher{
    private final IBuildingRepository<Building> buildingRepository;
    private final IStreetRepository<Street> streetRepository;

    public BuildingSearcher(IBuildingRepository<Building> buildingRepository,
                            IStreetRepository<Street> streetRepository) {
        this.buildingRepository = buildingRepository;
        this.streetRepository = streetRepository;
    }

    @Override
    public int getBuildingId(String streetName, int buildingNumber) {
        if (streetName == null || streetName.isEmpty()) {
            throw new IllegalArgumentException("Street name cannot be empty.");
        }

        int streetId = streetRepository.getIdByName(streetName);
        if (streetId <= 0) {
            throw new IllegalArgumentException("Street was not found.");
        }

        int buildingId = buildingRepository.getIdByAddress(streetId, buildingNumber);
        if (buildingId <= 0) {
            throw new IllegalArgumentException("Building was not found.");
        }

        return buildingId;
    }

    @Override
    public Building getBuildingById(int id) {
        return buildingRepository.getById(id);
    }

    @Override
    public Street getStreetById(int id) {
        return streetRepository.getById(id);
    }

}