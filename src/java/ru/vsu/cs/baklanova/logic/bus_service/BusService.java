package ru.vsu.cs.baklanova.logic.bus_service;

import ru.vsu.cs.baklanova.database_interaction.table_objects.Bus;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Route;
import ru.vsu.cs.baklanova.database_interaction.table_objects.RouteToStop;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Stop;
import ru.vsu.cs.baklanova.database_interaction.repository.IBusRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IStopRepository;

import java.util.*;

public class BusService implements IBusService {
    private final IRouteRepository<Route> routeRepository;
    private final IStopRepository<Stop> stopRepository;
    private final IRouteToStopRepository<RouteToStop> routeToStopRepository;

    private final IBusRepository<Bus> busRepository;

    public BusService(IRouteRepository<Route> routeRepository,
                        IRouteToStopRepository<RouteToStop> routeToStopRepository,
                        IBusRepository<Bus> busRepository, IStopRepository<Stop> stopRepository) {
        this.routeRepository = routeRepository;
        this.routeToStopRepository = routeToStopRepository;
        this.stopRepository = stopRepository;
        this.busRepository = busRepository;
    }

    public Map<String, Character> getBusesOnWay(int routeNumber) {
        int routeId = routeRepository.getByNumber(routeNumber).getId();
        if (routeId <= 0) {
            throw new IllegalArgumentException("Route with this number doesn't exist");
        }


        List<Integer> stops = routeToStopRepository.findAllStopIdByRouteId(routeId);
        List<Bus> buses = busRepository.getAllBusesByRoute(routeId);
        if (buses.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Character> stopsWithBuses = new HashMap<>();
        for (Integer stopId : stops) {
            for (Bus bus : buses) {
                if (bus.getStopId() == stopId) {
                    stopsWithBuses.put(stopRepository.getById(stopId).getName(), '*');
                } else {
                    stopsWithBuses.put(stopRepository.getById(stopId).getName(), ' ');
                }
            }
        }

        return stopsWithBuses;
    }

}
