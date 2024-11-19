package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBRouteRepository implements IRouteRepository<Route> {
    private final FakeDB db;

    public FakeDBRouteRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(Route entity) {
        entity.setId(db.getCurrentRouteId() + 1);
        validate(entity);
        db.addRoute(entity);
        return true;
    }

    public void validate(Route entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Route cannot be null");
        }
        if (entity.getNumber() < 1) {
            throw new IllegalArgumentException("Route number cannot be less than 1");
        }
        if (getByNumber(entity.getNumber()) != null) {
            throw new IllegalArgumentException("Not unique route number");
        }
    }

    @Override
    public Route getById(int id) {
        return db.getRoutes().get(id);
    }

    @Override
    public boolean update(Route entity) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        Route s = getById(id);
        if (s != null) {
            db.getRoutes().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Route> findAll() {
        return new ArrayList<>(db.getRoutes().values());
    }


    @Override
    public Route getByNumber(int number) {
        return db.getRoutes().values().stream()
                .filter(route -> Objects.equals(route.getNumber(), number))
                .findFirst()
                .orElse(null);
    }

}


