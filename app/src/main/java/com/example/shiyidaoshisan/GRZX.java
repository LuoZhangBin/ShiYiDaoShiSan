package com.example.shiyidaoshisan;

import static android.os.Build.ID;
import static android.provider.Telephony.Mms.Part.NAME;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.shipei.GRZX_shipaiqi1;
import com.example.shiyidaoshisan.shipei.GRZX_shipaiqi2;
import com.example.shiyidaoshisan.shipei.GRZX_shipaiqi3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
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

public class GRZX extends AppCompatActivity implements View.OnClickListener {

    private TextView mGRZXWz1;
    private TextView mGRZXWz2;
    private TextView mGRZXWz3;
    private ViewPager mHd;
    private List<Fragment> mFragmentList=new ArrayList<Fragment>();
    private SHZS.FragmentAdapter fragmentAdapter;
    private GRZX_shipaiqi1 shipaiqi1;
    private GRZX_shipaiqi2 shipaiqi2;
    private GRZX_shipaiqi3 shipaiqi3;
    private List<String> plate;
    private String user;
    private int yvzhi;
    private int balance;
    private int yvei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzx);
        initView();

        fragmentAdapter=new SHZS.FragmentAdapter(this.getSupportFragmentManager(), mFragmentList) ;
        mHd.setOffscreenPageLimit(3);
        mHd.setAdapter(fragmentAdapter);
        mHd.setCurrentItem(0);
        mHd.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                piehuan(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        yvzhi();
        ynei();
        extracted();



    }

    private void piehuan(int position) {
        if (position==0){
            mGRZXWz1.setBackgroundResource(R.drawable.xiahuaxian);
            mGRZXWz2.setBackgroundResource(0);
            mGRZXWz3.setBackgroundResource(0);
        }
        if (position==1){
            mGRZXWz1.setBackgroundResource(0);
            mGRZXWz2.setBackgroundResource(R.drawable.xiahuaxian);
            mGRZXWz3.setBackgroundResource(0);
        }
        if (position==2){
            mGRZXWz1.setBackgroundResource(0);
            mGRZXWz2.setBackgroundResource(0);
            mGRZXWz3.setBackgroundResource(R.drawable.xiahuaxian);
        }
    }

    private void extracted() {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (yvzhi<balance){

                    String ss="阈值警告";
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //开辟一个通道
                        NotificationChannel mChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_LOW);
                        notificationManager.createNotificationChannel(mChannel);
                        Notification.Builder builder = new Notification.Builder(GRZX.this);
                        //设置属性
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        builder.setContentTitle("警告!");
                        builder.setContentText(ss);
                        //这个要和创建通道的ID一致
                        builder.setChannelId(ID);

                        //创建对象,发送的就是这个对象
                        Notification build = builder.build();
                        notificationManager.notify(1, build);
                    }
                }
            }
        },0,3000);
    }


    private void ynei(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("plate","鲁A10002");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_balance_c", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    yvei = jsonObject1.optInt("balance");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void initView() {
        mGRZXWz1 = (TextView) findViewById(R.id.GRZX_wz1);
        mGRZXWz2 = (TextView) findViewById(R.id.GRZX_wz2);
        mGRZXWz3 = (TextView) findViewById(R.id.GRZX_wz3);
        mHd = (ViewPager) findViewById(R.id.hd);
        mGRZXWz1.setOnClickListener(this);
        mGRZXWz2.setOnClickListener(this);
        mGRZXWz3.setOnClickListener(this);

        shipaiqi1 = new GRZX_shipaiqi1();
        shipaiqi2 = new GRZX_shipaiqi2();
        shipaiqi3 = new GRZX_shipaiqi3();
        mFragmentList.add(shipaiqi1);
        mFragmentList.add(shipaiqi2);
        mFragmentList.add(shipaiqi3);
    }
    private void yvzhi() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.optJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                    yvzhi = jsonObject2.getInt("threshold");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.GRZX_wz1:
                mHd.setCurrentItem(0,true);
                break;
            case R.id.GRZX_wz2:
                mHd.setCurrentItem(1,true);
                break;
            case R.id.GRZX_wz3:
                mHd.setCurrentItem(2,true);
                break;
        }
    }
}