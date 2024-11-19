package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IStopRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Building;
import ru.vsu.cs.baklanova.database_interaction.table_objects.BuildingTypeEnum;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Stop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostgreStopRepository implements IStopRepository<Stop> {
    private final DataBaseConnector db;

    public PostgreStopRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }
    @Override
    public boolean add(Stop entity) {
        String query = "INSERT INTO stop (stop_name, street_id) VALUES (?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getStreetId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Stop getById(int id) {
        String query = "SELECT * FROM stop WHERE stop_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractStop(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Stop entity) {
        String query = "UPDATE stop SET stop_name = ?, street_id = ? WHERE stop_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getStreetId());
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
        String query = "DELETE FROM stop WHERE stop_id = ?";
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
    public List findAll() {
        String query = "SELECT * FROM stop";
        try (Connection connection = db.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             return extractStops(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void validate(Stop entity) {

    }

    @Override
    public List getByStreetId(int streetId) {
        String query = "SELECT * FROM stop WHERE street_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, streetId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractStops(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Stop extractStop(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("stop_id");
        String name = resultSet.getString("stop_name");
        int streetId = resultSet.getInt("street_id");

        return new Stop(id, name, streetId);
    }

    private List<Stop> extractStops(ResultSet resultSet) throws SQLException {
        List<Stop> stops = new ArrayList<>();
        while (resultSet.next()) {
            stops.add(extractStop(resultSet));
        }
        return stops;
    }
}
