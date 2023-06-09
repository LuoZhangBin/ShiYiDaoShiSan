package com.example.shiyidaoshisan.shipei;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GRZX_shipaiqi2 extends Fragment {

    private ImageView mCzjlTouxiang;
    private TextView mName;
    private ListView mList11;
    private String user;
    private TextView mTishi;
    boolean sss=true;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (sex.equals("男")) {
                mCzjlTouxiang.setBackgroundResource(R.drawable.touxiang_2);
            } else {
                mCzjlTouxiang.setBackgroundResource(R.drawable.touxiang_1);
            }
            mName.setText(name);
            mTishi.setText("没有充值记录");
            handler.removeCallbacks(null);

            return false;
        }
    });
    private String name;
    private String sex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.czjl, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        SharedPreferences sp = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
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
                    name = jsonObject1.getString("name");
                    sex = jsonObject1.getString("sex");
                    do {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(0);
                            }
                        }).start();
                    }while (sss);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void initView() {
        mCzjlTouxiang = (ImageView) getView().findViewById(R.id.czjl_touxiang);
        mName = (TextView) getView().findViewById(R.id.name);
        mList11 = (ListView) getView().findViewById(R.id.list11);
        mTishi = (TextView) getView().findViewById(R.id.tishi);
    }

    @Override
    public void onDestroy() {
        sss=false;
        super.onDestroy();
    }
}
