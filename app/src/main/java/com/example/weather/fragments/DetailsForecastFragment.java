package com.example.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.MainActivity;
import com.example.weather.Utils;
import com.example.weather.databinding.FragmentDetailsForecastBinding;
import com.example.weather.models.api.WeatherData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsForecastFragment extends Fragment {

    private MainActivity activity;
    private FragmentDetailsForecastBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity)requireActivity();
        activity.getDate().observe(getViewLifecycleOwner(), this::updateUI);

        binding = FragmentDetailsForecastBinding.inflate(inflater, container, false);

        binding.detailsWrapper.setVisibility(View.INVISIBLE);

        return binding.getRoot();
    }

    private void updateUI(WeatherData data) {
        binding.detailsWrapper.setVisibility(View.VISIBLE);

        binding.txtWindSpeed.setText(Float.toString(data.getWind().getSpeed()) + (MainActivity.units.equals("metric") ? " m/s" : " m/h"));
        binding.txtVisibility.setText(data.getVisibility() + " km");
        binding.txtHumidity.setText(data.getMain().getHumidity() + " %");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date sunrise = Utils.addHoursToJavaUtilDate(new Date(data.getSys().getSunrise() * 1000), 1);
        Date sunset = Utils.addHoursToJavaUtilDate(new Date(data.getSys().getSunset() * 1000), 1);
        binding.txtSunrise.setText(simpleDateFormat.format(sunrise));
        binding.txtSunset.setText(simpleDateFormat.format(sunset));
    }
}