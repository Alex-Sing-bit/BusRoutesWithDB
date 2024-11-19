package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IBuildingRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBBuildingRepository implements IBuildingRepository<Building> {
    private final FakeDB db;

    public FakeDBBuildingRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(Building entity) {
        entity.setId(db.getCurrentBuildingId() + 1);
        validate(entity);
        db.addBuilding(entity);
        return true;
    }

    public void validate(Building entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Stop cannot be null");
        }
        if (entity.getNumber() < 1) {
            throw new IllegalArgumentException("Building number name cannot be less than 1");
        }
        if (getByAddress(entity.getStreetId(), entity.getNumber()) != null) {
            throw new IllegalArgumentException("Not unique building address");
        }
        if (db.getStreets().get(entity.getStreetId()) == null) {
            throw new IllegalArgumentException("Invalid fk: street id");
        }
    }
    @Override
    public Building getById(int id) {
        return db.getBuildings().get(id);
    }

    @Override
    public boolean update(Building entity) {
        db.getBuildings().put(entity.getId(), entity);
        return true;
    }

    @Override
    public boolean delete(int id) {
        Building s = getById(id);
        if (s != null) {
            db.getBuildings().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Building> findAll() {
        return new ArrayList<>(db.getBuildings().values());
    }

    @Override
    public int getIdByAddress(int streetId, int number) {
        Building b = getByAddress(streetId, number);

        return b == null ? -1 : b.getId();
    }

    @Override
    public Building getByAddress(int streetId, int number) {
        return db.getBuildings().values().stream()
                .filter(sr -> Objects.equals(sr.getStreetId(), streetId))
                .filter(sr -> Objects.equals(sr.getNumber(), number))
                .findFirst()
                .orElse(null);
    }
}
