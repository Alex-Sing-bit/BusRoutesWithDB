package ru.vsu.cs.baklanova.database_interaction;

import ru.vsu.cs.baklanova.database_interaction.repository.*;
import ru.vsu.cs.baklanova.database_interaction.table_objects.*;
import ru.vsu.cs.baklanova.logic.user_service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataBaseGenerator {

    private final IStreetRepository<Street> streetRepository;
    private final IStopRepository<Stop> stopRepository;
    private final IBuildingRepository<Building> buildingRepository;
    private final IUserRepository<User> userRepository;
    private final IRouteRepository<Route> routeRepository;
    private final IBuildingToStopRepository<BuildingToStop> buildingToStopRepository;
    private final IRouteToStopRepository<RouteToStop> routeToStopRepository;
    private final IBusRepository<Bus> busRepository;

    private final Random rand = new Random();

    public DataBaseGenerator(
            IStreetRepository<Street> streetRepository,
            IStopRepository<Stop> stopRepository,
            IBuildingRepository<Building> buildingRepository,
            IUserRepository<User> userRepository,
            IRouteRepository<Route> routeRepository,
            IBuildingToStopRepository<BuildingToStop> buildingToStopRepository,
            IRouteToStopRepository<RouteToStop> routeToStopRepository,
            IBusRepository<Bus> busRepository) {

        this.streetRepository = streetRepository;
        this.stopRepository = stopRepository;
        this.buildingRepository = buildingRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.buildingToStopRepository = buildingToStopRepository;
        this.routeToStopRepository = routeToStopRepository;
        this.busRepository = busRepository;
    }

    public void generateAll() {
        generateStreets();
        generateStops();
        generateBuildings();
        generateUsers();
        generateRoutes();
        setStopsToBuildings();
        setStopsToRoutes();
        generateBuses();
    }

    public void generateStreets() {
        ArrayList<String> names = stringListFromTxt("streets.txt");
        for (String name : names) {
            streetRepository.add(new Street(1, name, StreetTypeEnum.random()));
        }
    }

    public void generateStops() {
        ArrayList<String> names = stringListFromTxt("stops.txt");
        for (String name : names) {
            int ind = rand.nextInt(1, streetRepository.findAll().size() + 1);
            stopRepository.add(new Stop(1, name, ind));
        }
    }

    public void generateBuildings() {
        for (Street street : streetRepository.findAll()) {
            int buildingsNumInStreet = 30;
            for (int i = 0; i < buildingsNumInStreet; i++) {
                buildingRepository.add(new Building(1, i + 1, BuildingTypeEnum.random(), street.getId()));
            }
        }
    }

    public void generateUsers() {
        var names = stringListFromTxt("names.txt");
        long i = 9000000000L;
        for (String name : names) {
            if (name.length() < 2) {
                throw new IllegalArgumentException("Too short user name");
            }
            int ind = rand.nextInt(1, buildingRepository.findAll().size() + 1);
            String phoneNumber = String.format("+7-%03d-%03d-%02d-%02d",
                    (i / 1_000_000_00) % 1000, // первая часть (900-999)
                    (i / 10_000_00) % 1000,    // вторая часть (000-999)
                    (i / 100) % 100,           // третья часть (00-99)
                    i % 100);
            i++;
            String password = "0000";
            userRepository.add(new User(1, name, phoneNumber, UserService.hashPassword(password), ind));
        }
    }

    public void generateRoutes() {
        int routesNum = 102;
        for (int i = 0; i < routesNum; i++) {
            routeRepository.add(new Route(1, i + 1, RouteTypeEnum.random()));
        }
    }

    public void setStopsToBuildings() {
        for (Building building : buildingRepository.findAll()) {
            List<Integer> matchingStopIds = new ArrayList<>();
            List<Integer> nonMatchingStopIds = new ArrayList<>();
            for (Stop stop : stopRepository.findAll()) {
                if (stop.getStreetId() == building.getStreetId()) {
                    matchingStopIds.add(stop.getId());
                } else {
                    nonMatchingStopIds.add(stop.getId());
                }
            }
            if (!matchingStopIds.isEmpty()) {
                int randomMatchingStopId = matchingStopIds.get(rand.nextInt(matchingStopIds.size()));
                buildingToStopRepository.add(new BuildingToStop(1, building.getId(), randomMatchingStopId));
            }
            if (!nonMatchingStopIds.isEmpty()) {
                int randomNonMatchingStopId = nonMatchingStopIds.get(rand.nextInt(nonMatchingStopIds.size()));
                buildingToStopRepository.add(new BuildingToStop(1, building.getId(), randomNonMatchingStopId));
            }
        }
    }

    public void setStopsToRoutes() {
        int rSize = routeRepository.findAll().size();
        int sSize = stopRepository.findAll().size();
        int num = rSize / 2;
        int stopInNumOfRoutes = (rSize * num) / sSize + 1;
        ArrayList<Integer> streetsId = new ArrayList<>();
        for (int i = 0; i < sSize; i++) {
            streetsId.add(i + 1);
        }
        int[] usageCount = new int[sSize];
        for (int i = 0; i < rSize; i++) {
            Collections.shuffle(streetsId);
            Set<Integer> addedStops = new HashSet<>();
            int stopsAdded = 0;

            for (int j = 0; j < sSize && stopsAdded < stopInNumOfRoutes; j++) {
                int stopIndex = streetsId.get(j) - 1;
                if (usageCount[stopIndex] < num && !addedStops.contains(streetsId.get(j))) {
                    int stopId = streetsId.get(j);
                    int number = stopsAdded + 1;
                    routeToStopRepository.add(new RouteToStop(1, i + 1, stopId, number));
                    usageCount[stopIndex]++;
                    addedStops.add(stopId);
                    stopsAdded++;
                }
            }
        }
    }

    public void generateBuses() {
        for (int i = 0; i < 1000; i++) {
            char firstLetter = (char) ('А' + i % 33);
            firstLetter = firstLetter > 'Я' ? 'Я' : firstLetter;
            char secondLetter = (char) rand.nextInt('А', 'Я');
            char thirdLetter = (char) rand.nextInt('А', 'Я');
            int indR = rand.nextInt(1, routeRepository.findAll().size() + 1);
            List<Integer> stops2 = routeToStopRepository.findAll().stream().filter(rs -> rs.getRouteId() == indR)
                    .map(RouteToStop::getStopId).toList();
            if (stops2.isEmpty()) {
                continue;
            }
            int indS = stops2.get(rand.nextInt(0, stops2.size()));
            String number = (i > 99 ? "" + i : i > 9 ? i + "0" : i + "00").substring(0, 3);
            number = firstLetter + number + secondLetter + thirdLetter;
            busRepository.add(new Bus(1, number, indR, indS));
        }
    }

    private static ArrayList<String> stringListFromTxt(String resourceFileName) {
        ArrayList<String> quotedLines = new ArrayList<>();
        try (InputStream inputStream = DataBaseGenerator.class.getClassLoader().getResourceAsStream(resourceFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                quotedLines.add(line.strip());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotedLines;
    }
}
