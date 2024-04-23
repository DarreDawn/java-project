package com.example.myapplication.fragment;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.Address;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.base.BaseNormalFragment;
import com.example.myapplication.databinding.FragmentHomeBinding;

import org.xutils.ex.DbException;

import java.util.List;

public class HomeFragment extends BaseNormalFragment<FragmentHomeBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        mBinding.txtPopulation.setText(Utils.city);
        saveAddress(Utils.city);
        mBinding.tvPeople.setText("Residents: "+Utils.data);
        if (Utils.weatherData!=null){
            String weatherDataAsString = Utils.weatherData.getName() + "\n" +
                    "Weather now: " + Utils.weatherData.getMain() + "(" + Utils.weatherData.getDescription() + ")\n" +
                    "Temperature: " + Utils.weatherData.getTemperature()+"°c" + "\n" +
                    "Wind speed: " + Utils.weatherData.getWindSpeed() +"m/s"+ "\n";

            mBinding.txtWeather.setText(weatherDataAsString);
            if (Utils.weatherData.getMain().toLowerCase().indexOf("clouds")!=-1){
                Glide.with(this).load(R.drawable.duoyu).into( mBinding.img);
            }else if (Utils.weatherData.getMain().toLowerCase().indexOf("rain")!=-1){
                Glide.with(this).load(R.drawable.rain).into(mBinding.img);
            }else if (Utils.weatherData.getMain().toLowerCase().indexOf("snow")!=-1){
                Glide.with(this).load(R.drawable.snow).into(mBinding.img);
            }else {
                Glide.with(this).load(R.drawable.sun).into(mBinding.img);

            }
            }}

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
                if (city.equals(addressList.get(i).getAddress())){
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
