package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IBuildingToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.BuildingToStop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreBuildingToStopRepository implements IBuildingToStopRepository<BuildingToStop> {
    private final DataBaseConnector db;

    public PostgreBuildingToStopRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(BuildingToStop entity) {
        String query = "INSERT INTO buildingtostop (building_id, stop_id) VALUES (?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getBuildingId());
            preparedStatement.setInt(2, entity.getStopId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public BuildingToStop getById(int id) {
        String query = "SELECT * FROM buildingtostop WHERE building_to_stop_id = ?";
        BuildingToStop buildingToStop = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                buildingToStop = extractBuildingToStop(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildingToStop;
    }

    @Override
    public boolean update(BuildingToStop entity) {
        String query = "UPDATE buildingtostop SET building_id = ?, stop_id = ? WHERE building_to_stop_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getBuildingId());
            preparedStatement.setInt(2, entity.getStopId());
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
        String query = "DELETE FROM buildingtostop WHERE building_to_stop_id = ?";
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
    public List<BuildingToStop> findAll() {
        String query = "SELECT * FROM buildingtostop";
        List<BuildingToStop> buildingToStops = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                buildingToStops.add(extractBuildingToStop(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildingToStops;
    }

    @Override
    public void validate(BuildingToStop entity) {
        // Validation logic for BuildingToStop entity
    }

    @Override
    public BuildingToStop getByBuildingToStopIdPair(int buildingId, int stopId) {
        String query = "SELECT * FROM buildingtostop WHERE building_id = ? AND stop_id = ?";
        BuildingToStop buildingToStop = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, buildingId);
            preparedStatement.setInt(2, stopId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                buildingToStop = extractBuildingToStop(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildingToStop;
    }

    @Override
    public List<Integer> findAllStopIdByBuildingId(int buildingId) {
        String query = "SELECT stop_id FROM buildingtostop WHERE building_id = ?";
        List<Integer> stopIds = new ArrayList<>();
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, buildingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stopIds.add(resultSet.getInt("stop_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopIds;
    }

    private BuildingToStop extractBuildingToStop(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("building_to_stop_id");
        int buildingId = resultSet.getInt("building_id");
        int stopId = resultSet.getInt("stop_id");

        return new BuildingToStop(id, buildingId, stopId);
    }
}
