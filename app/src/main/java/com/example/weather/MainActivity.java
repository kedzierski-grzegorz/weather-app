package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.weather.api.RetrofitClient;
import com.example.weather.models.Units;
import com.example.weather.models.api.WeatherData;
import com.example.weather.api.WeatherService;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.fragments.DailyForecastFragment;
import com.example.weather.fragments.DetailsForecastFragment;
import com.example.weather.fragments.MainForecastDataFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int NUM_PAGES = 3;
    private FragmentStateAdapter pagerAdapter;

    public static boolean settingsChanged = false;
    public static String cityName = "";
    public static float lon = 0;
    public static float lat = 0;
    public static String units = Units.units.get(0);

    private final MutableLiveData<WeatherData> data = new MutableLiveData<>();
    public void setData(WeatherData value){
        data.setValue(value);
    }
    public LiveData<WeatherData> getDate(){
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkIfUserSetCity()) {
            loadData(false);
        }
        MainActivity.settingsChanged = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_refresh:
                loadData(true);
                return true;
            case R.id.btn_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initLayout() {
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

    private boolean checkIfUserSetCity(){
        SharedPreferences preferences = getSharedPreferences("WEATHER", Activity.MODE_PRIVATE);
        if (preferences.contains(getString(R.string.KEY_CITY_NAME)) && preferences.contains(getString(R.string.KEY_CITY_LAT)) && preferences.contains(getString(R.string.KEY_CITY_LON)) && preferences.contains(getString(R.string.KEY_UNITS))) {
            cityName = preferences.getString(getString(R.string.KEY_CITY_NAME), "");
            lon = preferences.getFloat(getString(R.string.KEY_CITY_LON), 0);
            lat = preferences.getFloat(getString(R.string.KEY_CITY_LAT), 0);
            units = preferences.getString(getString(R.string.KEY_UNITS), "metric");
        }

        if (cityName.isEmpty()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return false;
        }

        return true;
    }

    private void loadData(boolean forceReload) {
        WeatherData d = FileManager.readLastCurrentWeather(this);
        if (
                forceReload ||
                d == null ||
                !d.getName().equals(cityName) ||
                settingsChanged ||
                Utils.addHoursToJavaUtilDate(d.getSaveDate(), 1).compareTo(new Date()) <= 0
        ) {
            WeatherService weatherService = RetrofitClient.getRetrofitClient().create(WeatherService.class);

            ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);

            Context ctx = this;

            weatherService.getCurrentWeather(lat, lon, units).enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    WeatherData data = response.body();
                    FileManager.writeLastCurrentWeather(ctx, data);
                    setData(data);
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        } else {
            setData(d);
        }
    }

    private static class ScreenSlidePageAdapter extends FragmentStateAdapter {
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