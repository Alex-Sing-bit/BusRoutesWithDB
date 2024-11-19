package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IBusRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreBusRepository implements IBusRepository<Bus> {

    private final DataBaseConnector db;

    public PostgreBusRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(Bus entity) {
        String query = "INSERT INTO bus (bus_number, route_id, stop_id) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getNumber());
            preparedStatement.setInt(2, entity.getRouteId());
            preparedStatement.setInt(3, entity.getStopId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Bus getById(int id) {
        String query = "SELECT * FROM bus WHERE bus_id = ?";
        Bus bus = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bus = extractBus(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bus;
    }

    @Override
    public boolean update(Bus entity) {
        String query = "UPDATE bus SET bus_number = ?, route_id = ?, stop_id = ? WHERE bus_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getNumber());
            preparedStatement.setInt(2, entity.getRouteId());
            preparedStatement.setInt(3, entity.getStopId());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM bus WHERE bus_id = ?";
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
    public List<Bus> findAll() {
        String query = "SELECT * FROM bus";
        List<Bus> buses = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                buses.add(extractBus(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }

    @Override
    public void validate(Bus entity) {
        // Validation logic for Bus entity
    }

    @Override
    public Bus getByNumber(String number) {
        String query = "SELECT * FROM bus WHERE bus_number = ?";
        Bus bus = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bus = extractBus(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bus;
    }

    @Override
    public List<Bus> getAllBusesByRoute(int routeId) {
        String query = "SELECT * FROM bus WHERE route_id = ?";
        List<Bus> buses = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buses.add(extractBus(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }

    // Helper method to extract Bus object from ResultSet
    private Bus extractBus(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("bus_id");
        String number = resultSet.getString("bus_number");
        int routeId = resultSet.getInt("route_id");
        int stopId = resultSet.getInt("stop_id");

        return new Bus(id, number, routeId, stopId);
    }
}
