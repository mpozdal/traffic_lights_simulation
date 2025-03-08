package com.example.backend.controller;

import com.example.backend.service.TrafficService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {
    private final TrafficService _trafficService;


    public TrafficController() {
        _trafficService = new TrafficService();
    }

    @PostMapping("/addVehicle")
    public void addVehicle(@RequestParam String vehicleId, @RequestParam String startRoad) {
        _trafficService.addVehicle(vehicleId, startRoad);
    }

    @PostMapping("/step")
    public void stepSimulation() {
        _trafficService.step();
    }

    @GetMapping("/state")
    public Map<String, Object> getTrafficState() {
        Map<String, String> lights = new HashMap<>();
        for (String road : List.of("north", "south", "east", "west")) {
            lights.put(road, _trafficService.getLightColor(road));
        }

        return Map.of(
                "lights", lights,
                "vehicles", _trafficService.getVehicleCount()
        );
    }


    @PostMapping("/processCommands")
    public List<Map<String, List<String>>> processCommands(@RequestBody Map<String, Object> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            List<Map<String, String>> commands = (List<Map<String, String>>) requestBody.get("commands");


            if (commands != null) {
                for (Map<String, String> command : commands) {
                    String type = command.get("type");
                    if ("addVehicle".equals(type)) {
                        String vehicleId = command.get("vehicleId");
                        String startRoad = command.get("startRoad");
                        _trafficService.addVehicle(vehicleId, startRoad);
                    } else if ("step".equals(type)) {
                        _trafficService.step();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _trafficService.getStepStatuses();
    }
}

