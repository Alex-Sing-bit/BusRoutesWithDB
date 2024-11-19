package ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories;

import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.repository.IBuildingRepository;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Building;
import ru.vsu.cs.baklanova.database_interaction.table_objects.BuildingTypeEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreBuildingRepository implements IBuildingRepository<Building> {
    private final DataBaseConnector db;
    public PostgreBuildingRepository(DataBaseConnector db) {
        if (db == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.db = db;
    }

    @Override
    public boolean add(Building entity) {
        String query = "INSERT INTO building (building_number, building_type, street_id) VALUES (?, ?, ?)";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setObject(2, entity.getType().name(), Types.OTHER);
            preparedStatement.setInt(3, entity.getStreetId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }



    @Override
    public Building getById(int id) {
        String query = "SELECT * FROM building WHERE building_id = ?";
        Building building = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                building = extractBuilding(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return building;
    }

    @Override
    public boolean update(Building entity) {
        String query = "UPDATE building SET building_number = ?, building_type = ?, street_id = ? WHERE building_id = ?";
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, entity.getNumber());
            preparedStatement.setString(2, entity.getType().name());
            preparedStatement.setInt(3, entity.getStreetId());
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
        String query = "DELETE FROM building WHERE building_id = ?";
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
    public List<Building> findAll() {
        String query = "SELECT * FROM building";
        List<Building> buildings = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {
            buildings = extractBuildings(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildings;
    }

    @Override
    public void validate(Building entity) {
        // Валидация данных здания
    }

    @Override
    public Building getByAddress(int streetId, int number) {
        String query = "SELECT * FROM building WHERE street_id = ? AND building_number = ?";
        Building building = null;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, streetId);
            preparedStatement.setInt(2, number);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                building = extractBuilding(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return building;
    }

    @Override
    public int getIdByAddress(int streetId, int number) {
        String query = "SELECT building_id FROM building WHERE street_id = ? AND building_number = ?";
        int id = -1;
        try (Connection connection = db.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, streetId);
            preparedStatement.setInt(2, number);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private Building extractBuilding(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("building_id");
        int number = resultSet.getInt("building_number");
        BuildingTypeEnum type = BuildingTypeEnum.valueOf(resultSet.getString("building_type"));
        int streetId = resultSet.getInt("street_id");

        return new Building(id, number, type, streetId);
    }

    private List<Building> extractBuildings(ResultSet resultSet) throws SQLException {
        List<Building> buildings = new ArrayList<>();
        while (resultSet.next()) {
            buildings.add(extractBuilding(resultSet));
        }
        return buildings;
    }
}