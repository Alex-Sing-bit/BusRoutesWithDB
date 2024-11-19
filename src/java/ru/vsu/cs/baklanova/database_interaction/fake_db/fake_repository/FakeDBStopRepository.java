package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IStopRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Stop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBStopRepository implements IStopRepository<Stop> {
    private final FakeDB db;
    private static final int maxNameLength = 100;

    public FakeDBStopRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(Stop entity) {
        entity.setId(db.getCurrentStopId() + 1);
        validate(entity);
        db.addStop(entity);
        return true;
    }

    public void validate(Stop entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Stop cannot be null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("Stop name cannot be null or empty");
        } else if (entity.getName().length() > maxNameLength) {
            throw new IllegalArgumentException("Too long stop name");
        }
        if (db.getStreets().get(entity.getStreetId()) == null) {
            throw new IllegalArgumentException("Invalid fk: street id");
        }
    }
    @Override
    public Stop getById(int id) {
        return db.getStops().get(id);
    }

    @Override
    public boolean update(Stop entity) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        Stop s = getById(id);
        if (s != null) {
            db.getStops().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Stop> findAll() {
        return new ArrayList<>(db.getStops().values());
    }

    @Override
    public List<Stop> getByStreetId(int streetId) {
        return db.getStops().values().stream()
                .filter(stop -> Objects.equals(stop.getStreetId(), streetId)).toList();
    }
}