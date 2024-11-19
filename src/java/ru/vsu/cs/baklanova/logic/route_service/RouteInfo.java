package ru.vsu.cs.baklanova.logic.route_service;

public class RouteInfo {
    private int routeId;
    private int startStopId;
    private int finishStopId;

    public RouteInfo(int routeId, int startStopId, int finishStopId) {
        this.routeId = routeId;
        this.startStopId = startStopId;
        this.finishStopId = finishStopId;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getStartStopId() {
        return startStopId;
    }

    public int getFinishStopId() {
        return finishStopId;
    }
}