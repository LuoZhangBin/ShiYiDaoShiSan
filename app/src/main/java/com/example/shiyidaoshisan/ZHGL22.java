package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shiyidaoshisan.fanfa.B;
import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.jieko2;
import com.example.shiyidaoshisan.shipei.ZHGL_shipei22;
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

public class ZHGL22 extends AppCompatActivity {

    private TextView mPlcz;
    private TextView mCfjl;
    private ListView mList22;
    private ArrayList<B> bb;
    private ZHGL_shipei22 shipei22;
    private ArrayList<String> sss=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhgl22);
        initView();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_vehicle", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    bb = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<B>>(){}.getType());
                    shipei22 = new ZHGL_shipei22(ZHGL22.this,bb);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mList22.setAdapter(shipei22);
                            shipei22.setOnClick(new jieko2() {
                                @Override
                                public void jieko(String s) {
                                    sss.add(s);
                                    FragmentManager manager=getSupportFragmentManager();
                                    FragmentTransaction transaction=manager.beginTransaction();
                                    ZHGL22_TanChuang zhgl_tanChuang=new ZHGL22_TanChuang(sss);
                                    transaction.add(zhgl_tanChuang,"zhgl_tanChuang-tog");
                                    transaction.show(zhgl_tanChuang);
                                    transaction.commit();
                                }

                                @Override
                                public void jiekos(String ss) {
                                    sss.add(ss);
                                }
                            });
                            mPlcz.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FragmentManager manager=getSupportFragmentManager();
                                    FragmentTransaction transaction=manager.beginTransaction();
                                    ZHGL22_TanChuang zhgl_tanChuang=new ZHGL22_TanChuang(sss);
                                    transaction.add(zhgl_tanChuang,"zhgl_tanChuang-tog");
                                    transaction.show(zhgl_tanChuang);
                                    transaction.commit();
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
        mPlcz = (TextView) findViewById(R.id.plcz);
        mCfjl = (TextView) findViewById(R.id.cfjl);
        mList22 = (ListView) findViewById(R.id.list22);
    }
}