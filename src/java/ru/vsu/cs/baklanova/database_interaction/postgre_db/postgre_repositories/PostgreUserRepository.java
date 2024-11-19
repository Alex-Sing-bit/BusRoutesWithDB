package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IUserRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreUserRepository implements IUserRepository<User> {

    private final DataBaseConnector db;
    public PostgreUserRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(User entity) {
        String query = "INSERT INTO user (user_name, user_phone_number, user_password, building_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getPhoneNumber());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getHomeBuildingId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM user WHERE user_id = ?";
        User user = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User entity) {
        String query = "UPDATE user SET user_name = ?, user_phone_number = ?, " +
                "user_password = ?, building_id = ? WHERE user_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getPhoneNumber());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getHomeBuildingId());
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM user";
        List<User> users = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            users = extractUsers(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void validate(User entity) {
        // Валидация данных пользователя
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        String query = "SELECT * FROM user WHERE user_phone_number = ?";
        User user = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser (resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Методы для извлечения данных из ResultSet в объект User и список User
    private User extractUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String name = resultSet.getString("user_name");
        String phoneNumber = resultSet.getString("user_phone_number");
        String password = resultSet.getString("user_password");
        int homeBuildingId = resultSet.getInt("building_id");

        return new User(id, name, phoneNumber, password, homeBuildingId);
    }

    private List<User> extractUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(extractUser(resultSet));
        }
        return users;
    }
}
