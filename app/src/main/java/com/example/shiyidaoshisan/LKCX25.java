package com.example.shiyidaoshisan;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.C;
import com.example.shiyidaoshisan.fanfa.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LKCX25 extends AppCompatActivity {

    private TextView mPM2525;
    private TextView mWd25;
    private TextView mSd25;
    private TextView mDl1;
    private TextView mDl2;
    private TextView mDl3;
    private TextView mYs1;
    private TextView mYs2;
    private TextView mYs3;
    private ArrayList<C> cc=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lkcx25);
        initView();
        tianqi();
        extracted();
    }

    private void extracted() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("RoadId",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_road_status", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    cc = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<C>>(){}.getType());
                    int lukuan1=cc.get(0).getState();
                    int lukuan2=cc.get(1).getState();
                    int lukuan3=cc.get(2).getState();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            daoluzhangtai(lukuan1, lukuan2, lukuan3);
                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void daoluzhangtai(int lukuan1, int lukuan2, int lukuan3) {
        if (lukuan1 ==1){
            mDl1.setText("1号道路:通畅");
            mYs1.setBackgroundColor(Color.parseColor("#0ebd12"));
        }
        if (lukuan1 ==2){
            mDl1.setText("1号道路:较通畅");
            mYs1.setBackgroundColor(Color.parseColor("#98ed1f"));
        }
        if (lukuan1 ==3){
            mDl1.setText("1号道路:拥挤");
            mYs1.setBackgroundColor(Color.parseColor("#ffff01"));
        }
        if (lukuan1 ==4){
            mDl1.setText("1号道路:堵塞");
            mYs1.setBackgroundColor(Color.parseColor("#ff0103"));
        }
        if (lukuan1 >=5){
            mDl1.setText("1号道路:爆表");
            mYs1.setBackgroundColor(Color.parseColor("#4c060e"));
        }

        if (lukuan2 ==1){
            mDl2.setText("1号道路:通畅");
            mYs2.setBackgroundColor(Color.parseColor("#0ebd12"));
        }
        if (lukuan2 ==2){
            mDl2.setText("1号道路:较通畅");
            mYs2.setBackgroundColor(Color.parseColor("#98ed1f"));
        }
        if (lukuan2 ==3){
            mDl2.setText("1号道路:拥挤");
            mYs2.setBackgroundColor(Color.parseColor("#ffff01"));
        }
        if (lukuan2 ==4){
            mDl2.setText("1号道路:堵塞");
            mYs2.setBackgroundColor(Color.parseColor("#ff0103"));
        }
        if (lukuan2 >=5){
            mDl2.setText("1号道路:爆表");
            mYs2.setBackgroundColor(Color.parseColor("#4c060e"));
        }

        if (lukuan3 ==1){
            mDl3.setText("1号道路:通畅");
            mYs3.setBackgroundColor(Color.parseColor("#0ebd12"));
        }
        if (lukuan3 ==2){
            mDl3.setText("1号道路:较通畅");
            mYs3.setBackgroundColor(Color.parseColor("#98ed1f"));
        }
        if (lukuan3 ==3){
            mDl3.setText("1号道路:拥挤");
            mYs3.setBackgroundColor(Color.parseColor("#ffff01"));
        }
        if (lukuan3 ==4){
            mDl3.setText("1号道路:堵塞");
            mYs3.setBackgroundColor(Color.parseColor("#ff0103"));
        }
        if (lukuan3 >=5){
            mDl3.setText("1号道路:爆表");
            mYs3.setBackgroundColor(Color.parseColor("#4c060e"));
        }
    }

    private void tianqi() {
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
                    String pm25=jsonObject1.optString("pm25");
                    String temperature=jsonObject1.getString("temperature");
                    String humidity=jsonObject1.getString("humidity");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWd25.setText("温 度:"+temperature);
                            mSd25.setText("湿 度:"+humidity);
                            mPM2525.setText("PM2.5:"+pm25);
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mPM2525 = (TextView) findViewById(R.id.PM25_25);
        mWd25 = (TextView) findViewById(R.id.wd25);
        mSd25 = (TextView) findViewById(R.id.sd25);
        mDl1 = (TextView) findViewById(R.id.dl1);
        mDl2 = (TextView) findViewById(R.id.dl2);
        mDl3 = (TextView) findViewById(R.id.dl3);
        mYs1 = (TextView) findViewById(R.id.ys1);
        mYs2 = (TextView) findViewById(R.id.ys2);
        mYs3 = (TextView) findViewById(R.id.ys3);
    }
}