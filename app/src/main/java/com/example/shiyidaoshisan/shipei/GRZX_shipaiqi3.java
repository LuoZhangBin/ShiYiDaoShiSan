package com.example.shiyidaoshisan.shipei;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GRZX_shipaiqi3 extends Fragment {
    private TextView mYzszWz1;
    private EditText mShezhi;
    private Button mYzszAnniu;
    private String user;
    boolean sss=true;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            yvzhi();
            mYzszWz1.setText("当前1-4号小车账户余额警告阈值为"+yvzhi+"元");
            handler.removeCallbacks(null);
            return false;
        }
    });
    private String yvzhi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yzsz, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        SharedPreferences sp = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
        user = sp.getString("usname", null);
        yvzhi();
        mYzszAnniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chongzhi();
            }
        });

    }

    private void chongzhi() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName",user);
            jsonObject.put("threshold",mShezhi.getText());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("set_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.v("666666666","554564654");
            }
        });
    }

    private void yvzhi() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName",user);
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
                    yvzhi = jsonObject2.getString("threshold");
                    for (int i=0;i<1;i++){
                        do {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    handler.sendEmptyMessage(0);
                                }
                            }).start();
                        }while (false);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mYzszWz1 = (TextView) getView().findViewById(R.id.yzsz_wz1);
        mShezhi = (EditText) getView().findViewById(R.id.shezhi);
        mYzszAnniu = (Button) getView().findViewById(R.id.yzsz_anniu);
    }

    @Override
    public void onDestroy() {
        sss=false;
        super.onDestroy();
    }
}
