package com.example.backend.Interfaces;

import com.example.backend.service.TrafficService;

public interface Command
{
        void execute(TrafficService trafficService);
}
