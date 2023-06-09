package com.example.shiyidaoshisan;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.A;
import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.shipei.HLDGL21_shpeiqi1;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HLDGL21 extends AppCompatActivity {

    private ImageView mHld;
    private Spinner mXialia21;
    private ListView mList21;
    private ArrayList<A> aa=new ArrayList<>();
    private HLDGL21_shpeiqi1 shpeiqi1;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hldgl21);
        initView();
        extracted();

    }

    private void extracted() {
        for (int i=1;i<=5;i++){
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("UserName","user1");
                jsonObject.put("number",i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int finalI = i;
            new Http().sendResUtil("get_traffic_light", jsonObject.toString(), "POST", new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                    try {
                        JSONObject jsonObject1=new JSONObject(s);
                        aa.add(new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(),A.class));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalI ==5){
                                    setSortList();
                                    shpeiqi1 = new HLDGL21_shpeiqi1(HLDGL21.this,aa);
                                    mList21.setAdapter(shpeiqi1);
                                }
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    private void initView() {
        AnimationDrawable ad;
        ad= (AnimationDrawable) getResources().getDrawable(R.drawable.duenghua4);
        mHld = (ImageView) findViewById(R.id.hld);
        mHld.setBackgroundDrawable(ad);
        ad.start();
        mXialia21 = (Spinner) findViewById(R.id.xialia21);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, getResources().getStringArray(R.array.xialia));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mXialia21.setSelection(0,true);
        mXialia21.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mXialia21.setSelection(i,true);
                extracted();
                shpeiqi1.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mList21 = (ListView) findViewById(R.id.list21);
    }
    private void setSortList(){
        Collections.sort(aa, new Comparator<A>() {
            @Override
            public int compare(A a, A t1) {
                switch ((int) mXialia21.getSelectedItemId()){
                    case 0:
                        return a.getNumber() - t1.getNumber();
                    case 1:
                        return t1.getNumber() - a.getNumber();
                    case 2:
                        return a.getRed() - t1.getRed();
                    case 3:
                        return t1.getRed() - a.getRed();
                    case 4:
                        return a.getGreen() - t1.getGreen();
                    case 5:
                        return t1.getGreen() - a.getGreen();
                    case 6:
                        return a.getYellow() - t1.getYellow();
                    case 7:
                        return t1.getYellow() - a.getYellow();
                }
                return 0;
            }
        });
    }
}