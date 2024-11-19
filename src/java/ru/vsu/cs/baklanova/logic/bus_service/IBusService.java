package ru.vsu.cs.baklanova.logic.bus_service;

import java.util.Map;

public interface IBusService {
    Map<String, Character> getBusesOnWay(int routeNumber);
}
