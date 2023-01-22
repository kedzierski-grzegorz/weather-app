package com.example.weather.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/data/2.5/weather?appid=171933863d5c1e17e75595f4094b62d5")
    Call<WeatherData> getCurrentWeather(@Query("lat") double lat, @Query("lon") double lon);
}
