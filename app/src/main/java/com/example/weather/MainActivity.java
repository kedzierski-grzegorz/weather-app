package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.weather.api.RetrofitClient;
import com.example.weather.api.WeatherData;
import com.example.weather.api.WeatherService;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.fragments.DailyForecastFragment;
import com.example.weather.fragments.DetailsForecastFragment;
import com.example.weather.fragments.MainForecastDataFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int NUM_PAGES = 3;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_1, new MainForecastDataFragment())
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_2, new DetailsForecastFragment())
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_3, new DailyForecastFragment())
                    .commit();
        } else {
            pagerAdapter = new ScreenSlidePageAdapter(this);
            binding.viewPager.setAdapter(pagerAdapter);
            new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
                if (position == 0)
                    tab.setText("Main data");
                else if (position == 1)
                    tab.setText("Details");
                else
                    tab.setText("Daily forecast");
            }).attach();
        }
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

    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(MainActivity mainActivity) {
            super(mainActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new MainForecastDataFragment();
                case 1:
                    return new DetailsForecastFragment();
                case 2:
                    return new DailyForecastFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}