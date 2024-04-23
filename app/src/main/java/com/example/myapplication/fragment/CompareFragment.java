package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.myapplication.FragmentActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MunicipalityData;
import com.example.myapplication.MunicipalityDataRetriever;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.WeatherData;
import com.example.myapplication.WeatherDataRetriever;
import com.example.myapplication.base.BaseNormalFragment;
import com.example.myapplication.databinding.FragmentCompareBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompareFragment extends BaseNormalFragment<FragmentCompareBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_compare;
    }

    @Override
    protected void initData() {
        mBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String one= mBinding.etOne.getText().toString();
                String two= mBinding.etTwo.getText().toString();
                if (TextUtils.isEmpty(one) || TextUtils.isEmpty(two)){
                    showToast("please check input");
                    return;
                }
                deal(one,two);


            }
        });

    }




    //显示统计图
    private void showPieChart(PieChart pieChart, List<PieEntry> pieList) {
        PieDataSet dataSet = new PieDataSet(pieList,"Label");

        // 设置颜色list，让不同的块显示不同颜色，下面是我觉得不错的颜色集合，比较亮
        ArrayList<Integer> colors = new ArrayList<Integer>();
        int[] MATERIAL_COLORS = {
                Color.rgb(200, 172, 255)
        };
        for (int c : MATERIAL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);

        // 设置描述，我设置了不显示，因为不好看，你也可以试试让它显示，真的不好看
        Description description = new Description();
        description.setEnabled(false);
        pieChart.setDescription(description);
        //设置半透明圆环的半径, 0为透明
        pieChart.setTransparentCircleRadius(0f);

        //设置初始旋转角度
        pieChart.setRotationAngle(-15);

        //数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1OffsetPercentage(80f);

        //设置连接线的颜色
        dataSet.setValueLineColor(Color.LTGRAY);
        // 连接线在饼状图外面
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // 设置饼块之间的间隔
        dataSet.setSliceSpace(1f);
        dataSet.setHighlightEnabled(true);
        // 不显示图例
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        // 和四周相隔一段距离,显示数据
        pieChart.setExtraOffsets(26, 5, 26, 5);

        // 设置pieChart图表是否可以手动旋转
        pieChart.setRotationEnabled(false);
        // 设置piecahrt图表点击Item高亮是否可用
        pieChart.setHighlightPerTapEnabled(true);
        // 设置pieChart图表展示动画效果，动画运行1.4秒结束
//        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //设置pieChart是否只显示饼图上百分比不显示文字
        pieChart.setDrawEntryLabels(true);
        //是否绘制PieChart内部中心文本
        pieChart.setDrawCenterText(false);
        // 绘制内容value，设置字体颜色大小
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.DKGRAY);

        pieChart.setData(pieData);
        // 更新 piechart 视图
        pieChart.postInvalidate();
    }


    private void deal(String city,String two){
        Context context = getActivity();
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mBinding.txtPopulation.setText(city);
                                        mBinding.tvPeople.setText("Residents: "+municipalityDataArrayList.get(municipalityDataArrayList.size() -1).getPopulation());
                                        if (weatherData!=null){
                                            String weatherDataAsString = weatherData.getName() + "\n" +
                                                    "Weather now: " + weatherData.getMain() + "(" + weatherData.getDescription() + ")\n" +
                                                    "Temperature: " + weatherData.getTemperature()+"°c" + "\n" +
                                                    "Wind speed: " + weatherData.getWindSpeed() +"m/s"+ "\n";

                                            mBinding.txtWeather.setText(weatherDataAsString);
                                            if (weatherData.getMain().toLowerCase().indexOf("clouds")!=-1){
                                                Glide.with(getActivity()).load(R.drawable.duoyu).into( mBinding.img);
                                            }else if (weatherData.getMain().toLowerCase().indexOf("rain")!=-1){
                                                Glide.with(getActivity()).load(R.drawable.rain).into(mBinding.img);
                                            }else if (weatherData.getMain().toLowerCase().indexOf("snow")!=-1){
                                                Glide.with(getActivity()).load(R.drawable.snow).into(mBinding.img);
                                            }else {
                                                Glide.with(getActivity()).load(R.drawable.sun).into(mBinding.img);
                                            }
                                        }}
                                });
                                deal1(city,two,municipalityDataArrayList.get(municipalityDataArrayList.size() -1).getPopulation());
                            }
                        }
        );
    }

    private void deal1(String city,String two,int people){
        Context context = getActivity();
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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mBinding.txtPopulation1.setText(city);
                                        mBinding.tvPeople1.setText("Residents: "+municipalityDataArrayList.get(municipalityDataArrayList.size() -1).getPopulation());
                                        if (weatherData!=null){
                                            String weatherDataAsString = weatherData.getName() + "\n" +
                                                    "Weather now: " + weatherData.getMain() + "(" + weatherData.getDescription() + ")\n" +
                                                    "Temperature: " + weatherData.getTemperature()+"°c" + "\n" +
                                                    "Wind speed: " + weatherData.getWindSpeed() +"m/s"+ "\n";

                                            mBinding.txtWeather1.setText(weatherDataAsString);
                                            if (weatherData.getMain().toLowerCase().indexOf("clouds")!=-1){
                                                Glide.with(getActivity()).load(R.drawable.duoyu).into( mBinding.img1);
                                            }else if (weatherData.getMain().toLowerCase().indexOf("rain")!=-1){
                                                Glide.with(getActivity()).load(R.drawable.rain).into(mBinding.img1);
                                            }else if (weatherData.getMain().toLowerCase().indexOf("snow")!=-1){
                                                Glide.with(getActivity()).load(R.drawable.snow).into(mBinding.img1);
                                            }else {
                                                Glide.with(getActivity()).load(R.drawable.sun).into(mBinding.img1);
                                            }
                                        }}
                                });


                                List<PieEntry> pieList=new ArrayList<>();
                                pieList.add(new PieEntry( municipalityDataArrayList.get(municipalityDataArrayList.size() -1).getPopulation(),two));
                                pieList.add(new PieEntry(people,city));
                                showPieChart(mBinding.pieChart, pieList);
                            }
                        }
        );
    }
}
