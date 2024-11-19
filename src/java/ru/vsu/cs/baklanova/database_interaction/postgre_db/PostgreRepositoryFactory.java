package ru.vsu.cs.baklanova.database_interaction.postgre_db;

import ru.vsu.cs.baklanova.database_interaction.IRepositoryFactory;
import ru.vsu.cs.baklanova.database_interaction.postgre_db.postgre_repositories.*;
import ru.vsu.cs.baklanova.database_interaction.repository.*;
import ru.vsu.cs.baklanova.database_interaction.table_objects.*;

public class PostgreRepositoryFactory implements IRepositoryFactory {
    private final DataBaseConnector db;

    public PostgreRepositoryFactory(DataBaseConnector db) {
        this.db = db;
    }
    @Override
    public IBuildingRepository<Building> createBuildingRepository() {
        return new PostgreBuildingRepository(db);
    }

    @Override
    public IBuildingToStopRepository<BuildingToStop> createBuildingToStopRepository() {
        return new PostgreBuildingToStopRepository(db);
    }

    @Override
    public IBusRepository<Bus> createBusRepository() {
        return new PostgreBusRepository(db);
    }

    @Override
    public IRouteRepository<Route> createRouteRepository() {
        return new PostgreRouteRepository(db);
    }

    @Override
    public IRouteToStopRepository<RouteToStop> createRouteToStopRepository() {
        return new PostgreRouteToStopRepository(db);
    }

    @Override
    public IStopRepository<Stop> createStopRepository() {
        return new PostgreStopRepository(db);
    }

    @Override
    public IStreetRepository<Street> createStreetRepository() {
        return new PostgreStreetRepository(db);
    }

    @Override
    public IUserRepository<User> createUserRepository() {
        return new PostgreUserRepository(db);
    }
}
