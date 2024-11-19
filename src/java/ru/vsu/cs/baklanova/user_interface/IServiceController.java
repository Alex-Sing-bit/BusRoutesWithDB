package ru.vsu.cs.baklanova.user_interface;

public interface IServiceController {
     String registerUser(String name, String phoneNumber, String password);

     String getWaysBetweenAddresses(String name1, int number1, String name2, int number2);

     String getBusInfo(String routeNumber);

     String loginUser(String phoneNumber, String password);

     void logoutUser();

     String getUserName();

     String getUserPhoneNumber();

     String getUserAddress();

     String updateUserName(String name);

     String updateUserPhoneNumber(String phoneNumber);

     String updateUserAddress(String streetName, String number);

     boolean isUserChosen();
}
