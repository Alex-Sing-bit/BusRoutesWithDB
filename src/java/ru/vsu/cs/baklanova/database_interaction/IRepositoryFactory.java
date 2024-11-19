package ru.vsu.cs.baklanova.database_interaction;

import ru.vsu.cs.baklanova.database_interaction.repository.*;
import ru.vsu.cs.baklanova.database_interaction.table_objects.*;
public interface IRepositoryFactory {
    IBuildingRepository<Building> createBuildingRepository();
    IBuildingToStopRepository<BuildingToStop> createBuildingToStopRepository();
    IBusRepository<Bus> createBusRepository();
    IRouteRepository<Route> createRouteRepository();
    IRouteToStopRepository<RouteToStop> createRouteToStopRepository();
    IStopRepository<Stop> createStopRepository();
    IStreetRepository<Street> createStreetRepository();
    IUserRepository<User> createUserRepository();
}
