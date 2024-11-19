package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IStreetRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Street;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBStreetRepository implements IStreetRepository<Street> {
    private final FakeDB db;
    private static final int maxNameLength = 100;

    public FakeDBStreetRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(Street entity) {
        entity.setId(db.getCurrentStreetId() + 1);
        validate(entity);
        db.addStreet(entity);
        return true;
    }

    public void validate(Street entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Street cannot be null");
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new IllegalArgumentException("Street name cannot be null or empty");
        } else if (entity.getName().length() > maxNameLength) {
            throw new IllegalArgumentException("Too long street name");
        }
        if (getByName(entity.getName()) != null) {
            throw new IllegalArgumentException("Not unique street name");
        }
    }

    @Override
    public Street getById(int id) {
        return db.getStreets().get(id);
    }

    @Override
    public boolean update(Street entity) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        Street s = getById(id);
        if (s != null) {
            db.getStreets().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Street> findAll() {
        return new ArrayList<>(db.getStreets().values());
    }

    @Override
    public Street getByName(String name) {
        String nameStrip = name.strip();
        return db.getStreets().values().stream()
                .filter(street -> Objects.equals(street.getName(), nameStrip))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getIdByName(String name) {
        Street s = getByName(name);

        return s == null ? -1 : s.getId();
    }
}
