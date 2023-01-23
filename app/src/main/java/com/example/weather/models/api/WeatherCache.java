package com.example.weather.models.api;

import java.util.Date;

public class WeatherCache {
    private Date saveDate;
    private WeatherData currentForecast;
    private DailyForecast dailyForecast;

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public WeatherData getCurrentForecast() {
        return currentForecast;
    }

    public void setCurrentForecast(WeatherData currentForecast) {
        this.currentForecast = currentForecast;
    }

    public DailyForecast getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(DailyForecast dailyForecast) {
        this.dailyForecast = dailyForecast;
    }
}
