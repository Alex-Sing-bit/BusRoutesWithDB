package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IStreetRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.BuildingTypeEnum;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Stop;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Street;
import ru.vsu.cs.baklanova.database_interaction.table_objects.StreetTypeEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostgreStreetRepository implements IStreetRepository<Street> {

    private final DataBaseConnector db;
    public PostgreStreetRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }
    @Override
    public boolean add(Street entity) {
        String query = "INSERT INTO street (street_name, street_type) VALUES (?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setObject(2, entity.getType().name(), Types.OTHER);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Street getById(int id)  {
        String query = "SELECT * FROM street WHERE street_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractStreet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Street entity) {
        String query = "UPDATE street SET street_name = ?, street_type = ? WHERE street_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getType().name());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM street WHERE street_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Street> findAll() {
        String query = "SELECT * FROM street";
        try (Connection connection = db.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             return extractStreets(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    @Override
    public void validate(Street entity) {

    }

    @Override
    public Street getByName(String name) {
        String query = "SELECT * FROM street WHERE street_name = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractStreet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getIdByName(String name) {
        String query = "SELECT street_id FROM street WHERE street_name = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("street_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Street extractStreet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("street_id");
        String name = resultSet.getString("street_name");
        StreetTypeEnum type = StreetTypeEnum.valueOf(resultSet.getString("street_type"));

        return new Street(id, name, type);
    }

    private List<Street> extractStreets(ResultSet resultSet) throws SQLException {
        List<Street> streets = new ArrayList<>();
        while (resultSet.next()) {
            streets.add(extractStreet(resultSet));
        }
        return streets;
    }
}
