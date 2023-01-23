package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.weather.databinding.ActivitySettingsBinding;
import com.example.weather.models.City;
import com.example.weather.models.Units;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    private final String[] cities = City.cities.stream().map(City::getName).toArray(String[]::new);
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Weather - settings");

        initSpinners();

        binding.btnSave.setOnClickListener((v) -> save());

        loadData();
    }

    private void initSpinners() {
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cities);
        binding.locationSpinner.setAdapter(citiesAdapter);

        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Units.units);
        binding.unitsSpinner.setAdapter(unitsAdapter);
    }

    private void loadData() {
        SharedPreferences preferences = getSharedPreferences("WEATHER", Activity.MODE_PRIVATE);

        if (preferences.contains(getString(R.string.KEY_CITY_NAME))) {
            String cityName = preferences.getString(getString(R.string.KEY_CITY_NAME), "");
            int index = Arrays.asList(cities).indexOf(cityName);
            if (index != -1) {
                binding.locationSpinner.setSelection(index);
            }
        }

        if (preferences.contains(getString(R.string.KEY_UNITS))) {
            String units = preferences.getString(getString(R.string.KEY_UNITS), "metric");
            int index = Units.units.indexOf(units);
            if (index != -1) {
                binding.unitsSpinner.setSelection(index);
            }
        }
    }

    private void save() {
        City selectedCity = City.cities.get(binding.locationSpinner.getSelectedItemPosition());
        String selectedUnits = Units.units.get(binding.unitsSpinner.getSelectedItemPosition());

        SharedPreferences preferences = getSharedPreferences("WEATHER", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.KEY_CITY_NAME), selectedCity.getName());
        editor.putFloat(getString(R.string.KEY_CITY_LAT), selectedCity.getLat());
        editor.putFloat(getString(R.string.KEY_CITY_LON), selectedCity.getLon());
        editor.putString(getString(R.string.KEY_UNITS), selectedUnits);

        editor.apply();

        MainActivity.settingsChanged = true;

        finish();
    }
}