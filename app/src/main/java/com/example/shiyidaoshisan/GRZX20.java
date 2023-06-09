package com.example.shiyidaoshisan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GRZX20 extends AppCompatActivity {

    private ImageView mGrxxTouxiang2;
    private TextView mYhm;
    private TextView mGrxxWz12;
    private TextView mGrxxWz22;
    private TextView mGrxxWz32;
    private TextView mGrxxWz42;
    private TextView mGrxxWz52;
    private ImageView mTupian12;
    private TextView mChepai2;
    private ImageView mTupian22;
    private TextView mChepai22;
    private ImageView mTupian32;
    private TextView mChepai32;
    private ImageView mTupian42;
    private TextView mChepai42;
    private String user;
    private String ss;
    private TextView mYe1;
    private TextView mYe3;
    private TextView mYe2;
    private TextView mYe4;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzx20);
        initView();
        SharedPreferences sp = getSharedPreferences("name", Context.MODE_PRIVATE);
        user = sp.getString("usname", null);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", user);
            jsonObject.put("Password", "1234");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_root", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    String name = jsonObject1.getString("name");
                    String sex = jsonObject1.getString("sex");
                    String idnumber = jsonObject1.getString("idnumber");
                    String registered_time = jsonObject1.getString("registered_time");
                    String tel = jsonObject1.getString("tel");
                    String root = jsonObject1.getString("root");
                    List<String> plate = new Gson().fromJson(jsonObject1.optJSONArray("plate").toString(), new TypeToken<List<String>>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mYhm.setText("用户名:" + user);
                            mGrxxWz12.setText("姓名:" + name);
                            mGrxxWz22.setText("性别:" + sex);
                            if (sex.equals("男")) {
                                mGrxxTouxiang2.setBackgroundResource(R.drawable.touxiang_2);
                            } else {
                                mGrxxTouxiang2.setBackgroundResource(R.drawable.touxiang_1);
                            }
                            mGrxxWz32.setText("手机号码:" + tel);
                            mGrxxWz42.setText("身份证号:" + idnumber);
                            mGrxxWz52.setText("注册时间:" + registered_time);
                            for (int i = 0; i < plate.size(); i++) {
                                if (i == 0) {
                                    mChepai2.setText(plate.get(0));
                                    ss = (String) mChepai2.getText();
                                    yvei();
                                    mYe1.setText("余额:"+balance);

                                }
                                if (i == 1) {
                                    mChepai22.setText(plate.get(1));
                                    ss = (String) mChepai22.getText();
                                    yvei();
                                    mYe2.setText("余额:"+balance);
                                }
                                if (i == 2) {
                                    mChepai32.setText(plate.get(2));
                                    ss = (String) mChepai32.getText();
                                    yvei();
                                    mYe3.setText("余额:"+balance);
                                }
                                if (i == 3) {
                                    mChepai42.setText(plate.get(3));
                                    ss = (String) mChepai42.getText();
                                    yvei();
                                    mYe4.setText("余额:"+balance);
                                }
                            }
                            chebiao();

                        }
                    });


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void initView() {
        mGrxxTouxiang2 = (ImageView) findViewById(R.id.grxx_touxiang2);
        mYhm = (TextView) findViewById(R.id.yhm);
        mGrxxWz12 = (TextView) findViewById(R.id.grxx_wz12);
        mGrxxWz22 = (TextView) findViewById(R.id.grxx_wz22);
        mGrxxWz32 = (TextView) findViewById(R.id.grxx_wz32);
        mGrxxWz42 = (TextView) findViewById(R.id.grxx_wz42);
        mGrxxWz52 = (TextView) findViewById(R.id.grxx_wz52);
        mTupian12 = (ImageView) findViewById(R.id.tupian12);
        mChepai2 = (TextView) findViewById(R.id.chepai2);
        mTupian22 = (ImageView) findViewById(R.id.tupian22);
        mChepai22 = (TextView) findViewById(R.id.chepai22);
        mTupian32 = (ImageView) findViewById(R.id.tupian32);
        mChepai32 = (TextView) findViewById(R.id.chepai32);
        mTupian42 = (ImageView) findViewById(R.id.tupian42);
        mChepai42 = (TextView) findViewById(R.id.chepai42);
        mYe1 = (TextView) findViewById(R.id.ye1);
        mYe3 = (TextView) findViewById(R.id.ye3);
        mYe2 = (TextView) findViewById(R.id.ye2);
        mYe4 = (TextView) findViewById(R.id.ye4);
    }

    private void chebiao() {
        if (mChepai2.getText().equals("鲁A10001")) {
            mTupian12.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai2.getText().equals("鲁A10002")) {
            mTupian12.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai2.getText().equals("鲁A10003")) {
            mTupian12.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai2.getText().equals("鲁A10004")) {
            mTupian12.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai2.getText().equals("鲁A10005")) {
            mTupian12.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai2.getText().equals("鲁A10006")) {
            mTupian12.setBackgroundResource(R.drawable.voervo);
        }


        if (mChepai22.getText().equals("鲁A10001")) {
            mTupian22.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai22.getText().equals("鲁A10002")) {
            mTupian22.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai22.getText().equals("鲁A10003")) {
            mTupian22.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai22.getText().equals("鲁A10004")) {
            mTupian22.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai22.getText().equals("鲁A10005")) {
            mTupian22.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai22.getText().equals("鲁A10006")) {
            mTupian22.setBackgroundResource(R.drawable.voervo);
        }


        if (mChepai32.getText().equals("鲁A10001")) {
            mTupian32.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai32.getText().equals("鲁A10002")) {
            mTupian32.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai32.getText().equals("鲁A10003")) {
            mTupian32.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai32.getText().equals("鲁A10004")) {
            mTupian32.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai32.getText().equals("鲁A10005")) {
            mTupian32.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai32.getText().equals("鲁A10006")) {
            mTupian32.setBackgroundResource(R.drawable.voervo);
        }


        if (mChepai42.getText().equals("鲁A10001")) {
            mTupian42.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai42.getText().equals("鲁A10002")) {
            mTupian42.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai42.getText().equals("鲁A10003")) {
            mTupian42.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai42.getText().equals("鲁A10004")) {
            mTupian42.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai42.getText().equals("鲁A10005")) {
            mTupian42.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai42.getText().equals("鲁A10006")) {
            mTupian42.setBackgroundResource(R.drawable.voervo);
        }
    }

    private void yvei() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
            jsonObject.put("plate", ss);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_balance_c", jsonObject.toString(), "POST", new Callback() {


            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    balance = jsonObject1.getString("balance");
                    Log.v("6666",balance);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}