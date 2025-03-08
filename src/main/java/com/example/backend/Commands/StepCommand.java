package com.example.backend.Commands;

import com.example.backend.Interfaces.Command;
import com.example.backend.service.TrafficService;

public class StepCommand implements Command {

    @Override
    public void execute(TrafficService trafficService) {
        trafficService.step();
    }
}
