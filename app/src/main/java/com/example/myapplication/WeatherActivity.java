package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.xutils.ex.DbException;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    TextView txtWeather,txtPopulation,tv_people;
    WeatherData weatherData = null;
    ImageView image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherData = (WeatherData) getIntent().getSerializableExtra("weather");
        String city = getIntent().getStringExtra("city");
        String data = getIntent().getStringExtra("data");
        txtWeather = findViewById(R.id.txtWeather);
        tv_people = findViewById(R.id.tv_people);
        image = findViewById(R.id.img);
        txtPopulation = findViewById(R.id.txtPopulation);
        txtPopulation.setText(city);
        saveAddress(city);
        tv_people.setText("Residents: "+data);
        if (weatherData!=null){
            String weatherDataAsString = weatherData.getName() + "\n" +
                    "Weather now: " + weatherData.getMain() + "(" + weatherData.getDescription() + ")\n" +
                    "Temperature: " + weatherData.getTemperature()+"°c" + "\n" +
                    "Wind speed: " + weatherData.getWindSpeed() +"m/s"+ "\n";

            txtWeather.setText(weatherDataAsString);
            if (weatherData.getMain().toLowerCase().indexOf("clouds")!=-1){
                Glide.with(this).load(R.drawable.duoyu).into(image);
            }else if (weatherData.getMain().toLowerCase().indexOf("rain")!=-1){
                Glide.with(this).load(R.drawable.rain).into(image);
            }else if (weatherData.getMain().toLowerCase().indexOf("snow")!=-1){
                Glide.with(this).load(R.drawable.snow).into(image);
            }else {
                Glide.with(this).load(R.drawable.sun).into(image);
            }
        }

    }

    private void saveAddress(String city){
        try {
            List<Address> addressList = App.dbManager.findAll(Address.class);
            if (addressList ==null){
                Address address = new Address();
                address.setId(System.currentTimeMillis()+"");
                address.setAddress(city);
                App.dbManager.save(address);
                return;
            }
            boolean has= false;
            for(int i = 0; i < addressList.size(); i++) {
                if (city.equals(addressList.get(i).address)){
                    has = true;
                }
            }
            if (!has){
                Address address = new Address();
                address.setId(System.currentTimeMillis()+"");
                address.setAddress(city);
                App.dbManager.save(address);
            }
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
    }
}
