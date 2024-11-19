package ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository;

import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.RouteToStop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeDBRouteToStopRepository implements IRouteToStopRepository<RouteToStop> {

    private final FakeDB db;

    public FakeDBRouteToStopRepository(FakeDB db) {
        if (db == null) {
            throw new IllegalArgumentException("Null data base");
        }
        this.db = db;
    }

    @Override
    public boolean add(RouteToStop entity) {
        validate(entity);
        db.addStopToRoute(entity);
        return true;
    }

    @Override
    public RouteToStop getById(int id) {
        return db.getRoutesToStops().get(id);
    }

    public void validate(RouteToStop entity) {
        if (getByRouteToStopIdPair(entity.getRouteId(), entity.getStopId()) != null) {
            throw new IllegalArgumentException("Not unique stop to route pair");
        }
    }


    @Override
    public RouteToStop getByRouteToStopIdPair(int routeId, int stopId) {
        return db.getRoutesToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getRouteId(), routeId))
                .filter(sr -> Objects.equals(sr.getStopId(), stopId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(RouteToStop entity) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        RouteToStop rs = getById(id);
        if (rs != null) {
            db.getBuildingsToStops().remove(id);
            return true;
        }
        return false;
    }


    @Override
    public List<RouteToStop> findAll() {
        return new ArrayList<>(db.getRoutesToStops().values());
    }

    @Override
    public List<Integer> findAllRouteIdByStopId(int stopId) {
        return db.getRoutesToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getStopId(), stopId))
                .map(RouteToStop::getRouteId).toList();
    }

    @Override
    public List<Integer> findAllStopIdByRouteId(int routeId) {
        return db.getRoutesToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getRouteId(), routeId))
                .map(RouteToStop::getStopId).toList();
    }

    @Override
    public int findStopNumberInWay(int routeId, int stopId) {
        return db.getRoutesToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getRouteId(), routeId))
                .filter(sr -> Objects.equals(sr.getStopId(), stopId))
                .map(RouteToStop::getStopNumberInRoute).findFirst()
                .orElse(-1);
    }

    @Override
    public int findStopByNumberInWay(int routeId, int number) {
        return db.getRoutesToStops().values().stream()
                .filter(sr -> Objects.equals(sr.getRouteId(), routeId))
                .filter(sr -> Objects.equals(sr.getStopNumberInRoute(), number))
                .map(RouteToStop::getStopId).findFirst()
                .orElse(-1);
    }
}
