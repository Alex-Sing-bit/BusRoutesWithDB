package ru.vsu.cs.baklanova.database_interaction.table_objects;

public class BuildingToStop extends ID{
    private int buildingId;
    private int stopId;

    public BuildingToStop(int id, int buildingId, int stopId) {
        super(id);
        this.buildingId = buildingId;
        this.stopId = stopId;
    }

    public void setBuildingId(int buildingId) {
        if (buildingId < 1) {
            throw new IllegalArgumentException("Building id cannot be less than 1");
        }
        this.buildingId = buildingId;
    }

    public void setStopId(int stopId) {
        if (stopId < 1) {
            throw new IllegalArgumentException("Stop id cannot be less than 1");
        }
        this.stopId = stopId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public int getStopId() {
        return stopId;
    }
}
