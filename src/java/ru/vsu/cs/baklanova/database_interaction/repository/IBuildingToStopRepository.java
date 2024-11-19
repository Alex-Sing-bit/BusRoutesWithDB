package ru.vsu.cs.baklanova.database_interaction.repository;

import java.util.List;

public interface IBuildingToStopRepository<T> extends IRepository<T>{

    /**
     * Retrieves a building-to-stop entity by the specified building ID and stop ID.
     * If an entity with the given building ID and stop ID does not exist, returns null.
     *
     * @param buildingId the ID of the building
     * @param stopId the ID of the stop
     * @return the building-to-stop entity with the specified building and stop IDs, or null if not found
     */
    T getByBuildingToStopIdPair(int buildingId, int stopId);


    /**
     * Returns a list of all stop IDs that are associated with the given building ID.
     * If no stops are found for the provided building ID, returns an empty list.
     *
     * @param buildingId the ID of the building for which to find associated stop IDs
     * @return a list of stop IDs associated with the given building ID
     */
    List<Integer> findAllStopIdByBuildingId(int buildingId);
}
