package com.example.weather.models.api;

import java.util.Date;

public class WeatherData {
    private Date saveDate;

    private String name;

    private WeatherMain main;
    private Weather[] weather;
    private String base;
    private float visibility;
    private WeatherWind wind;
    private WeatherCoord coord;

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public WeatherWind getWind() {
        return wind;
    }

    public void setWind(WeatherWind wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeatherCoord getCoord() {
        return coord;
    }

    public void setCoord(WeatherCoord coord) {
        this.coord = coord;
    }
}
