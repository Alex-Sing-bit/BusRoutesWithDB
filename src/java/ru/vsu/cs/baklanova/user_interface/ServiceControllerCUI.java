package ru.vsu.cs.baklanova.user_interface;

import ru.vsu.cs.baklanova.logic.bus_service.IBusService;
import ru.vsu.cs.baklanova.logic.user_service.IUserService;
import ru.vsu.cs.baklanova.logic.route_service.IRouteService;

import java.util.ArrayList;
import java.util.Map;

public class ServiceControllerCUI implements IServiceController {
    private final IUserService userService;
    private final IRouteService routeService;

    private final IBusService busService;

    private static final String SUCCESS = "Update success";
    private static final String FAIL = "The operation failed. Check data and try again";


    public ServiceControllerCUI(IUserService userService, IRouteService routeService, IBusService busService) {
        this.userService = userService;
        this.routeService = routeService;
        this.busService = busService;
    }

    @Override
    public String registerUser(String name, String phoneNumber, String password) {
        boolean r = userService.registerUser(name, phoneNumber, password);
        return r ? "Registration success" : "Wrong data. Check it and try again";

    }

    @Override
    public String getWaysBetweenAddresses(String streetName1, int number1, String streetName2, int number2) {
        StringBuilder s = new StringBuilder();
        Map<Integer, ArrayList<String>> possibleWays = routeService.findWayBetween(streetName1, number1, streetName2, number2);
        if (possibleWays.isEmpty()) {
            return "No ways between these buildings";
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : possibleWays.entrySet()) {
            s.append(ConsoleTextEnum.WHITE.getCode()).append("Route: ").append(entry.getKey())
                    .append("\n").append(ConsoleTextEnum.RESET.getCode());
            ArrayList<String> stops = entry.getValue();
            int len = stops.size();
            for (int i = 0; i < len; i++) {
                if (i == 0 || i == len - 1) {
                    s.append(ConsoleTextEnum.YELLOW.getCode()).append(stops.get(i))
                            .append("\n").append(ConsoleTextEnum.RESET.getCode());
                } else {
                    s.append(stops.get(i)).append("\n");
                }
            }
        }
        return s.toString();
    }

    @Override
    public String getBusInfo(String routeNumber) {
        int num;
        try {
            num = Integer.parseInt(routeNumber);
        } catch (Exception e) {
            return "Wrong route number";
        }

        return formatBusWayToConsoleInterface(busService.getBusesOnWay(num));
    }

    private String formatBusWayToConsoleInterface(Map<String, Character> stopsWithBuses) {
        if (stopsWithBuses == null || stopsWithBuses.isEmpty()) {
            return "This route is not working now";
        }
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, Character> e : stopsWithBuses.entrySet()) {
            s.append(e.getKey()).append(" ").append(e.getValue()).append("\n");
        }

        return s.toString();
    }

    @Override
    public String loginUser(String phoneNumber, String password) {
        boolean r = userService.loginUser(phoneNumber, password);
        return r ? "Login success" : "Wrong login or password";
    }

    @Override
    public void logoutUser() {
        userService.logoutUser();
    }

    @Override
    public String getUserName() {
        String info = userService.getUserInfo()[0];
        return (info == null || info.isEmpty()) ? "Unknown name" : info;
    }

    @Override
    public String getUserPhoneNumber() {
        String info = userService.getUserInfo()[1];
        return (info == null || info.isEmpty()) ? "Unknown phone number" : info;
    }

    @Override
    public String getUserAddress() {
        String info = userService.getUserInfo()[2];
        return (info == null || info.isEmpty()) ? "Unknown address" : info;
    }

    @Override
    public String updateUserName(String name) {
        boolean r = userService.updateUserName(name);
        return r ? SUCCESS : FAIL;
    }

    @Override
    public String updateUserPhoneNumber(String phoneNumber) {
        boolean r = userService.updateUserPhoneNumber(phoneNumber);
        return r ? SUCCESS : FAIL;
    }

    @Override
    public String updateUserAddress(String streetName, String number) {
        int num;
        try {
            num = Integer.parseInt(number);
        } catch (Exception e) {
            return "Wrong building number";
        }

        boolean r = userService.updateUserAddress(streetName, num);
        return r ? SUCCESS : FAIL;
    }

    @Override
    public boolean isUserChosen() {
        return userService.getCurrent() != null;
    }
}

