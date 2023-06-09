package com.example.shiyidaoshisan;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.SHZS_WD;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SHZS24 extends AppCompatActivity {

    private TextView mSHZSWengdu24;
    private TextView mSHZSWengdu224;
    private ImageView mSHZSShuaxing24;
    private LineChart mSHZSWengduxiansh24;
    private List<SHZS_WD> wds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shzs24);
        initView();
        wenduxianshi();
    }

    private void initView() {
        mSHZSWengdu24 = (TextView) findViewById(R.id.SHZS_wengdu_24);
        mSHZSWengdu224 = (TextView) findViewById(R.id.SHZS_wengdu2_24);
        mSHZSShuaxing24 = (ImageView) findViewById(R.id.SHZS_shuaxing24);
        mSHZSWengduxiansh24 = (LineChart) findViewById(R.id.SHZS_wengduxiansh24);
    }
    private void wenduxianshi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_weather_info", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSHZSWengdu24.setText(jsonObject1.optInt("temperature") + "°");
                            JSONArray jsonArray = jsonObject1.optJSONArray("ROWS_DETAIL");
                            mSHZSWengdu224.setText("今天:" + jsonArray.optJSONObject(1).optString("interval") + "°C");
                            wds = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<SHZS_WD>>() {
                            }.getType());
                            setWeatherChart();
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setWeatherChart() {
        List<Entry> maxEntry = new ArrayList<>();
        List<Entry> minEntry = new ArrayList<>();
        for (int i = 0; i < wds.size(); i++) {
            String[] arr = wds.get(i).getInterval().split("~");
            maxEntry.add(new Entry(i, Integer.parseInt(arr[1])));
            minEntry.add(new Entry(i, Integer.parseInt(arr[0])));
        }
        LineDataSet dataSet = new LineDataSet(maxEntry, "");
        LineDataSet dataSet1 = new LineDataSet(minEntry, "");

        dataSet.setCircleColor(Color.RED);
        dataSet.setColor(Color.RED);
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleHoleRadius(5);
        dataSet.setLineWidth(4);

        dataSet1.setCircleColor(Color.BLUE);
        dataSet1.setColor(Color.BLUE);
        dataSet1.setDrawCircleHole(false);
        dataSet1.setCircleHoleRadius(5);
        dataSet1.setLineWidth(4);

        List<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        iLineDataSets.add(dataSet1);
        LineData data = new LineData(iLineDataSets);

        mSHZSWengduxiansh24.setData(data);
        mSHZSWengduxiansh24.getAxisRight().setEnabled(false);
        YAxis yAxis = mSHZSWengduxiansh24.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);


        mSHZSWengduxiansh24.animateXY(1500, 1000);
        mSHZSWengduxiansh24.getXAxis().setEnabled(false);
        mSHZSWengduxiansh24.getLegend().setEnabled(false);
        mSHZSWengduxiansh24.getDescription().setEnabled(false);
        mSHZSWengduxiansh24.setTouchEnabled(false);
        mSHZSWengduxiansh24.invalidate();
    }
}