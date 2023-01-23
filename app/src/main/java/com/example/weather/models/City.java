package com.example.weather.models;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class City {
    private String name;
    private float lon;
    private float lat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public City(String name, float lat, float lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public static List<City> cities = Arrays.asList(
            new City("Lodz", 51.7687f, 19.4569f),
            new City("London", 51.5085f, -0.1257f),
            new City("Warsaw", 52.2319f, 21.0067f),
            new City("Chicago", 41.8755f, -87.6244f)
    );
}
