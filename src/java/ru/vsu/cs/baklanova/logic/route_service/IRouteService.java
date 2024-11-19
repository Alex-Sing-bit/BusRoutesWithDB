package ru.vsu.cs.baklanova.logic.route_service;

import java.util.ArrayList;
import java.util.Map;

public interface IRouteService {
    Map<Integer, ArrayList<String>> findWayBetween(String streetName1, int number1,
                                                   String streetName2, int number2);
}
