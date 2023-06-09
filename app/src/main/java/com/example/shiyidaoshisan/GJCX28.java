package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.BUS;
import com.example.shiyidaoshisan.fanfa.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GJCX28 extends AppCompatActivity {
    private Map<String, List<BUS>> map;
    private GJCXAdapter adapter;
    private ExpandableListView mLiebiao28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gjcx28);
        initView();
        mLiebiao28.setGroupIndicator(null);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setVolley();
            }
        },0,3000);

    }

    private void initView() {
        mLiebiao28 = (ExpandableListView) findViewById(R.id.liebiao_28);
    }
    private void setVolley() {
       JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_bus_stop_distance", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    if (map == null) {
                        map = new HashMap<>();
                    } else {
                        map.clear();
                    }
                    List<BUS> buses1=  new Gson().fromJson(jsonObject1.optJSONArray("中医院站").toString(),
                            new TypeToken<List<BUS>>(){}.getType());
                    buses1.sort(new Comparator<BUS>() {
                        @Override
                        public int compare(BUS o1, BUS o2) {
                            return o1.getDistance() - o2.getDistance();
                        }
                    });
                    map.put("一号站台",buses1);
                    List<BUS> buses2=  new Gson().fromJson(jsonObject1.optJSONArray("联想大厦站").toString(),
                            new TypeToken<List<BUS>>(){}.getType());
                    buses2.sort(new Comparator<BUS>() {
                        @Override
                        public int compare(BUS o1, BUS o2) {
                            return o1.getDistance() - o2.getDistance();
                        }
                    });
                    map.put("二号站台",buses2);
                    if (adapter ==null){
                        adapter = new GJCXAdapter(map);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLiebiao28.setAdapter(adapter);
                            }
                        });

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}