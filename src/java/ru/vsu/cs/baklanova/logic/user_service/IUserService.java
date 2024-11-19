package ru.vsu.cs.baklanova.logic.user_service;

import ru.vsu.cs.baklanova.database_interaction.table_objects.User;

public interface IUserService {

    void validateUserName(String name);

    void validatePassword(String password);

    void validatePhoneNumber(String phoneNumber);


    boolean registerUser(String name, String phoneNumber, String password);

    boolean loginUser(String phoneNumber, String password);

    void logoutUser();

    String[] getUserInfo();

    boolean updateUserName(String name);

    boolean updateUserPhoneNumber(String phoneNumber);

    boolean updateUserAddress(String streetName, int number);

    User getCurrent();
}
