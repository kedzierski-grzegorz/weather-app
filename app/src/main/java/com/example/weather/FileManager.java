package com.example.weather;

import android.content.Context;

import com.example.weather.models.api.WeatherCache;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileManager {
    public static WeatherCache readCacheData(Context context) {
        try {
            FileInputStream stream = context.openFileInput("current_data.json");
            StringBuilder json = new StringBuilder();
            int c;
            while( (c = stream.read()) != -1){
                json.append((char) c);
            }
            stream.close();

            WeatherCache data = new Gson().fromJson(json.toString(), WeatherCache.class);

            if (Utils.addHoursToJavaUtilDate(data.getSaveDate(), 1).compareTo(new Date()) <= 0) {
                return null;
            } else {
                return data;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeCacheData(Context context, WeatherCache data) {
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
