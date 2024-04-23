package com.example.myapplication;

import android.app.Application;
import android.content.Context;



import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class App extends Application {

    private static App singstance;
    private static Context context;
    public static DbManager dbManager;
    public List<Danxuan> danxuanList =new ArrayList<>();


    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });

    public static Context getContext() {
        return context;
    }

    public static App getInstance(){
        return singstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singstance = this;
        context = this.getApplicationContext();

        x.Ext.init(this);
        if (dbManager == null) {
            dbManager = x.getDb(daoConfig);
        }
        danxuanList.add(new Danxuan("Capital of Finland？","Helsinki","beijing","shanghai","Washington","a"));
        danxuanList.add(new Danxuan("Total population of Finland？","4.556 million","5.556 million","6.556 million","7.556 million","b"));
        danxuanList.add(new Danxuan("Which continent does Finland belong to？","Europe","Asia","Africa","America","a"));
        danxuanList.add(new Danxuan("How many cities are there in Finland？","344","342","342","341","c"));
        danxuanList.add(new Danxuan("Is Finland a developed country？","no","yes","i do not know","All of these answers are true","b"));
        danxuanList.add(new Danxuan("Colours of Finland?","White and blue","White and red","White and black","White","a"));
        danxuanList.add(new Danxuan("Finland gdp per capita?","$20,900","$30,900","$40,900","$50,900","d"));
        danxuanList.add(new Danxuan("The ratio of men to women in Finland is 2,023？","96.8","98.5","97.6","98","c"));
        danxuanList.add(new Danxuan("Largest city in Finland？","Rovaniemi","Helsinki","Lahti","Washington","b"));
        danxuanList.add(new Danxuan("National languages of Finland","English","Chinese","Suomen tasavalta","Republiken Finland","c"));
    }
}
