package ru.vsu.cs.baklanova.database_interaction.repository;

public interface IBuildingRepository<T> extends IRepository<T>{

    /**
     * Retrieves a building entity by its street ID and building number.
     *
     * @param streetId The ID of the street where the building is located.
     * @param number The number of the building on the specified street.
     * @return The building entity corresponding to the given street ID and number, or null if not found.
     */
    T getByAddress(int streetId, int number);

    /**
     * Retrieves the ID of a building entity by its street ID and building number.
     *
     * @param streetId The ID of the street where the building is located.
     * @param number The number of the building on the specified street.
     * @return The ID of the building if found, otherwise -1.
     */
    int getIdByAddress(int streetId, int number);
}
