package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IBusRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBBusRepository implements IBusRepository<Bus> {
    private final FakeDB db;

    private static final int numberLength = 6;

    public FakeDBBusRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(Bus entity) {
        entity.setId(db.getCurrentBusId() + 1);
        validate(entity);
        db.addBus(entity);
        return true;
    }

    public void validate(Bus entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Bus cannot be null");
        }
        if (entity.getNumber() == null || entity.getNumber().isEmpty()) {
            throw new IllegalArgumentException("Bus number cannot be null or empty");
        } else if (entity.getNumber().length() != numberLength) {
            throw new IllegalArgumentException("Wrong length of bus plate number");
        }
        if (db.getStops().get(entity.getStopId()) == null) {
            throw new IllegalArgumentException("Invalid fk: driver id");
        }
        if (db.getRoutes().get(entity.getRouteId()) == null) {
            throw new IllegalArgumentException("Invalid fk: route id");
        }
        if (db.getRoutesToStops().values().stream().filter(rs -> rs.getRouteId() == entity.getRouteId()
                && rs.getStopId() == entity.getStopId()).findFirst().orElse(null) == null) {
            throw new IllegalArgumentException("Invalid fk: route id doesn't contain stop id");
        }
    }

    @Override
    public Bus getById(int id) {
        return db.getBuses().get(id);
    }

    @Override
    public boolean update(Bus entity) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        Bus s = getById(id);
        if (s != null) {
            db.getStops().remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Bus> findAll() {
        return new ArrayList<>(db.getBuses().values());
    }

    @Override
    public Bus getByNumber(String number) {
        String numberStrip = number.strip();
        return db.getBuses().values().stream()
                .filter(bus -> Objects.equals(bus.getNumber(), numberStrip))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Bus> getAllBusesByRoute(int routeId) {
        return db.getBuses().values().stream()
                .filter(bus -> Objects.equals(bus.getRouteId(), routeId)).toList();
    }
}
