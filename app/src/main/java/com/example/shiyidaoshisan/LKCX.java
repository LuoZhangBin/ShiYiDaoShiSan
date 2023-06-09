package com.example.shiyidaoshisan;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.LKJC;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LKCX extends AppCompatActivity {

    private ImageView mLKCXCaidan;
    private LinearLayout mHcksl;
    private LinearLayout mXYL;
    private LinearLayout mLXL;
    private LinearLayout mXFL;
    private LinearLayout mYYL;
    private LinearLayout mTCC;
    private TextView mLKCXShijian;
    private TextView mLKCXWengdu;
    private ImageView mLKCXShiuaxing;
    private TextView mLKCXXingqi;
    private LinearLayout mJiaojing1;
    private LinearLayout mJiaojing2;
    private TextView mLKCXShidu;
    private TextView mLKCXPM25;
    private List<LKJC> lkjcs;
    private LinearLayout mHuancenggx1;
    private LinearLayout mHuancenggx2;
    private LinearLayout mHuanchengkxl1;
    private LinearLayout mHuanchengkxl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lkcx);
        initView();
        shijian();
        wendu();
        daolu();

    }

    private void daolu() {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("UserName", "user1");
                    jsonObject.put("RoadId", 0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                new Http().sendResUtil("get_road_status", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s = response.body().string();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            lkjcs = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<LKJC>>() {
                            }.getType());
                            setRoadColor();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        },0,3000);
    }

    private void shijian() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mLKCXShijian.setText(year + "-" + month + "-" + day);
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        mLKCXXingqi.setText("星期" + mWay);
    }

    private void wendu() {

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
                    String s1= String.valueOf(jsonObject1.getInt("temperature"));
                    String s2= String.valueOf(+ jsonObject1.getInt("humidity"));
                    String s3= String.valueOf(+ jsonObject1.getInt("pm25"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLKCXWengdu.setText("温度: " + s1 + "℃");
                            mLKCXShidu.setText("相对湿度:" + s2 + "%");
                            mLKCXPM25.setText("PM2.5:" + s3 + "ug/m³");
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        AnimationDrawable ad, ad2;
        ad = (AnimationDrawable) getResources().getDrawable(R.drawable.duenghua2);
        ad2 = (AnimationDrawable) getResources().getDrawable(R.drawable.duenghua3);
        mLKCXCaidan = (ImageView) findViewById(R.id.LKCX_caidan);
        mXYL = (LinearLayout) findViewById(R.id.XYL);
        mLXL = (LinearLayout) findViewById(R.id.LXL);
        mXFL = (LinearLayout) findViewById(R.id.XFL);
        mYYL = (LinearLayout) findViewById(R.id.YYL);
        mTCC = (LinearLayout) findViewById(R.id.TCC);
        mLKCXShijian = (TextView) findViewById(R.id.LKCX_shijian);
        mLKCXWengdu = (TextView) findViewById(R.id.LKCX_wengdu);
        mLKCXShiuaxing = (ImageView) findViewById(R.id.LKCX_shiuaxing);
        mLKCXShiuaxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wendu();
            }
        });
        mLKCXXingqi = (TextView) findViewById(R.id.LKCX_xingqi);
        mJiaojing1 = (LinearLayout) findViewById(R.id.jiaojing1);
        mJiaojing1.setBackgroundDrawable(ad);
        ad.start();
        mJiaojing2 = (LinearLayout) findViewById(R.id.jiaojing2);
        mJiaojing2.setBackgroundDrawable(ad2);
        ad2.start();
        mLKCXShidu = (TextView) findViewById(R.id.LKCX_shidu);
        mLKCXPM25 = (TextView) findViewById(R.id.LKCX_PM25);
        mHuancenggx1 = (LinearLayout) findViewById(R.id.huancenggx1);
        mHuancenggx2 = (LinearLayout) findViewById(R.id.huancenggx2);
        mHuanchengkxl1 = (LinearLayout) findViewById(R.id.huanchengkxl1);
        mHuanchengkxl2 = (LinearLayout) findViewById(R.id.huanchengkxl2);
    }

    private int getRoadColor(int state) {
        switch (state) {
            case 1:
                return Color.parseColor("#6ab82e");
            case 2:
                return Color.parseColor("#ece93a");
            case 3:
                return Color.parseColor("#f49b25");
            case 4:
                return Color.parseColor("#e33532");
            case 5:
                return Color.parseColor("#b01e23");
            default:
                return Color.WHITE;
        }
    }

    private void setRoadColor() {
        for (int i = 0; i < lkjcs.size(); i++) {
            LKJC lkjc = lkjcs.get(i);
            switch (i) {
                case 0:
                    mXYL.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
                case 1:
                    mLXL.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
                case 2:
                    mYYL.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
                case 3:
                    mXFL.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
                case 4:

                    mHuancenggx2.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
                case 5:
                    mHuancenggx1.setBackgroundColor(getRoadColor(lkjc.getState()));
                    mHuanchengkxl1.setBackgroundColor(getRoadColor(lkjc.getState()));
                    mHuanchengkxl2.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
                case 6:
                    mTCC.setBackgroundColor(getRoadColor(lkjc.getState()));
                    break;
            }
        }

    }
}