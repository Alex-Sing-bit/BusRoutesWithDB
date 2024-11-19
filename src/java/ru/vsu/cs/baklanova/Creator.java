package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.database_interaction.DBType;
import ru.vsu.cs.baklanova.database_interaction.DataBaseGenerator;
import ru.vsu.cs.baklanova.database_interaction.postgre_db.DataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.postgre_db.PostgreRepositoryFactory;
import ru.vsu.cs.baklanova.database_interaction.repository.*;
import ru.vsu.cs.baklanova.database_interaction.table_objects.*;
import ru.vsu.cs.baklanova.user_interface.ConsoleUserInterface;
import ru.vsu.cs.baklanova.user_interface.IUserInterface;
import ru.vsu.cs.baklanova.user_interface.ServiceControllerCUI;
import ru.vsu.cs.baklanova.user_interface.UIType;
import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDB;
import ru.vsu.cs.baklanova.database_interaction.fake_db.FakeDBRepositoryFactory;
import ru.vsu.cs.baklanova.database_interaction.IDataBaseConnector;
import ru.vsu.cs.baklanova.database_interaction.IRepositoryFactory;

import ru.vsu.cs.baklanova.logic.BuildingSearcher;
import ru.vsu.cs.baklanova.logic.bus_service.BusService;
import ru.vsu.cs.baklanova.logic.bus_service.IBusService;
import ru.vsu.cs.baklanova.logic.IBuildingSearcher;
import ru.vsu.cs.baklanova.logic.route_service.IRouteService;
import ru.vsu.cs.baklanova.logic.route_service.RouteService;
import ru.vsu.cs.baklanova.logic.user_service.IUserService;
import ru.vsu.cs.baklanova.logic.user_service.UserService;


public class Creator {

    private IDataBaseConnector db;
    private IRouteRepository<Route> routeRepository;
    private IStopRepository<Stop> stopRepository;
    private IRouteToStopRepository<RouteToStop> routeToStopRepository;
    private IBuildingToStopRepository<BuildingToStop> buildingToStopRepository;
    private IUserRepository<User> userRepository;
    private IStreetRepository<Street> streetRepository;
    private IBuildingRepository<Building> buildingRepository;
    private IBusRepository<Bus> busRepository;
    private IUserService userService;
    private IRouteService routeService;
    private IBusService busService;
    private IUserInterface userInterface;

    public void create(DBType dbType, UIType uiType) {
        initializeDatabase(dbType);
        initializeRepositories();
        initializeServices();
        initializeUserInterface(uiType);
    }

    private void initializeDatabase(DBType dbType) {
        switch (dbType) {
            case FAKE -> db = new FakeDB();
            case POSTGRESQL -> db = new DataBaseConnector();
            default -> throw new IllegalArgumentException("Unsupported DBType: " + dbType);
        }
    }

    private void initializeRepositories() {
        IRepositoryFactory repositoryFactory = createRepositoryFactory(db);

        routeRepository = repositoryFactory.createRouteRepository();
        stopRepository = repositoryFactory.createStopRepository();
        routeToStopRepository = repositoryFactory.createRouteToStopRepository();
        buildingToStopRepository = repositoryFactory.createBuildingToStopRepository();
        userRepository = repositoryFactory.createUserRepository();
        streetRepository = repositoryFactory.createStreetRepository();
        buildingRepository = repositoryFactory.createBuildingRepository();
        busRepository = repositoryFactory.createBusRepository();

        DataBaseGenerator dataBaseGenerator = new DataBaseGenerator(streetRepository, stopRepository, buildingRepository,
                userRepository, routeRepository, buildingToStopRepository, routeToStopRepository, busRepository);
        dataBaseGenerator.generateAll();
    }

    private IRepositoryFactory createRepositoryFactory(IDataBaseConnector db) {
        if (db instanceof FakeDB fakeDB) {
            return new FakeDBRepositoryFactory(fakeDB);
        } else if (db instanceof DataBaseConnector postgreDB) {
            return new PostgreRepositoryFactory(postgreDB);
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + db.getClass().getSimpleName());
        }
    }

    private void initializeServices() {
        IBuildingSearcher buildingSearcher = new BuildingSearcher(buildingRepository, streetRepository);
        userService = new UserService(userRepository, buildingSearcher);
        routeService = new RouteService(routeRepository, stopRepository,
                routeToStopRepository, buildingToStopRepository, buildingSearcher);
        busService = new BusService(routeRepository,
                routeToStopRepository, busRepository, stopRepository);
    }

    private void initializeUserInterface(UIType uiType) {
        switch (uiType) {
            case CONSOLE ->
                    userInterface = new ConsoleUserInterface(
                            new ServiceControllerCUI(userService, routeService, busService));
            default -> throw new IllegalArgumentException("Unsupported UIType: " + uiType);
        }
    }

    public IUserInterface getUserInterface() {
        return userInterface;
    }
}
