package ru.vsu.cs.baklanova.user_interface;


public interface IUserInterface {
    void displayMenu();
    void displayRouteMenu();
    void displayBusMenu();
    void displayUserMenu();

    void chosenUserMenu();

    void emptyUserMenu();

    void registerUser();
    void loginUser();
    void showError(String message);
    void showUserInfo();
    void logoutUser();

    void exit();
}
