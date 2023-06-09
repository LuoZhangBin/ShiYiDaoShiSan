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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.GRXX;
import com.example.shiyidaoshisan.fanfa.Http;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GRZX_shipaiqi1 extends Fragment {

    private ImageView mGrxxTouxiang;
    private TextView mGrxxWz1;
    private TextView mGrxxWz2;
    private TextView mGrxxWz3;
    private TextView mGrxxWz4;
    private TextView mGrxxWz5;
    private ImageView mTupian1;
    private TextView mChepai;
    private ImageView mTupian2;
    private TextView mChepai2;
    private ImageView mTupian3;
    private TextView mChepai3;
    private ImageView mTupian4;
    private TextView mChepai4;
    private String user;
    private List<String> plate;
    private String root;
    private String tel;
    private String registered_time;
    private String idnumber;
    private String sex;
    private String name;

    boolean sss=true;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            mGrxxWz1.setText("姓名:"+name);
            mGrxxWz2.setText("性别:"+sex);
            if (sex.equals("男")){
                mGrxxTouxiang.setBackgroundResource(R.drawable.touxiang_2);
            }else {
                mGrxxTouxiang.setBackgroundResource(R.drawable.touxiang_1);
            }
            mGrxxWz3.setText("手机号码:"+tel);
            mGrxxWz4.setText("身份证号:"+idnumber);
            mGrxxWz5.setText("注册时间:"+registered_time);
            for (int i=0;i<plate.size();i++){
                if (i==0){
                    mChepai.setText(plate.get(0));
                }
                if (i==1){
                    mChepai2.setText(plate.get(1));
                }
                if (i==2){
                    mChepai3.setText(plate.get(2));
                }
                if (i==3){
                    mChepai4.setText(plate.get(3));
                }
            }
            chebiao();
            handler.removeCallbacks(null);
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grxx, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        SharedPreferences sp= getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
        user = sp.getString("usname",null);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName",user);
            jsonObject.put("Password","1234");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_root", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    name = jsonObject1.getString("name");
                    sex = jsonObject1.getString("sex");
                    idnumber = jsonObject1.getString("idnumber");
                    registered_time = jsonObject1.getString("registered_time");
                    tel = jsonObject1.getString("tel");
                    root = jsonObject1.getString("root");
                    plate = new Gson().fromJson(jsonObject1.optJSONArray("plate").toString(),new TypeToken<List<String>>(){}.getType());
                    do {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(0);
                            }
                        }).start();
                    }while (false);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void chebiao() {
        if (mChepai.getText().equals("鲁A10001")){
            mTupian1.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai.getText().equals("鲁A10002")){
            mTupian1.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai.getText().equals("鲁A10003")){
            mTupian1.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai.getText().equals("鲁A10004")){
            mTupian1.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai.getText().equals("鲁A10005")){
            mTupian1.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai.getText().equals("鲁A10006")){
            mTupian1.setBackgroundResource(R.drawable.voervo);
        }



        if (mChepai2.getText().equals("鲁A10001")){
            mTupian2.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai2.getText().equals("鲁A10002")){
            mTupian2.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai2.getText().equals("鲁A10003")){
            mTupian2.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai2.getText().equals("鲁A10004")){
            mTupian2.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai2.getText().equals("鲁A10005")){
            mTupian2.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai2.getText().equals("鲁A10006")){
            mTupian2.setBackgroundResource(R.drawable.voervo);
        }



        if (mChepai3.getText().equals("鲁A10001")){
            mTupian3.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai3.getText().equals("鲁A10002")){
            mTupian3.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai3.getText().equals("鲁A10003")){
            mTupian3.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai3.getText().equals("鲁A10004")){
            mTupian3.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai3.getText().equals("鲁A10005")){
            mTupian3.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai3.getText().equals("鲁A10006")){
            mTupian3.setBackgroundResource(R.drawable.voervo);
        }



        if (mChepai4.getText().equals("鲁A10001")){
            mTupian4.setBackgroundResource(R.drawable.tesila);
        }
        if (mChepai4.getText().equals("鲁A10002")){
            mTupian4.setBackgroundResource(R.drawable.baoma);
        }
        if (mChepai4.getText().equals("鲁A10003")){
            mTupian4.setBackgroundResource(R.drawable.benchi);
        }
        if (mChepai4.getText().equals("鲁A10004")){
            mTupian4.setBackgroundResource(R.drawable.sibalu);
        }
        if (mChepai4.getText().equals("鲁A10005")){
            mTupian4.setBackgroundResource(R.drawable.fute);
        }
        if (mChepai4.getText().equals("鲁A10006")){
            mTupian4.setBackgroundResource(R.drawable.voervo);
        }
    }

    private void initView() {
        mGrxxTouxiang = (ImageView) getView().findViewById(R.id.grxx_touxiang);
        mGrxxWz1 = (TextView) getView().findViewById(R.id.grxx_wz1);
        mGrxxWz2 = (TextView) getView().findViewById(R.id.grxx_wz2);
        mGrxxWz3 = (TextView) getView().findViewById(R.id.grxx_wz3);
        mGrxxWz4 = (TextView) getView().findViewById(R.id.grxx_wz4);
        mGrxxWz5 = (TextView) getView().findViewById(R.id.grxx_wz5);
        mTupian1 = (ImageView) getView().findViewById(R.id.tupian1);
        mChepai = (TextView) getView().findViewById(R.id.chepai);
        mTupian2 = (ImageView) getView().findViewById(R.id.tupian2);
        mChepai2 = (TextView) getView().findViewById(R.id.chepai2);
        mTupian3 = (ImageView) getView().findViewById(R.id.tupian3);
        mChepai3 = (TextView) getView().findViewById(R.id.chepai3);
        mTupian4 = (ImageView) getView().findViewById(R.id.tupian4);
        mChepai4 = (TextView) getView().findViewById(R.id.chepai4);
    }

    @Override
    public void onDestroy() {
        mChepai.setText("");
        mChepai2.setText("");
        mChepai3.setText("");
        mChepai4.setText("");
        sss=false;
        super.onDestroy();
    }
}
