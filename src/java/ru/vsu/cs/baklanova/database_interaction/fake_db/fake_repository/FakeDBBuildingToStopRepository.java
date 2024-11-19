package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IBuildingToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.BuildingToStop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBBuildingToStopRepository implements IBuildingToStopRepository<BuildingToStop> {

    private final FakeDB db;

    public FakeDBBuildingToStopRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(BuildingToStop entity) {
        validate(entity);
        db.addStopToBuilding(entity);
        return true;
    }
    public void validate(BuildingToStop entity) {
        if (getByBuildingToStopIdPair(entity.getBuildingId(), entity.getStopId()) != null) {
            throw new IllegalArgumentException("Not unique stop to route pair");
        }
    }

    @Override
    public BuildingToStop getById(int id) {
        return db.getBuildingsToStops().get(id);
    }
    @Override
    public BuildingToStop getByBuildingToStopIdPair(int buildingId, int stopId) {
        return db.getBuildingsToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getBuildingId(), buildingId))
                .filter(sr -> Objects.equals(sr.getStopId(), stopId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(BuildingToStop entity) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        BuildingToStop bs = getById(id);
        if (bs != null) {
            db.getBuildingsToStops().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<BuildingToStop> findAll() {
        return new ArrayList<>(db.getBuildingsToStops().values());
    }


    @Override
    public List<Integer> findAllStopIdByBuildingId(int buildingId) {
        return db.getBuildingsToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getBuildingId(), buildingId))
                .map(BuildingToStop::getStopId).toList();
    }
}