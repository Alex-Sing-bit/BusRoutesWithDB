package ru.vsu.cs.baklanova.database_interaction.fake_db;

import ru.vsu.cs.baklanova.database_interaction.IDataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.table_objects.*;

import java.util.*;

public class FakeDB implements IDataBaseConnector {
    private final HashMap<Integer, Street> streets = new HashMap<>();
    private final HashMap<Integer, Stop> stops = new HashMap<>();
    private final HashMap<Integer, Building> buildings = new HashMap<>();
    private final HashMap<Integer, User> users = new HashMap<>();
    private final HashMap<Integer, Route> routes = new HashMap<>();
    private final HashMap<Integer, Bus> buses = new HashMap<>();

    private final HashMap<Integer, BuildingToStop> buildingsToStop = new HashMap<>();
    private final HashMap<Integer, RouteToStop> routesToStops = new HashMap<>();
    private int currentStreetId = 0;
    private int currentStopId = 0;
    private int currentBuildingId = 0;
    private int currentUserId = 0;
    private int currentRouteId = 0;
    private int currentBusId = 0;
    private int currentBuildingToStopId = 0;
    private int currentRouteToStopId = 0;

    public FakeDB() {

    }

    public void addStreet(Street street) {
        currentStreetId++;
        streets.put(currentStreetId, street);
    }

    public void addStop(Stop stop) {
        currentStopId++;
        stops.put(currentStopId, stop);
    }

    public void addBuilding(Building building) {
        currentBuildingId++;
        buildings.put(currentBuildingId, building);
    }

    public void addUser(User user) {
        currentUserId++;
        users.put(currentUserId, user);
    }

    public void addRoute(Route route) {
        currentRouteId++;
        routes.put(currentRouteId, route);
    }

    public void addBus(Bus bus) {
        currentBusId++;
        buses.put( currentBusId, bus);
    }

    public void addStopToBuilding(BuildingToStop buildingToStop) {
        currentBuildingToStopId++;
        buildingsToStop.put(currentBuildingToStopId, buildingToStop);
    }

    public void addStopToRoute(RouteToStop routeToStop) {
        currentRouteToStopId++;
        routesToStops.put(currentRouteToStopId, routeToStop);
    }

    public HashMap<Integer, Building> getBuildings() {
        return buildings;
    }

    public HashMap<Integer, Bus> getBuses() {
        return buses;
    }

    public HashMap<Integer, Route> getRoutes() {
        return routes;
    }

    public HashMap<Integer, Stop> getStops() {
        return stops;
    }

    public HashMap<Integer, Street> getStreets() {
        return streets;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public HashMap<Integer, BuildingToStop> getBuildingsToStops() {
        return buildingsToStop;
    }

    public HashMap<Integer, RouteToStop> getRoutesToStops() {
        return routesToStops;
    }

    public int getCurrentBuildingId() {
        return currentBuildingId;
    }

    public int getCurrentBusId() {
        return currentBusId;
    }

    public int getCurrentRouteId() {
        return currentRouteId;
    }

    public int getCurrentStopId() {
        return currentStopId;
    }

    public int getCurrentStreetId() {
        return currentStreetId;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }


}