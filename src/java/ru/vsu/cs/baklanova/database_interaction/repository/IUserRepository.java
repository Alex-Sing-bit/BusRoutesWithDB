package ru.vsu.cs.baklanova.database_interaction.repository;

public interface IUserRepository<T> extends IRepository<T>{

    /**
     * Retrieves a user entity based on their phone number.
     *
     * @param phoneNumber The phone number in the format +7-000-000-00-00
     * @return the user entity corresponding to the given phone number, or null if not found
     */
    T getByPhoneNumber(String phoneNumber);
}

