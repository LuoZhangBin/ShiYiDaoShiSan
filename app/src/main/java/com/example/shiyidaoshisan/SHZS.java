package com.example.shiyidaoshisan;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.SHZS_WD;
import com.example.shiyidaoshisan.shipei.SHZS_wendu;
import com.example.shiyidaoshisan.shipei.SHZS_zhenxian;
import com.example.shiyidaoshisan.shipei.SHZS_zhenxian2;
import com.example.shiyidaoshisan.shipei.SHZS_zhenxian3;
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

public class SHZS extends AppCompatActivity implements View.OnClickListener {

    private ImageView mCLWZCaidan;
    private TextView mSHZSWengdu;
    private TextView mSHZSWengdu2;
    private ImageView mSHZSShuaxing;
    private LineChart mSHZSWengduxiansh;
    private TextView mSHZSWz1;
    private TextView mSHZSTishi1;
    private TextView mSHZSWz2;
    private TextView mSHZSTishi2;
    private TextView mSHZSWz3;
    private TextView mSHZSTishi3;
    private TextView mSHZSWz4;
    private TextView mSHZSTishi4;
    private TextView mSHZSWz5;
    private TextView mSHZSTishi5;
    private ViewPager mViewPager;
    private List<SHZS_WD> wds = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private int temperature;
    private int humidity;
    private int illumination;
    private int co2;
    private int pm25;
    private SHZS_wendu wendu;
    private SHZS_zhenxian zhenxian;
    private SHZS_zhenxian2 zhenxian2;
    private SHZS_zhenxian3 zhenxian3;
    private FragmentAdapter fragmentAdapter;
    private TextView mKqzl;
    private TextView mSd;
    private TextView mXdsd;
    private TextView mEyht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shzs);
        initView();
        wenduxianshi();
        tianqishuzhi();
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                beijing(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void beijing(int pos) {
        if (pos==0){
            mKqzl.setBackgroundResource(R.drawable.beij5);
            mSd.setBackgroundResource(0);
            mEyht.setBackgroundResource(0);
            mXdsd.setBackgroundResource(0);
        }
        if (pos==1){
            mKqzl.setBackgroundResource(0);
            mSd.setBackgroundResource(R.drawable.beij5);
            mEyht.setBackgroundResource(0);
            mXdsd.setBackgroundResource(0);
        }
        if (pos==2){
            mKqzl.setBackgroundResource(0);
            mSd.setBackgroundResource(0);
            mEyht.setBackgroundResource(0);
            mXdsd.setBackgroundResource(R.drawable.beij5);
        }
        if (pos==3){
            mKqzl.setBackgroundResource(0);
            mSd.setBackgroundResource(0);
            mEyht.setBackgroundResource(R.drawable.beij6);
            mXdsd.setBackgroundResource(0);
        }
    }

    private void tianqishuzhi() {
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
                    Log.v("55555555", String.valueOf(jsonObject1.getInt("illumination")));
                    temperature = jsonObject1.getInt("temperature");
                    humidity = jsonObject1.getInt("humidity");
                    illumination = jsonObject1.getInt("illumination");
                    co2 = jsonObject1.getInt("co2");
                    pm25 = jsonObject1.getInt("pm25");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (illumination <= 1000 && illumination > 0) {
                                mSHZSWz1.setText("弱(" + illumination + ")");
                                mSHZSTishi1.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
                            } else if (illumination > 1000 && illumination <= 3000) {
                                mSHZSWz1.setText("中等(" + illumination + ")");
                                mSHZSTishi1.setText("涂擦SPF大于15、PA+防晒护肤品");
                            } else {
                                mSHZSWz1.setText("强(" + illumination + ")");
                                mSHZSTishi1.setText("尽量减少外出，需要涂抹高倍数防晒霜");
                            }

                            if (temperature <= 8) {
                                mSHZSWz2.setText("较易发(" + temperature + ")");
                                mSHZSTishi2.setText("温度低，风较大，较易发生感冒，注意防护");
                            } else {
                                mSHZSWz2.setText("少发(" + temperature + ")");
                                mSHZSTishi2.setText("无明显降温，感冒机率较低");
                            }

                            if (temperature <= 12) {
                                mSHZSWz3.setText("冷(" + temperature + ")");
                                mSHZSTishi3.setText("建议穿长袖衬衫、单裤等服装");
                            } else if (temperature > 12 && temperature <= 21) {
                                mSHZSWz3.setText("舒适(" + temperature + ")");
                                mSHZSTishi3.setText("建议穿短袖衬衫、单裤等服装");
                            } else {
                                mSHZSWz3.setText("热(" + temperature + ")");
                                mSHZSTishi3.setText("适合穿T恤、短薄外套等夏季服装");
                            }

                            if (co2 <= 3000) {
                                mSHZSWz4.setText("适宜(" + co2 + ")");
                                mSHZSTishi4.setText("气候适宜，推荐您进行户外运动");
                            } else if (co2 > 3000 && co2 <= 6000) {
                                mSHZSWz4.setText("中(" + co2 + ")");
                                mSHZSTishi4.setText("易感人群应适当减少室外活动");
                            } else {
                                mSHZSWz4.setText("较不宜(" + co2 + ")");
                                mSHZSTishi4.setText("空气氧气含量低，请在室内进行休闲运动");
                            }

                            if (pm25 <= 30) {
                                mSHZSWz5.setText("优(" + pm25 + ")");
                                mSHZSTishi5.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
                            } else if (pm25 > 30 && pm25 <= 100) {
                                mSHZSWz5.setText("良(" + pm25 + ")");
                                mSHZSTishi5.setText("易感人群应适当减少室外活动");
                            } else {
                                mSHZSWz5.setText("污染(" + pm25 + ")");
                                mSHZSTishi5.setText("空气质量差，不适合户外活动");
                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                            mSHZSWengdu.setText(jsonObject1.optInt("temperature") + "°");
                            JSONArray jsonArray = jsonObject1.optJSONArray("ROWS_DETAIL");
                            mSHZSWengdu2.setText("今天:" + jsonArray.optJSONObject(1).optString("interval") + "°C");
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

    private void initView() {
        mCLWZCaidan = (ImageView) findViewById(R.id.CLWZ_caidan);
        mSHZSWengdu = (TextView) findViewById(R.id.SHZS_wengdu);
        mSHZSWengdu2 = (TextView) findViewById(R.id.SHZS_wengdu2);
        mSHZSShuaxing = (ImageView) findViewById(R.id.SHZS_shuaxing);
        mSHZSShuaxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wenduxianshi();
                tianqishuzhi();
            }
        });
        mSHZSWengduxiansh = (LineChart) findViewById(R.id.SHZS_wengduxiansh);
        mSHZSWz1 = (TextView) findViewById(R.id.SHZS_wz1);
        mSHZSTishi1 = (TextView) findViewById(R.id.SHZS_tishi1);
        mSHZSWz2 = (TextView) findViewById(R.id.SHZS_wz2);
        mSHZSTishi2 = (TextView) findViewById(R.id.SHZS_tishi2);
        mSHZSWz3 = (TextView) findViewById(R.id.SHZS_wz3);
        mSHZSTishi3 = (TextView) findViewById(R.id.SHZS_tishi3);
        mSHZSWz4 = (TextView) findViewById(R.id.SHZS_wz4);
        mSHZSTishi4 = (TextView) findViewById(R.id.SHZS_tishi4);
        mSHZSWz5 = (TextView) findViewById(R.id.SHZS_wz5);
        mSHZSTishi5 = (TextView) findViewById(R.id.SHZS_tishi5);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        wendu = new SHZS_wendu();
        zhenxian = new SHZS_zhenxian();
        zhenxian2 = new SHZS_zhenxian2();
        zhenxian3 = new SHZS_zhenxian3();
        fragments.add(wendu);
        fragments.add(zhenxian);
        fragments.add(zhenxian2);
        fragments.add(zhenxian3);
        mKqzl = (TextView) findViewById(R.id.kqzl);
        mSd = (TextView) findViewById(R.id.sd);
        mXdsd = (TextView) findViewById(R.id.xdsd);
        mEyht = (TextView) findViewById(R.id.eyht);
        mKqzl.setOnClickListener(this);
        mSd .setOnClickListener(this);
        mXdsd.setOnClickListener(this);
        mEyht.setOnClickListener(this);
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

        mSHZSWengduxiansh.setData(data);
        mSHZSWengduxiansh.getAxisRight().setEnabled(false);
        YAxis yAxis = mSHZSWengduxiansh.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawLabels(false);


        mSHZSWengduxiansh.animateXY(1500, 1000);
        mSHZSWengduxiansh.getXAxis().setEnabled(false);
        mSHZSWengduxiansh.getLegend().setEnabled(false);
        mSHZSWengduxiansh.getDescription().setEnabled(false);
        mSHZSWengduxiansh.setTouchEnabled(false);
        mSHZSWengduxiansh.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kqzl:
                mViewPager.setCurrentItem(0,true);
                break;
            case R.id.sd:
                mViewPager.setCurrentItem(1,true);
                break;
            case R.id.xdsd:
                mViewPager.setCurrentItem(2,true);
                break;
            case R.id.eyht:
                mViewPager.setCurrentItem(3,true);
                break;
        }
    }

    public static class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = new ArrayList<>();

        public FragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}