package com.example.shiyidaoshisan.shipei;

import android.graphics.Color;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SSXS_Fragment2 extends Fragment {

    List<String> time;
    List<Float> list;
    private TextView mTvTitle;
    private LineChart mLineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ssxs, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        time = new ArrayList<>();
        initView();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                set1();
            }
        },0,3000);
    }
    private void set1(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    String humidity=jsonObject1.getString("humidity");
                    setData(humidity);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setData(String a) {
        float temperature=Float.parseFloat(a);

        if (list.size()==20){
            list.remove(0);
        }
        list.add(temperature);
        System.out.println(list.size());
        List<Entry> entries=new ArrayList<Entry>();
        for(int i = 0; i < list.size(); i++){
            entries.add(new Entry(i, list.get(i)));

        }
        LineDataSet lineDataSet=new LineDataSet(entries,"null");
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleColor(Color.GRAY);
        lineDataSet.setCircleSize(7);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setLineWidth(4);
        lineDataSet.setColor(Color.GRAY);
        LineData lineData=new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mTvTitle.setText("湿度");
        setYAxis();
        setXAxis();
        mLineChart.invalidate();
    }
    public void setXAxis(){
        if (time.size()==20){
            time.remove(0);
        }
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        time.add(format.format(new Date()));
        XAxis xAxis=mLineChart.getXAxis();
        xAxis.setTextSize(30f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setLabelCount(20);
        xAxis.setAxisMinimum(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
    }
    public void setYAxis(){
        mLineChart.getAxisRight().setEnabled(false);
        YAxis yAxis=mLineChart.getAxisLeft();
        yAxis.setTextSize(30f);
    }


    private void initView() {
        mTvTitle = (TextView) getView().findViewById(R.id.tv_title);
        mLineChart = (LineChart) getView().findViewById(R.id.line_chart);
    }
}
