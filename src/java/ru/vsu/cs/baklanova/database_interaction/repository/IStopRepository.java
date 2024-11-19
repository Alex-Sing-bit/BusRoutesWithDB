package ru.vsu.cs.baklanova.database_interaction.repository;

import java.util.List;

public interface IStopRepository<T> extends IRepository<T>{
    /**
     * Returns a list of stops located on the street with the given streetId
     */
    List<T> getByStreetId(int streetId);
}