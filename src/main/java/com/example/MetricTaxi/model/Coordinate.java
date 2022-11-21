package com.example.MetricTaxi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate {
    public String longitude;
    public String latitude;

    @Override
    public String toString() {
        return  longitude + "," + latitude;
    }
}
