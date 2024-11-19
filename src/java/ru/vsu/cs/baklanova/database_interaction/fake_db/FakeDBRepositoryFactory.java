package ru.vsu.cs.baklanova.database_interaction.fake_db;

import ru.vsu.cs.baklanova.database_interaction.IRepositoryFactory;
import ru.vsu.cs.baklanova.database_interaction.fake_db.fake_repository.*;
import ru.vsu.cs.baklanova.database_interaction.repository.*;
import ru.vsu.cs.baklanova.database_interaction.table_objects.*;

public class FakeDBRepositoryFactory implements IRepositoryFactory {
    private final FakeDB db;

    public FakeDBRepositoryFactory(FakeDB db) {
        this.db = db;
    }

    @Override
    public IBuildingRepository<Building> createBuildingRepository() {
        return new FakeDBBuildingRepository(db);
    }

    @Override
    public IBuildingToStopRepository<BuildingToStop> createBuildingToStopRepository() {
        return new FakeDBBuildingToStopRepository(db);
    }

    @Override
    public IBusRepository<Bus> createBusRepository() {
        return new FakeDBBusRepository(db);
    }

    @Override
    public IRouteRepository<Route> createRouteRepository() {
        return new FakeDBRouteRepository(db);
    }

    @Override
    public IRouteToStopRepository<RouteToStop> createRouteToStopRepository() {
        return new FakeDBRouteToStopRepository(db);
    }

    @Override
    public IStopRepository<Stop> createStopRepository() {
        return new FakeDBStopRepository(db);
    }

    @Override
    public IStreetRepository<Street> createStreetRepository() {
        return new FakeDBStreetRepository(db);
    }

    @Override
    public IUserRepository<User> createUserRepository() {
        return new FakeDBUserRepository(db);
    }
}
