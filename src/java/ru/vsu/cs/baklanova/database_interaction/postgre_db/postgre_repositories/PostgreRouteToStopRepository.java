package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.RouteToStop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreRouteToStopRepository implements IRouteToStopRepository<RouteToStop> {
    private final DataBaseConnector db;

    public PostgreRouteToStopRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(RouteToStop entity) {
        String query = "INSERT INTO routetostop (route_id, stop_id, stop_number_in_way) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getRouteId());
            preparedStatement.setInt(2, entity.getStopId());
            preparedStatement.setInt(3, entity.getStopNumberInRoute());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public RouteToStop getById(int id) {
        String query = "SELECT * FROM routetostop WHERE route_to_stop_id = ?";
        RouteToStop routeToStop = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                routeToStop = extractRouteToStop(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeToStop;
    }

    @Override
    public boolean update(RouteToStop entity) {
        String query = "UPDATE routetostop SET route_id = ?, stop_id = ?, stop_number_in_way = ? WHERE route_to_stop_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getRouteId());
            preparedStatement.setInt(2, entity.getStopId());
            preparedStatement.setInt(3, entity.getStopNumberInRoute());
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
        String query = "DELETE FROM routetostop WHERE route_to_stop_id = ?";
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
    public List<RouteToStop> findAll() {
        String query = "SELECT * FROM routetostop";
        List<RouteToStop> routeToStops = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                routeToStops.add(extractRouteToStop(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeToStops;
    }

    @Override
    public void validate(RouteToStop entity) {
        // Validation logic for RouteToStop entity
    }

    @Override
    public RouteToStop getByRouteToStopIdPair(int routeId, int stopId) {
        String query = "SELECT * FROM routetostop WHERE route_id = ? AND stop_id = ?";
        RouteToStop routeToStop = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);
            preparedStatement.setInt(2, stopId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                routeToStop = extractRouteToStop(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeToStop;
    }

    @Override
    public List<Integer> findAllRouteIdByStopId(int stopId) {
        String query = "SELECT route_id FROM routetostop WHERE stop_id = ?";
        List<Integer> routeIds = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stopId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                routeIds.add(resultSet.getInt("route_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeIds;
    }

    @Override
    public List<Integer> findAllStopIdByRouteId(int routeId) {
        String query = "SELECT stop_id FROM routetostop WHERE route_id = ?";
        List<Integer> stopIds = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stopIds.add(resultSet.getInt("stop_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopIds;
    }

    @Override
    public int findStopNumberInWay(int routeId, int stopId) {
        String query = "SELECT stop_number_in_way FROM routetostop WHERE route_id = ? AND stop_id = ?";
        int stopNumber = 0;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);
            preparedStatement.setInt(2, stopId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stopNumber = resultSet.getInt("stop_number_in_way");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopNumber;
    }

    @Override
    public int findStopByNumberInWay(int routeId, int number) {
        String query = "SELECT stop_id FROM routetostop WHERE route_id = ? AND stop_number_in_way = ?";
        int stopId = 0;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, routeId);
            preparedStatement.setInt(2, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stopId = resultSet.getInt("stop_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopId;
    }


    private RouteToStop extractRouteToStop(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("route_to_stop_id");
        int routeId = resultSet.getInt("route_id");
        int stopId = resultSet.getInt("stop_id");
        int stopNumber = resultSet.getInt("stop_number_in_way");

        return new RouteToStop(id, routeId, stopId, stopNumber);
    }
}
