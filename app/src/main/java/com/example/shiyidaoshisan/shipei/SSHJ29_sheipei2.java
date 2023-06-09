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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SSHJ29_sheipei2 extends Fragment {
    private TextView mWz129;
    private LineChart mXiangshi29;
    List<String> time;
    List<Float> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sshj19, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        time=new ArrayList<>();
        list=new ArrayList<>();
        initView();
        mWz129.setText("温度");
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                xianshi();
            }
        },0,3000);

    }

    private void xianshi() {
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
                    String pm25=jsonObject1.optString("temperature");
                    setData(pm25);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mWz129 = (TextView) getView().findViewById(R.id.wz1_29);
        mXiangshi29 = (LineChart) getView().findViewById(R.id.xiangshi29);
    }
    private void setData(String a) {
        float temperature= Float.parseFloat(a);
        if (list.size()==20){
            list.remove(0);
        }
        list.add(temperature);
        List<Entry> es=new ArrayList<Entry>();for(int i = 0; i < list.size(); i++){
            es.add(new Entry(i, list.get(i)));
            LineDataSet lineDataSet=new LineDataSet(es,null);
            lineDataSet.setDrawValues(false);

            lineDataSet.setCircleColor(Color.RED);
            lineDataSet.setCircleSize(7);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setLineWidth(4);
            lineDataSet.setColor(Color.GRAY);
            LineData lineData=new LineData(lineDataSet);

            mXiangshi29.getLegend().setEnabled(false);
            mXiangshi29.getDescription().setEnabled(false);
            mXiangshi29.setData(lineData);

            mXiangshi29.getAxisRight().setEnabled(false);
            YAxis yAxis=mXiangshi29.getAxisLeft();
            yAxis.setTextSize(30f);


            XAxis xAxis = mXiangshi29.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setLabelCount(time.size());
            xAxis.setGranularity(0);
            xAxis.setTextSize(15f);

            jishu();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
            mXiangshi29.invalidate();

        }

    }
    private void jishu() {
        for (int i = 0; i <= 60; i++) {
            if (i % 3 == 0) {
                if (i <= 9) {
                    time.add("0" + i);
                } else {
                    time.add(String.valueOf(i));
                }
            }
        }
    }
}
