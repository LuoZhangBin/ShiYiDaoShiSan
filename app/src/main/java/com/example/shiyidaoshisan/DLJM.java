package com.example.shiyidaoshisan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.DL;
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

public class DLJM extends AppCompatActivity {

    private EditText mName;
    private EditText mMima;
    private Button mDl;
    private Button mZhuche;
    private List<DL> yhxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dljm);
        initView();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_login", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    yhxx = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<DL>>(){}.getType());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mName = (EditText) findViewById(R.id.namess);
        mMima = (EditText) findViewById(R.id.mimaaa);
        mDl = (Button) findViewById(R.id.dl);
        mDl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                for (int i=0;i<yhxx.size();i++){
                    if (mMima.getText().toString().equals(yhxx.get(i).getPassword())&&
                            mName.getText().toString().equals(yhxx.get(i).getUsername())){
                        Log.v("66666",yhxx.get(i).getUsername());

                        SharedPreferences sp=getSharedPreferences("name", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed= sp.edit();
                        ed.putString("usname",yhxx.get(i).getUsername());
                        ed.commit();
                        intent.setClass(DLJM.this,GRZX.class);
                        startActivity(intent);
                    }
                }

            }
        });
        mZhuche = (Button) findViewById(R.id.zhuche);
    }
}