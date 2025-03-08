package com.example.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrafficLight {
    private String _color;

    public TrafficLight() {
        _color = "RED";
    }
}
