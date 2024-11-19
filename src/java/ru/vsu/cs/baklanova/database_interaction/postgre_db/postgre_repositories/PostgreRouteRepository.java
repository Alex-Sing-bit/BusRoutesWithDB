package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Route;
import ru.vsu.cs.baklanova.database_interaction.table_objects.RouteTypeEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreRouteRepository implements IRouteRepository<Route> {
    private final DataBaseConnector db;

    public PostgreRouteRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(Route entity) {
        String query = "INSERT INTO route (route_number, route_type) VALUES (?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setObject(2, entity.getType().name(), Types.OTHER);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Route getById(int id) {
        String query = "SELECT * FROM route WHERE route_id = ?";
        Route route = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                route = extractRoute(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public boolean update(Route entity) {
        String query = "UPDATE route SET route_number = ?, route_type = ? WHERE route_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setString(2, entity.getType().name());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM route WHERE route_id = ?";
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
    public List<Route> findAll() {
        String query = "SELECT * FROM route";
        List<Route> routes = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                routes.add(extractRoute(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    public void validate(Route entity) {
        // Validation logic for Route entity
    }

    @Override
    public Route getByNumber(int number) {
        String query = "SELECT * FROM route WHERE route_number = ?";
        Route route = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                route = extractRoute(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return route;
    }

    // Helper method to extract Route object from ResultSet
    private Route extractRoute(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("route_id");
        int number = resultSet.getInt("route_number");
        String type = resultSet.getString("route_type");

        return new Route(id, number, RouteTypeEnum.valueOf(type)); // Assuming Route has a Type enum
    }
}
