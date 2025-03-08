package com.example.backend.Interfaces;

import com.example.backend.model.TrafficLight;
import com.example.backend.service.TrafficService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public interface ITrafficService {
        public void addVehicle(String vehicleId, String startRoad);

        public void step() ;

        public void adjustTrafficLights();
        public String calculateAverageDelay();

        public String getLightColor(String road);

        public Map<String, Integer> getVehicleCount();

        public List<Map<String, List<String>>> getStepStatuses();

        public void processCommands(List<Map<String, String>> commands);
    }


