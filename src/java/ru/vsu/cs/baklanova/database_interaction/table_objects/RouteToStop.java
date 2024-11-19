package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class RouteToStop extends ID {
    private int routeId;
    private int stopId;

    private int stopNumberInRoute;

    public RouteToStop(int id, int routeId, int stopId, int stopNumberInRoute) {
        super(id);
        setRouteId(routeId);
        setStopId(stopId);
        setStopNumberInRoute(stopNumberInRoute);
    }

    public void setRouteId(int routeId) {
        if (routeId < 1) {
            throw new IllegalArgumentException("Route id cannot be less than 1");
        }
        this.routeId = routeId;
    }

    public void setStopId(int stopId) {
        if (stopId < 1) {
            throw new IllegalArgumentException("Stop id cannot be less than 1");
        }
        this.stopId = stopId;
    }

    public void setStopNumberInRoute(int stopNumberInRoute) {
        if (stopNumberInRoute < 1) {
            throw new IllegalArgumentException("Stop number in route cannot be less than 1");
        }
        this.stopNumberInRoute = stopNumberInRoute;
    }


    public int getRouteId() {
        return routeId;
    }

    public int getStopId() {
        return stopId;
    }

    public int getStopNumberInRoute() {
        return stopNumberInRoute;
    }
}
