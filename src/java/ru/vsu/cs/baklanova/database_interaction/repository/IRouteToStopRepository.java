package ru.vsu.cs.baklanova.database_interaction.repository;

import java.util.List;

public interface IRouteToStopRepository<T> extends IRepository<T>{
    /**
     * Gets route-to-stop entity by route id and stop id.
     * If entity with given route id and stop id does not exist, returns null.
     * @return route-to-stop entity with given route id and stop id if exists, null otherwise
     */
    T getByRouteToStopIdPair(int routeId, int stopId);


    /**
     * Returns a list of all route IDs that are associated with the given stop ID.
     * If no routes are found for the provided stop ID, returns an empty list.
     *
     * @param stopId the ID of the stop for which to find associated route IDs
     * @return a list of route IDs associated with the given stop ID
     */
    List<Integer> findAllRouteIdByStopId(int stopId);

    /**
     * Returns a list of all stop IDs that are associated with the given route ID.
     * If no stops are found for the provided route ID, returns an empty list.
     *
     * @param routeId the ID of the route for which to find associated stop IDs
     * @return a list of stop IDs associated with the given route ID
     */
    List<Integer> findAllStopIdByRouteId(int routeId);


    /**
     * Returns the number of the stop in the route with the given route ID.
     * If the route does not contain the given stop ID, returns -1.
     *
     * @param routeId the ID of the route in which to find the stop number
     * @param stopId the ID of the stop for which to find the number in the route
     * @return the number of the stop in the route if found, -1 otherwise
     */
    int findStopNumberInWay(int routeId, int stopId);


    /**
     * Returns the stop ID for the stop that is in the specified position within the route.
     * If the route does not have a stop at the given position, returns -1.
     *
     * @param routeId the ID of the route in which to find the stop
     * @param number the position of the stop in the route
     * @return the stop ID if found, -1 otherwise
     */
    int findStopByNumberInWay(int routeId, int number);
}

