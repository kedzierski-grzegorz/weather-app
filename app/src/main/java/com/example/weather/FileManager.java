package com.example.weather;

import android.content.Context;
import android.view.View;

import com.example.weather.api.WeatherData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FileManager {
    public static WeatherData readLastCurrentWeather(Context context) {
        try {
            FileInputStream stream = context.openFileInput("current_data.json");
            StringBuilder json = new StringBuilder();
            int c;
            while( (c = stream.read()) != -1){
                json.append((char) c);
            }
            stream.close();

            WeatherData data = new Gson().fromJson(json.toString(), WeatherData.class);

            if (true) {
                return data;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeLastCurrentWeather(Context context, WeatherData data) {
        try {
            data.setSaveDate(new Date());
            String json = new Gson().toJson(data);
            FileOutputStream fOut = context.openFileOutput("current_data.json",Context.MODE_PRIVATE);
            fOut.write(json.getBytes());
            fOut.close();
        } catch (IOException ignored) {
        }
    }
}
