package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZHSZ23 extends AppCompatActivity {

    private TextView mWz123;
    private EditText mWdk23;
    private Button mAnniu23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhsz23);
        initView();
        extracted();


    }

    private boolean extracted1() {
        int a= Integer.parseInt(mWdk23.getText().toString());
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
            jsonObject.put("threshold",a);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("set_car_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
        return true;
    }

    private void extracted() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
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
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);

                    String yvzhi=jsonObject2.getString("threshold");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWz123.setText("我的1-4号车账户余额警告为"+yvzhi+"元");
                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        mWz123 = (TextView) findViewById(R.id.wz1_23);
        mWdk23 = (EditText) findViewById(R.id.wdk23);
        mAnniu23 = (Button) findViewById(R.id.anniu23);
        mAnniu23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWdk23.getText().toString().equals("")){
                    Toast.makeText(ZHSZ23.this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (extracted1()){
                    extracted();
                    Toast.makeText(ZHSZ23.this, "成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ZHSZ23.this, "失败", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }
}