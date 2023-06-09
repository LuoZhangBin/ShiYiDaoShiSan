package com.example.shiyidaoshisan.shipei;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.Http;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SHZS_wendu extends Fragment {
    private List<Float> floats;
    private List<String> list;
    private BarChart mZhuzhuangtu;
    private int s;
    private TextView mShipiWz;
    private String pm25;
    private int pm252=0;
    private boolean ss=true;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            loaDAta();
            return false;
        }
    });

    private void loaDAta() {
        if (Integer.parseInt(pm25)<pm252){
            mShipiWz.setText("过去1分钟空气质量最差值:"+pm252);
        }else {
            pm252= Integer.parseInt(pm25);
            mShipiWz.setText("过去1分钟空气质量最差值:"+pm252);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shzs_wengdu, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        floats = new ArrayList<>();
        initView();
        s = 0;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                set();
            }
        }, 0, 3000);

    }

    private void set() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    pm25 = jsonObject1.optString("pm25");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            do {
                                handler.sendEmptyMessage(0);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }while (ss);
                        }
                    }).start();
                    setLegend(pm25);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mZhuzhuangtu = (BarChart) getView().findViewById(R.id.zhuzhuangtu);
        mShipiWz = (TextView) getView().findViewById(R.id.shipi_wz);
    }

    private void setLegend(String a) {

        Legend legend = mZhuzhuangtu.getLegend();
        legend.setEnabled(false);
        Description description = mZhuzhuangtu.getDescription();
        description.setEnabled(false);
        if (floats.size() == 20) {
            floats.remove(0);
        }
        float ff = Float.parseFloat(a);
        floats.add(ff);
        List<BarEntry> barEntries = new ArrayList<BarEntry>();
        for (int i = 1; i < floats.size(); i++) {
            barEntries.add(new BarEntry(i, floats.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, null);
        BarData ba = new BarData(barDataSet);
        mZhuzhuangtu.setData(ba);
        mZhuzhuangtu.invalidate();
        barDataSet.setColors(Color.GRAY);




        mZhuzhuangtu.getAxisRight().setEnabled(false);
        YAxis yAxis = mZhuzhuangtu.getAxisLeft();

        yAxis.setAxisMinimum(0f);
        yAxis.setTextSize(15f);
        yAxis.setDrawAxisLine(false);


        XAxis xAxis = mZhuzhuangtu.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(list.size());
        xAxis.setTextSize(15f);

        xAxis.setGranularity(1);
        jishu();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
    }

    private void jishu() {
        for (int i = 0; i <= 60; i++) {
            if (i % 3 == 0) {
                if (i <= 9) {
                    list.add("0" + i);
                } else {
                    list.add(String.valueOf(i));
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        ss=false;
        super.onDestroy();
    }
}
