package com.example.shiyidaoshisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.CLWZ_Left;
import com.example.shiyidaoshisan.fanfa.CLWZ_Right;
import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.WZXX;
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

public class CLWZ extends AppCompatActivity {

    private ImageView mCLWZCaidan;
    private EditText mCLWZWbk;
    private Button mCLWZAnniu;
    private List<WZXX> allInfos, wzxxes2;
    public static List<CLWZ_Left> clwzLefts;
    public static List<CLWZ_Right> clwzRights;

    public static List<CLWZ_Left> getClwzLefts() {
        return clwzLefts;
    }

    public static List<CLWZ_Right> getClwzRights() {
        return clwzRights;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clwz);
        initView();
        clwzLefts = new ArrayList<>();
        clwzRights = new ArrayList<>();
        neirong1();
    }

    private void neirong1() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_all_car_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    allInfos=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<WZXX>>(){}.getType());
                    neirong2();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void neirong2() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_pessancy_infos", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    wzxxes2=new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<WZXX>>(){}.getType());
                    setListAll();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private void setListAll() {
        for (int i = 0; i < allInfos.size(); i++) {
            WZXX wzxx = allInfos.get(i);
            for (int j = 0; j < wzxxes2.size(); j++) {
                WZXX wzxx1 = wzxxes2.get(j);
                if (wzxx.getInfoid().equals(wzxx1.getInfoid())) {
                    wzxx.setRoad(wzxx1.getRoad());
                    wzxx.setMessage(wzxx1.getMessage());
                    wzxx.setDeduct(wzxx1.getDeduct());
                    wzxx.setFine(wzxx1.getFine());
                }
            }
        }
    }

    PaletInfp paletInfp;

    private void initView() {
        mCLWZCaidan = (ImageView) findViewById(R.id.CLWZ_caidan);
        mCLWZWbk = (EditText) findViewById(R.id.CLWZ_wbk);
        mCLWZAnniu = (Button) findViewById(R.id.CLWZ_anniu);
        mCLWZAnniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCLWZWbk.getText().equals("")) {
                    Toast.makeText(CLWZ.this, "车牌号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String Cp = "鲁" + mCLWZWbk.getText().toString().toUpperCase();
                if (Cp.length() != 7) {
                    Toast.makeText(CLWZ.this, "请输入正确的车牌号", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject jsonObject =new JSONObject();
                try {
                    jsonObject.put("UserName","user1");
                    jsonObject.put("plate",Cp);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                new Http().sendResUtil("get_peccancy_plate", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String s=response.body().string();
                        try {
                            JSONObject jsonObject1=new JSONObject(s);
                            paletInfp = new Gson().fromJson(jsonObject1.toString(), PaletInfp.class);
                            if (paletInfp.id.size() == 0) {
                                Toast.makeText(CLWZ.this, "没有查询到" + Cp + "车的违章数据！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            List<Integer> list = paletInfp.getId();
                            int Kf = 0, Fk = 0;
                            for (int i = 0; i < clwzLefts.size(); i++) {
                                CLWZ_Left clwzLeft = clwzLefts.get(i);
                                if (clwzLeft.getCp().equals(Cp)){
                                    clwzLefts.remove(i);
                                    clwzLefts.add(0,clwzLeft);
                                    startActivity( new Intent(CLWZ.this,CLWZ_CXJG.class));
                                    mCLWZWbk.setText("");
                                    return;
                                }
                            }
                            for (int i = 0; i < list.size(); i++) {
                                for (int j = 0; j < allInfos.size(); j++) {
                                    WZXX wzxx = allInfos.get(j);
                                    if (wzxx.getId() == list.get(i)) {
                                        Kf += wzxx.getDeduct();
                                        Fk += wzxx.getFine();
                                        clwzRights.add(new CLWZ_Right(Cp, wzxx.getTime(), wzxx.getRoad(), wzxx.getMessage(), wzxx.getDeduct(), wzxx.getFine()));
                                    }
                                }
                            }
                            clwzLefts.add(0,new CLWZ_Left(Cp,list.size()+"",Kf,Fk));
                            startActivity( new Intent(CLWZ.this,CLWZ_CXJG.class));
                            mCLWZWbk.setText("");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });


        }
        class PaletInfp {



        private String RESULT;
        private List<Integer> id;

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

        public List<Integer> getId() {
            return id;
        }

        public void setId(List<Integer> id) {
            this.id = id;
        }
    }
}