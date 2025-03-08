package com.example.backend.Commands;

import com.example.backend.Interfaces.Command;
import com.example.backend.service.TrafficService;

import java.util.List;
import java.util.Map;

public class CommandProcessor {

    private final TrafficService trafficService;

    public CommandProcessor() {
        this.trafficService = new TrafficService();
    }

    public List<Map<String, List<String>>> processCommands(Map<String, Object> requestBody) {
        List<Map<String, String>> commands = (List<Map<String, String>>) requestBody.get("commands");

        if (commands != null) {
            for (Map<String, String> command : commands) {
                String type = command.get("type");
                Command parsedCommand = CommandFactory.createCommand(type, command);
                if (parsedCommand != null) {
                    parsedCommand.execute(trafficService);
                }
            }
        }
        return trafficService.getStepStatuses();
    }
}

