package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.weather.api.RetrofitClient;
import com.example.weather.api.WeatherData;
import com.example.weather.api.WeatherService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFiles();
    }

    private void testFiles() {
        WeatherData data = FileManager.readLastCurrentWeather(this);

        WeatherData newData = new WeatherData();
        newData.setLat(10);
        newData.setLon(2);
        FileManager.writeLastCurrentWeather(this, newData);
    }

    private void test() throws IOException {
        WeatherService weatherService = RetrofitClient.getRetrofitClient().create(WeatherService.class);

        ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        weatherService.getCurrentWeather(33.44, -94.04).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData data = response.body();
                int t = 1;
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}