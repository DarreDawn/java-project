package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *
 */
public class MainActivity extends AppCompatActivity implements IAddressListener{



    private EditText editMunicipalityName;

    AddressAdapter addressAdapter;
    RecyclerView rlAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMunicipalityName = findViewById(R.id.editMunicipalityName);
        rlAddress = findViewById(R.id.rl_address);
        addressAdapter = new AddressAdapter(this);
        rlAddress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rlAddress.addItemDecoration(new MyItemDecoration(10));
        rlAddress.setAdapter(addressAdapter);
        getList();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void getList(){
        try {
            List<Address> addressList = App.dbManager.findAll(Address.class);
            addressAdapter.setNewData(addressList);
            addressAdapter.notifyDataSetChanged();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
    }


    public void people(View view){
        startActivity(new Intent(MainActivity.this, PeopleActivity.class));
    }
    public void employment(View view){
        startActivity(new Intent(MainActivity.this, EmploymentActiivty.class));
    }
    public void workplace(View view){
        startActivity(new Intent(MainActivity.this, WorkplaceActivity.class));
    }

    public void onSearchButtonClick(View view) {

        String city = editMunicipalityName.getText().toString();
        deal(city);
    }


    private void deal(String city){
        Context context = this;
        MunicipalityDataRetriever municipalityDataRetriever = new MunicipalityDataRetriever();
        WeatherDataRetriever weatherDataRetriever = new WeatherDataRetriever();

        // Here we fetch the municipality data in a background service, so that we do not disturb the UI
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
                            @Override
                            public void run() {
                                MunicipalityDataRetriever.getMunicipalityCodesMap();
                                ArrayList<MunicipalityData> municipalityDataArrayList = municipalityDataRetriever.getData(context, city);

                                if (municipalityDataArrayList == null) {
                                    return;
                                }
                                WeatherData weatherData = weatherDataRetriever.getData(city);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent= new Intent(MainActivity.this, FragmentActivity.class);
                                        Utils.data = String.valueOf(municipalityDataArrayList.get(municipalityDataArrayList.size() -1).getPopulation());
                                        Utils.city = editMunicipalityName.getText().toString();
                                        Utils.weatherData = weatherData;;
                                        startActivity(intent);
                                    }
                                });

                            }
                        }
        );
    }
    @Override
    public void onAddressClick(Address address) {
        deal(address.address);
    }
}
