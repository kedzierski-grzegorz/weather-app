package com.example.weather.models.api;

import java.util.Date;

public class DailyForecast {
    private int cnt;
    private WeatherCity city;
    private DayForecast[] list;


    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public WeatherCity getCity() {
        return city;
    }

    public void setCity(WeatherCity city) {
        this.city = city;
    }

    public DayForecast[] getList() {
        return list;
    }

    public void setList(DayForecast[] list) {
        this.list = list;
    }
}
