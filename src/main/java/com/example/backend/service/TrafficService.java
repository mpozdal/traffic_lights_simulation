package com.example.backend.service;

import com.example.backend.Interfaces.ITrafficService;
import com.example.backend.model.TrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TrafficService implements ITrafficService {
    private static final Logger logger = LoggerFactory.getLogger(TrafficService.class);

    private final Map<String, Queue<String>> roads;
    private final Map<String, TrafficLight> lights;
    private final Map<String, List<Integer>> waitTimes;
    private List<Map<String, List<String>>> stepStatuses;
    private int timeStep;

    public TrafficService() {
        roads = new ConcurrentHashMap<>();
        lights = new ConcurrentHashMap<>();
        waitTimes = new ConcurrentHashMap<>();
        stepStatuses = new ArrayList<>();
        timeStep = 0;


        for (String direction : List.of("north", "south", "east", "west")) {
            roads.put(direction, new LinkedList<>());
            lights.put(direction, new TrafficLight());
            waitTimes.put(direction, new ArrayList<>());
        }
    }

    public void addVehicle(String vehicleId, String startRoad) {
        roads.get(startRoad).add(vehicleId);
        waitTimes.get(startRoad).add(timeStep);
        logger.info("Vehicle {} added to {}", vehicleId, startRoad);
    }

    public void step() {
        timeStep++;
        logger.info("Simulating step...");

        List<String> leftVehicles = new ArrayList<>();

        adjustTrafficLights();

        for (String road : roads.keySet()) {
            if ("GREEN".equals(lights.get(road).get_color()) && !roads.get(road).isEmpty()) {

                while (!roads.get(road).isEmpty()) {
                    String vehicle = roads.get(road).poll();
                    waitTimes.get(road).remove(0);
                    leftVehicles.add(vehicle);
                    logger.info("Vehicle {} passed from {}", vehicle, road);
                }
            }
        }


        Map<String, List<String>> stepResult = new HashMap<>();
        stepResult.put("leftVehicles", leftVehicles);
        stepStatuses.add(stepResult);


        if (!leftVehicles.isEmpty()) {
            logger.info("Vehicles that left the intersection: {}", leftVehicles);
        } else {
            logger.info("No vehicles left the intersection this step.");
        }
    }

    public void adjustTrafficLights() {
        String maxDelayRoad = calculateAverageDelay();


        for (String road : lights.keySet()) {
            lights.get(road).set_color("RED");
        }

        lights.get(maxDelayRoad).set_color("GREEN");
        logger.info("Green light for {}", maxDelayRoad);
    }

    public String calculateAverageDelay() {
        String maxDelayRoad = "north";
        double maxDelay = 0;

        for (String road : roads.keySet()) {
            List<Integer> times = waitTimes.get(road);
            if (!times.isEmpty()) {
                double avgDelay = times.stream().mapToInt(t -> timeStep - t).average().orElse(0);
                if (avgDelay > maxDelay) {
                    maxDelay = avgDelay;
                    maxDelayRoad = road;
                }
            }
        }
        return maxDelayRoad;
    }

    public String getLightColor(String road) {
        return lights.get(road).get_color();
    }

    public Map<String, Integer> getVehicleCount() {
        Map<String, Integer> vehicleCounts = new HashMap<>();
        for (String road : roads.keySet()) {
            vehicleCounts.put(road, roads.get(road).size());
        }
        return vehicleCounts;
    }


    public List<Map<String, List<String>>> getStepStatuses() {
        return stepStatuses;
    }


    public void processCommands(List<Map<String, String>> commands) {
        for (Map<String, String> command : commands) {
            String type = command.get("type");
            switch (type) {
                case "addVehicle":
                    addVehicle(command.get("vehicleId"), command.get("startRoad"));
                    break;
                case "step":
                    step();
                    break;
                default:
                    logger.warn("Unknown command type: {}", type);
            }
        }
    }
}

