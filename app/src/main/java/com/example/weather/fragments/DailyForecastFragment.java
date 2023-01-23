package com.example.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.DailyForecastListViewAdapter;
import com.example.weather.MainActivity;
import com.example.weather.R;
import com.example.weather.databinding.FragmentDailyForecastBinding;
import com.example.weather.databinding.FragmentDetailsForecastBinding;
import com.example.weather.models.api.DailyForecast;

public class DailyForecastFragment extends Fragment {

    private MainActivity activity;
    private FragmentDailyForecastBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity)requireActivity();
        activity.getDailyForecast().observe(getViewLifecycleOwner(), this::updateUI);

        binding = FragmentDailyForecastBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    private void updateUI(DailyForecast data) {
        binding.forecastList.setAdapter(new DailyForecastListViewAdapter(activity, data.getList()));
    }
}