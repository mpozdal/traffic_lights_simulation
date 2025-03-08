package com.example.backend.Commands;

import com.example.backend.Interfaces.Command;
import com.example.backend.service.TrafficService;

public class AddVehicleCommand implements Command {

    private final String vehicleId;
    private final String startRoad;

    public AddVehicleCommand(String vehicleId, String startRoad) {
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
    }

    @Override
    public void execute(TrafficService trafficService) {
        trafficService.addVehicle(vehicleId, startRoad);
    }
}
