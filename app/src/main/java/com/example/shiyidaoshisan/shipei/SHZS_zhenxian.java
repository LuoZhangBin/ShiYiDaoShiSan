package com.example.shiyidaoshisan.shipei;

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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

public class SHZS_zhenxian extends Fragment {

    private List<Float> floats;
    private boolean ss = true;


    private List<String> strings;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            loaDAta();
            return false;
        }
    });
    private String temperature;
    private int temperature1;
    private int mai = 4000;
    private int max=0;
    private TextView mSssi;
    private LineChart mZhenxiantu;

    private void loaDAta() {
        if (max < Integer.parseInt(temperature)) {
            max = Integer.parseInt(temperature);
        }
        if (mai > Integer.parseInt(temperature)) {
            mai = Integer.parseInt(temperature);
        }
        mSssi.setText("过去一分钟最高气温:" + max + "最低气温:" + mai);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shzs_zhexian, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        strings = new ArrayList<>();
        floats = new ArrayList<>();
        initView();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                extracted();
            }
        },0,3000);

    }

    private void extracted() {
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
                    temperature = jsonObject1.getString("temperature");
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

                            } while (ss);
                        }
                    }).start();
                    setData(temperature);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setData(String a) {
        float temperature= Float.parseFloat(a);
        if (floats.size()==20){
            floats.remove(0);
        }
        floats.add(temperature);
        List<Entry> es=new ArrayList<Entry>();for(int i = 0; i < floats.size(); i++){
            es.add(new Entry(i, floats.get(i)));
            LineDataSet lineDataSet=new LineDataSet(es,null);
            lineDataSet.setDrawValues(false);

            lineDataSet.setCircleColor(Color.GRAY);
            lineDataSet.setCircleSize(7);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setLineWidth(4);
            lineDataSet.setColor(Color.GRAY);
            LineData lineData=new LineData(lineDataSet);

            mZhenxiantu.getLegend().setEnabled(false);
            mZhenxiantu.getDescription().setEnabled(false);
            mZhenxiantu.setData(lineData);

            mZhenxiantu.getAxisRight().setEnabled(false);
            YAxis yAxis=mZhenxiantu.getAxisLeft();
            yAxis.setTextSize(30f);


            XAxis xAxis = mZhenxiantu.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setLabelCount(strings.size());
            xAxis.setGranularity(0);
            xAxis.setTextSize(15f);
            jishu();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));
            mZhenxiantu.invalidate();

        }

    }
    private void jishu() {
        for (int i = 0; i <= 60; i++) {
            if (i % 3 == 0) {
                if (i <= 9) {
                    strings.add("0" + i);
                } else {
                    strings.add(String.valueOf(i));
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        ss = false;
        super.onDestroy();
    }

    private void initView() {
        mSssi = (TextView) getView().findViewById(R.id.sssi);
        mZhenxiantu = (LineChart) getView().findViewById(R.id.zhenxiantu);
    }
}

