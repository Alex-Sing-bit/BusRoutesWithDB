package ru.vsu.cs.baklanova.logic.user_service;

import ru.vsu.cs.baklanova.database_interaction.table_objects.Building;
import ru.vsu.cs.baklanova.database_interaction.table_objects.User;
import ru.vsu.cs.baklanova.database_interaction.repository.IUserRepository;
import ru.vsu.cs.baklanova.logic.IBuildingSearcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserService implements IUserService {
    private final IUserRepository<User> userRepository;
    private final IBuildingSearcher buildingSearcher;
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int NAME_MIN_LENGTH = 2;

    private User current = null;

    public UserService(IUserRepository<User> userRepository, IBuildingSearcher buildingSearcher) {
        this.userRepository = userRepository;
        this.buildingSearcher = buildingSearcher;
    }

    @Override
    public boolean registerUser(String name, String phoneNumber, String password) {
        validateUser(name, phoneNumber, password);

        return userRepository.add(new User(name, phoneNumber, hashPassword(password), null));
    }

    private void validateUser(String name, String phoneNumber, String password) {
        validateUserName(name);
        validatePhoneNumber(phoneNumber);
        validatePassword(password);
    }

    @Override
    public void validateUserName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (name.length() < NAME_MIN_LENGTH) {
            throw new IllegalArgumentException("Too short name. At least 2 symbols needed.");
        }
    }

    @Override
    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new IllegalArgumentException("Too short password. At least 4 symbols needed.");
        }
    }

    @Override
    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty() ) {
            throw new IllegalArgumentException("PhoneNumber cannot be empty.");
        }
        if (isWrongNumberFormat(phoneNumber)) {
            throw new IllegalArgumentException("Wrong format of phone number. Example: +7-000-000-00-00");
        }
    }

    private boolean isWrongNumberFormat(String phoneNumber) {
        String regex = "^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$";
        return !Pattern.matches(regex, phoneNumber);
    }

    @Override
    public boolean loginUser(String phoneNumber, String password) {
        validatePhoneNumber(phoneNumber);
        validatePassword(password);

        User newCurrent = userRepository.getByPhoneNumber(phoneNumber);

        if (newCurrent != null && Objects.equals(newCurrent.getPassword(), hashPassword(password))) {
            current = newCurrent;
            return true;
        }
        return false;
    }

    @Override
    public void logoutUser() {
        current = null;
    }

    @Override
    public String[] getUserInfo() {
        String name = current == null ? "" : current.getName();
        String phoneNumber =  current == null ? "" : current.getPhoneNumber();
        String address = "";
        if (current != null && current.getHomeBuildingId() != null) {
            Building b = buildingSearcher.getBuildingById(current.getHomeBuildingId());
            address = b == null ? "" : buildingSearcher.getStreetById(b.getStreetId()).toString() + " " + b;
        }

        return new String[]{name, phoneNumber, address};
    }
    @Override
    public boolean updateUserName(String name) {
        if (current == null) {
            throw new IllegalArgumentException("Log in to change info");
        }
        current.setName(name);
        return userRepository.update(current);
    }

    @Override
    public boolean updateUserPhoneNumber(String phoneNumber) {
        if (current == null) {
            return false;
        }
        validatePhoneNumber(phoneNumber);

        current.setPhoneNumber(phoneNumber);
        return userRepository.update(current);
    }

    @Override
    public boolean updateUserAddress(String streetName, int number) {
        if (current == null) {
            return false;
        }

        current.setHomeBuildingId(buildingSearcher.getBuildingId(streetName, number));
        return userRepository.update(current);
    }

    @Override
    public User getCurrent() {
        return current;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
