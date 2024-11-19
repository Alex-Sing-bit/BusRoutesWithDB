package ru.vsu.cs.baklanova.database_interaction.repository;

public interface IRouteRepository <T> extends IRepository<T>{
    /**
     * Retrieves a route entity based on its number.
     *
     * @param number the number of the route to retrieve
     * @return the route entity corresponding to the given number, or null if not found
     */
    T getByNumber(int number);
}