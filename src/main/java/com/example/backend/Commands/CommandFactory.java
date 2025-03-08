package com.example.backend.Commands;

import com.example.backend.Interfaces.Command;

import java.util.Map;

public class CommandFactory {

    public static Command createCommand(String type, Map<String, String> commandData) {
        switch (type) {
            case "addVehicle":
                return new AddVehicleCommand(commandData.get("vehicleId"), commandData.get("startRoad"));
            case "step":
                return new StepCommand();
            default:
                return null;
        }
    }
}
