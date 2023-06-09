package com.example.shiyidaoshisan;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SHZS27 extends AppCompatActivity {

    private TextView mPM2527;
    private TextView mWd27;
    private TextView mSd27;
    private TextView mWz127;
    private TextView mWz227;
    private TextView mWz327;
    private TextView mWz427;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shzs27);
        initView();
        Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                extracted();
            }
        },0,3000);



    }

    private void extracted() {
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
                    int pm25=jsonObject1.getInt("pm25");
                    String temperature=jsonObject1.getString("temperature");
                    String humidity=jsonObject1.getString("humidity");
                    int illumination=jsonObject1.getInt("illumination");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWd27.setText("温 度:"+temperature);
                                    mSd27.setText("湿 度:"+humidity);
                                    mPM2527.setText("PM2.5:"+pm25+"");
                                    if (illumination<=1500){
                                        mWz127.setText("非常弱");
                                        mWz127.setTextColor(Color.parseColor("#000000"));
                                        mWz227.setText("您无须担心紫外线");
                                    }
                                    if (illumination<=3000&&illumination>1500){
                                        mWz127.setText("弱");
                                        mWz127.setTextColor(Color.parseColor("#000000"));
                                        mWz227.setText("外出适当涂抹低倍数防晒霜");
                                    }
                                    if (illumination<=1500){
                                        mWz127.setText("强");
                                        mWz127.setTextColor(Color.parseColor("#ff0000"));
                                        mWz227.setText("外出需要涂抹中倍数防晒霜");
                                    }

                                    if (pm25<=100){
                                        mWz327.setText("良好");
                                        mWz327.setTextColor(Color.parseColor("#000000"));
                                        mWz427.setText("气象条件会对晨练影响不大");
                                    }
                                    if (pm25<=200&&pm25>100){
                                        mWz327.setText("轻度");
                                        mWz327.setTextColor(Color.parseColor("#000000"));
                                        mWz427.setText("受天气影响，较不宜晨练");
                                    }
                                    if (pm25<=300&&pm25>200){
                                        mWz327.setText("重度");
                                        mWz327.setTextColor(Color.parseColor("#ff0000"));
                                        mWz427.setText("减少外出，出行注意戴口罩");
                                    }
                                    if (pm25>300){
                                        mWz327.setText("爆表");
                                        mWz327.setTextColor(Color.parseColor("#ff0000"));
                                        mWz427.setText("停止一切外出活动");
                                    }
                                }
                            });


                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mPM2527 = (TextView) findViewById(R.id.PM25_27);
        mWd27 = (TextView) findViewById(R.id.wd27);
        mSd27 = (TextView) findViewById(R.id.sd27);
        mWz127 = (TextView) findViewById(R.id.wz1_27);
        mWz227 = (TextView) findViewById(R.id.wz2_27);
        mWz327 = (TextView) findViewById(R.id.wz3_27);
        mWz427 = (TextView) findViewById(R.id.wz4_27);
    }
}