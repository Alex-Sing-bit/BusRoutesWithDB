package ru.vsu.cs.baklanova.logic.route_service;

import ru.vsu.cs.baklanova.database_interaction.table_objects.BuildingToStop;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Route;
import ru.vsu.cs.baklanova.database_interaction.table_objects.RouteToStop;
import ru.vsu.cs.baklanova.database_interaction.table_objects.Stop;
import ru.vsu.cs.baklanova.database_interaction.repository.IBuildingToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IRouteToStopRepository;
import ru.vsu.cs.baklanova.database_interaction.repository.IStopRepository;
import ru.vsu.cs.baklanova.logic.IBuildingSearcher;

import java.util.*;

public class RouteService implements IRouteService{

    private final IRouteRepository<Route> routeRepository;
    private final IStopRepository<Stop> stopRepository;
    private final IRouteToStopRepository<RouteToStop> routeToStopRepository;
    private final IBuildingToStopRepository<BuildingToStop> buildingToStopRepository;

    private final IBuildingSearcher buildingSearcher;

    public RouteService(IRouteRepository<Route> routeRepository, IStopRepository<Stop> stopRepository,
                        IRouteToStopRepository<RouteToStop> routeToStopRepository,
                        IBuildingToStopRepository<BuildingToStop> buildingToStopRepository,
                        IBuildingSearcher buildingSearcher) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
        this.routeToStopRepository = routeToStopRepository;
        this.buildingToStopRepository = buildingToStopRepository;
        this.buildingSearcher = buildingSearcher;
    }

    @Override
    public Map<Integer, ArrayList<String>> findWayBetween(String streetName1, int number1,
                                                              String streetName2, int number2) {
        int buildingId1 = buildingSearcher.getBuildingId(streetName1, number1);
        int buildingId2 = buildingSearcher.getBuildingId(streetName2, number2);

        return findWayBetween(buildingId1, buildingId2);
    }

    public Map<Integer, ArrayList<String>> findWayBetween(int buildingStartId, int buildingFinishId){
        List<Integer> startStopsId = buildingToStopRepository.findAllStopIdByBuildingId(buildingStartId);
        List<Integer> finishStopsId = buildingToStopRepository.findAllStopIdByBuildingId(buildingFinishId);

        ArrayList<RouteInfo> suitableRoutes = waysBetweenStops(startStopsId, finishStopsId);
        ArrayList<RouteWithStopsWay> ways = findStopsInRouteWay(suitableRoutes);
        if (ways.isEmpty()) {
            //ways = findTransferRoutes(buildingStartId, buildingFinishId);
        }

        return makeFinishedForm(startStopsId, ways);
    }

    private HashMap<Integer, ArrayList<String>> makeFinishedForm
            (List<Integer> start, List<RouteWithStopsWay> ways) {

        HashMap<Integer, ArrayList<String>> routes = new HashMap<>();
        for (RouteWithStopsWay way : ways) {
            int key = routeRepository.getById(way.getRouteId()).getNumber();
            List<Integer> stopsId = way.getStopIds();
            ArrayList<String> stops = new ArrayList<>();
            if (!start.contains(stopsId.get(0)))  {
                Collections.reverse(stopsId);
            }
            int size = stopsId.size();
            if (size < 2) {
                stops.add(size == 1 ? "The same stop for these buildings" : "No way between buildings");
                continue;
            }
            for (Integer id : stopsId) {
                stops.add(stopRepository.getById(id).getName());
            }
            routes.put(key, stops);
        }

        return routes;
    }

    private ArrayList<RouteWithStopsWay> findStopsInRouteWay(List<RouteInfo> routeInfos) {
        ArrayList<RouteWithStopsWay> ways = new ArrayList<>();
        int stopCountDifferenceRange = 2;
        int shortest = Integer.MAX_VALUE - stopCountDifferenceRange;

        for (RouteInfo info : routeInfos) {
            int routeId = info.getRouteId();
            int start = routeToStopRepository.findStopNumberInWay(routeId, info.getStartStopId());
            int finish = routeToStopRepository.findStopNumberInWay(routeId, info.getFinishStopId());

            if (start > finish) {
                int temp = start;
                start = finish;
                finish = temp;
            }

            int distance = finish - start;
            if (distance > shortest + stopCountDifferenceRange) {
                continue;
            }

            if (distance < shortest) {
                if (distance < shortest - stopCountDifferenceRange) {
                    ways.clear();
                }
                shortest = distance;
            }

            ways.add(new RouteWithStopsWay(routeId, gatherStopIds(routeId, start, finish)));
        }

        return ways;
    }

    private List<Integer> gatherStopIds(int routeId, int start, int finish) {
        List<Integer> stopIds = new ArrayList<>();
        for (int i = start; i <= finish; i++) {
            stopIds.add(routeToStopRepository.findStopByNumberInWay(routeId, i));
        }
        return stopIds;
    }

    private ArrayList<RouteInfo> waysBetweenStops(List<Integer> startStopsId, List<Integer> finishStopsId) {
        ArrayList<RouteInfo> routeInfos = new ArrayList<>();

        for (Integer startStopId : startStopsId) {
            List<Integer> sRoutes = routeToStopRepository.findAllRouteIdByStopId(startStopId);
            for (Integer finishStopId : finishStopsId) {
                List<Integer> fRoutes = routeToStopRepository.findAllRouteIdByStopId(finishStopId);
                if (sRoutes != null && fRoutes != null) {
                    List<RouteInfo> commonRoutes = sRoutes.stream().filter(fRoutes::contains)
                            .map(route -> new RouteInfo(route, startStopId, finishStopId))
                            .toList();
                    routeInfos.addAll(commonRoutes);
                }
            }
        }

        return routeInfos;
    }

}
