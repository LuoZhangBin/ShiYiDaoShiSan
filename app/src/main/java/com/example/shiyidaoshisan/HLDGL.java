package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shiyidaoshisan.fanfa.A;
import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.jieko1;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HLDGL extends AppCompatActivity {

    private ImageView mHLDCaidan;
    private Spinner mHLDXialia;
    private Button mHLDAnniu1;
    private Button mHLDAnniu2;
    private ListView mHLDList;
    private ArrayList<A> aa=new ArrayList<>();
    ArrayList<String> ccc=new ArrayList<>();
    private HLDGL_shpeiqi1 shjv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hldgl);
        initView();
        zhuti();
    }

    private void zhuti() {
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
                                        shjv=new HLDGL_shpeiqi1(HLDGL.this,aa);
                                        mHLDList.setAdapter(shjv);
                                        shjv.setOnClick(new jieko1() {
                                            @Override
                                            public void jieko(int s) {
                                                ccc.add(String.valueOf(s));
                                                FragmentManager manager=getSupportFragmentManager();
                                                FragmentTransaction transaction=manager.beginTransaction();
                                                HLDGL_shepeiqi2 hldgl_tangchuang=new HLDGL_shepeiqi2(ccc);
                                                transaction.add(hldgl_tangchuang,"hldgl-tog");
                                                transaction.show(hldgl_tangchuang);
                                                transaction.commit();
                                            }

                                            @Override
                                            public void jiekos(int ss) {
                                                ccc.add(String.valueOf(ss));
                                            }
                                        });
                                        mHLDAnniu2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                FragmentManager manager=getSupportFragmentManager();
                                                FragmentTransaction transaction=manager.beginTransaction();
                                                HLDGL_shepeiqi2 hldgl_tangchuang=new HLDGL_shepeiqi2(ccc);
                                                transaction.add(hldgl_tangchuang,"hldgl-tog");
                                                transaction.show(hldgl_tangchuang);
                                                transaction.commit();
                                            }
                                        });
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
        mHLDCaidan = (ImageView) findViewById(R.id.HLD_caidan);
        mHLDXialia = (Spinner) findViewById(R.id.HLD_xialia);
        mHLDAnniu1 = (Button) findViewById(R.id.HLD_anniu1);
        mHLDAnniu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSortList();
                shjv.notifyDataSetChanged();
            }
        });
        mHLDAnniu2 = (Button) findViewById(R.id.HLD_anniu2);
        mHLDList = (ListView) findViewById(R.id.HLD_list);
    }
    private void setSortList(){
        Collections.sort(aa, new Comparator<A>() {
            @Override
            public int compare(A a, A t1) {
                switch ((int) mHLDXialia.getSelectedItemId()){
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