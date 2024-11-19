package ru.vsu.cs.baklanova.database_interaction.repository;

import java.util.List;

public interface IBusRepository<T> extends IRepository<T>{


    /**
     * Retrieves a bus entity by its number.
     *
     * @param number The bus plate number.
     * @return The bus entity corresponding to the given number, or null if not found.
     */
    T getByNumber(String number);


    /**
     * Retrieves a list of all bus entities that are associated with the given route ID.
     *
     * @param routeId The ID of the route for which to find associated bus entities.
     * @return A list of bus entities associated with the given route ID. If no buses are found, returns an empty list.
     */
    List<T> getAllBusesByRoute(int routeId);
}
