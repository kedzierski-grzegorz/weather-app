package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weather.databinding.DailyForecastItemBinding;
import com.example.weather.models.api.DayForecast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyForecastListViewAdapter extends ArrayAdapter<DayForecast> {

    private Context mContext;
    private DayForecast[] items;

    public DailyForecastListViewAdapter(@NonNull Context context, DayForecast[] list) {
        super(context, R.layout.daily_forecast_item, list);
        mContext = context;
        items = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DayForecast data = getItem(position);
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.daily_forecast_item,parent,false);

        ImageView imgIcon = listItem.findViewById(R.id.img_icon);
        imgIcon.setImageResource(Utils.getResId("_" + data.getWeather()[0].getIcon(), R.drawable.class));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Utils.addHoursToJavaUtilDate(new Date(data.getDt() * 1000), 1);
        TextView txtDate = listItem.findViewById(R.id.txt_date);
        txtDate.setText(simpleDateFormat.format(date));

        TextView txtTemp = listItem.findViewById(R.id.txt_temp);
        String tempSymbol = MainActivity.units.equals("metric") ? " °C" : " °F";
        txtTemp.setText(data.getMain().getTemp() + tempSymbol);

        return listItem;
    }
}
