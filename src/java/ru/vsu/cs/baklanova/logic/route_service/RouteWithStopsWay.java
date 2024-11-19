package ru.vsu.cs.baklanova.logic.route_service;

import java.util.List;

public class RouteWithStopsWay {
    private int routeId;
    private List<Integer> stopIds;

    public RouteWithStopsWay(int routeId, List<Integer> stopIds) {
        this.routeId = routeId;
        this.stopIds = stopIds;
    }

    public int getRouteId() {
        return routeId;
    }

    public List<Integer> getStopIds() {
        return stopIds;
    }
}
