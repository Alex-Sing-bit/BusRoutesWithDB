package ru.vsu.cs.baklanova.user_interface;

import ru.vsu.cs.baklanova.Pair;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleUserInterface implements IUserInterface {
    private final ServiceControllerCUI serviceControllerCUI;
    private final Scanner scanner;

    private static final String MARGIN_LEFT = "\t\t\t\t";

    private static final String WRONG_OPTION = "The option does not exist. Please, try again.";

    public ConsoleUserInterface(ServiceControllerCUI serviceControllerCUI) {
        this.serviceControllerCUI = serviceControllerCUI;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {

        System.out.println(ConsoleTextEnum.GREY.getCode() + MARGIN_LEFT
                 + serviceControllerCUI.getUserName() + ConsoleTextEnum.RESET.getCode());
        System.out.println("1. Personal account");
        System.out.println("2. Find a route");
        System.out.println("3. Bus info");
        System.out.println("4. Exit");

        String option = scanner.nextLine().strip();
        int opt;
        try {
            opt = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            opt = 0;
        }

        switch (opt) {
            case 1 -> displayUserMenu();
            case 2 -> displayRouteMenu();
            case 3 -> displayBusMenu();
            case 4 -> {
                return;
            }
            default -> showError(WRONG_OPTION);
        }

        displayMenu();
    }

    @Override
    public void displayBusMenu() {
        showHint("* - chosen bus on this stop now");
        System.out.print("Bus's route: ");
        try {
            System.out.println(serviceControllerCUI.getBusInfo(scanner.nextLine().strip()));
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @Override
    public void displayRouteMenu() {
        Pair<Integer, String> start;
        Pair<Integer, String> finish;
        try {
            System.out.println("Start address");
            start = chooseHomeAddressMenu();
            System.out.println("Finish address");
            finish = chooseHomeAddressMenu();
            System.out.println(serviceControllerCUI.getWaysBetweenAddresses(start.getSecond(), start.getFirst(),
                        finish.getSecond(), finish.getFirst()));
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Wrong input");
        }
    }

    private Pair<Integer, String> chooseHomeAddressMenu() {
        System.out.println("1. Home address");
        System.out.println("2. Other address");
        String option = scanner.nextLine().strip();
        int opt;
        opt = Integer.parseInt(option);

        String s1 = "";
        int n1 = 0;
        String[] s = serviceControllerCUI.getUserAddress().split(" ");
        if (s.length < 3 && opt == 1) {
            System.out.println("Home address is not specified. Choose the address");
            opt = 2;
        }
        switch (opt) {
            case 1 -> {
                s1 = s[1];
                n1 = Integer.parseInt(s[2].substring(2));
            }
            case 2 -> {
                System.out.print("Street name: ");
                s1 = scanner.nextLine().strip();
                System.out.print("Building number: ");
               n1 = Integer.parseInt(scanner.nextLine().strip());
            }
            default -> showError(WRONG_OPTION);
        }
        return new Pair<>(n1, s1);
    }

    @Override
    public void displayUserMenu() {
        if (serviceControllerCUI.isUserChosen()) {
            chosenUserMenu();
        } else {
            emptyUserMenu();
        }
    }

    @Override
    public void chosenUserMenu() {
        System.out.println(ConsoleTextEnum.GREY.getCode() +
                MARGIN_LEFT + serviceControllerCUI.getUserName() + ConsoleTextEnum.RESET.getCode());
        System.out.println("1. User Info");
        System.out.println("2. Logout");
        System.out.println("3. Return");

        String option = scanner.nextLine().strip();
        int opt;
        try {
            opt = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            showError(WRONG_OPTION);
            return;
        }

        switch (opt) {
            case 1 -> showUserInfo();
            case 2 -> logoutUser();
            case 3 -> displayMenu();
            default -> showError(WRONG_OPTION);
        }
    }

    @Override
    public void emptyUserMenu() {
        System.out.println(ConsoleTextEnum.GREY.getCode() +
                MARGIN_LEFT + serviceControllerCUI.getUserName() + ConsoleTextEnum.RESET.getCode());
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Return");

        String option = scanner.nextLine().strip();
        int opt;
        try {
            opt = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            showError(WRONG_OPTION);
            return;
        }

        switch (opt) {
            case 1 -> registerUser();
            case 2 -> loginUser();
            case 3 -> displayMenu();
            default -> showError(WRONG_OPTION);
        }
    }
    @Override
    public void registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().strip();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine().strip();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().strip();

        try {
            System.out.println(serviceControllerCUI.registerUser(name, phoneNumber, password));
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @Override
    public void loginUser() {
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine().strip();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().strip();

        try {
            System.out.println(serviceControllerCUI.loginUser(phoneNumber, password));
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @Override
    public void showUserInfo() {
        String name = serviceControllerCUI.getUserName();
        if (name.isEmpty()) {
            System.out.println("The login has not been completed");
            return;
        }
        System.out.println(name);
        System.out.println(serviceControllerCUI.getUserPhoneNumber());
        System.out.println("Home: " + serviceControllerCUI.getUserAddress());

        changeUserInfo();
    }

    private void changeUserInfo() {
        System.out.println("1. Change name");
        System.out.println("2. Change phone number");
        System.out.println("3. Change address");
        System.out.println("4. Return");

        String option = scanner.nextLine().strip();
        int opt;
        try {
            opt = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            showError(WRONG_OPTION);
            return;
        }

        switch (opt) {
            case 1 -> {
                System.out.println("New name: ");
                try {
                    System.out.println(serviceControllerCUI.updateUserName(scanner.nextLine().strip()));
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }
            }
            case 2 -> {
                System.out.println("New phone number: ");
                try {
                    System.out.println(serviceControllerCUI.updateUserPhoneNumber(scanner.nextLine().strip()));
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }
            }
            case 3 -> {
                System.out.print("New address' street name: ");
                String streetName = scanner.nextLine().strip();
                System.out.println("New address' building number: ");
                try {
                    System.out.println(serviceControllerCUI.updateUserAddress(streetName, scanner.nextLine().strip()));
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }
            }
            case 4 -> { return; }
            default -> showError(WRONG_OPTION);

        }
    }

    @Override
    public void logoutUser() {
        serviceControllerCUI.logoutUser();
        System.out.println("Logout successful");
    }

    @Override
    public void showError(String message) {
        System.out.println(ConsoleTextEnum.RED.getCode() + message + ConsoleTextEnum.RESET.getCode());
    }

    public void showHint(String message) {
        System.out.println(ConsoleTextEnum.UNDERLINE.getCode() + ConsoleTextEnum.GREY.getCode()
                + message + ConsoleTextEnum.RESET.getCode());
    }

    @Override
    public void exit() {
        /*Unuseful in the console interface*/
    }
}
