package ru.vsu.cs.baklanova.database_interaction.repository;

public interface IStreetRepository<T> extends IRepository<T>{
    /**
     * Returns the street with the given name or null if no such street exists.
     *
     * @param name the name of the street to be found
     * @return the street with the given name or null
     */
    T getByName(String name);

    /**
     * Returns the id of the street with the given name or -1 if no such street exists.
     *
     * @param name the name of the street to be found
     * @return the id of the street with the given name or -1
     */
    int getIdByName(String name);
}
