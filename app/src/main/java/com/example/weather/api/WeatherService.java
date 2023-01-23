package com.example.weather.api;

import com.example.weather.models.api.DailyForecast;
import com.example.weather.models.api.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/data/2.5/weather?appid=171933863d5c1e17e75595f4094b62d5")
    Call<WeatherData> getCurrentWeather(@Query("lat") float lat, @Query("lon") float lon, @Query("units") String units);

    @GET("/data/2.5/forecast?appid=171933863d5c1e17e75595f4094b62d5")
    Call<DailyForecast> getDailyWeather(@Query("lat") float lat, @Query("lon") float lon, @Query("units") String units);
}
