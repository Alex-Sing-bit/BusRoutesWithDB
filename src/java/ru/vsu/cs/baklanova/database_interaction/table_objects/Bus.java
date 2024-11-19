package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class Bus extends ID {
    private String number;
    private int routeId;

    private int stopId;

    public Bus(int id, String number, int routeId, int stopId) {
        super(id);
        setNumber(number);
        setRouteId(routeId);
        setStopId(stopId);
    }


    public void setNumber(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Bus number cannot be null or empty");
        }
        number = number.strip();
        this.number = number;
    }

    public void setStopId(int stopId) {
        if (stopId < 1) {
            throw new IllegalArgumentException("Stop id cannot be less than 1");
        }
        this.stopId = stopId;
    }

    public void setRouteId(int routeId) {
        if (routeId < 1) {
            throw new IllegalArgumentException("Route id cannot be less than 1");
        }
        this.routeId = routeId;
    }

    public String getNumber() {
        return number;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getStopId() {
        return stopId;
    }

    public String toString() {
        return "Номер автобуса [" + number + "]\n";
    }
}
