package com.example.weather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.MainActivity;
import com.example.weather.R;
import com.example.weather.Utils;
import com.example.weather.databinding.FragmentMainForecastDataBinding;
import com.example.weather.models.api.WeatherData;

import java.text.SimpleDateFormat;

public class MainForecastDataFragment extends Fragment {

    private MainActivity activity;
    private FragmentMainForecastDataBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (MainActivity)requireActivity();
        activity.getDate().observe(getViewLifecycleOwner(), this::updateUI);
        binding = FragmentMainForecastDataBinding.inflate(inflater, container, false);

        binding.mainDataWrapper.setVisibility(View.INVISIBLE);

        return binding.getRoot();
    }

    private void updateUI(WeatherData data) {
        binding.mainDataWrapper.setVisibility(View.VISIBLE);

        binding.txtCity.setText(data.getName());
        binding.txtCoor.setText(String.format("lat: %s lon: %s", data.getCoord().getLat(), data.getCoord().getLon()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        binding.txtTime.setText(simpleDateFormat.format(data.getSaveDate()));

        String tempSymbol = MainActivity.units.equals("metric") ? " °C" : " °F";
        binding.txtTemp.setText(Float.toString(data.getMain().getTemp()) + tempSymbol);
        binding.txtPress.setText(Float.toString(data.getMain().getPressure()) + " hPa");
        binding.txtDesc.setText(data.getWeather()[0].getDescription());

        binding.imgIcon.setImageResource(Utils.getResId("_" + data.getWeather()[0].getIcon(), R.drawable.class));
    }
}