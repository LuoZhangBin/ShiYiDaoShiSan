package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZHGL22_TanChuang extends DialogFragment {

    private ArrayList<String> sss;


    public ZHGL22_TanChuang(ArrayList<String> sss){
        this.sss=sss;
    }
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle){
        return layoutInflater.inflate(R.layout.chongzhijiemian,null);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        TextView cph=getView().findViewById(R.id.cph);
        EditText qian=getView().findViewById(R.id.shurkuang);
        Button anniu1=getView().findViewById(R.id.anniu1);
        Button anniu2=getView().findViewById(R.id.anniu2);
        cph.setText(sss.toString());
        anniu1.setOnClickListener(view -> {
            for (int i=0;i< sss.size();i++){
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("balance",qian.getText().toString());
                    jsonObject.put("plate",sss.get(i));
                    jsonObject.put("UserName","user 1");

                    new Http().sendResUtil("set_balance", jsonObject.toString(), "POST", new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String s=response.body().string();
                            try {
                                JSONObject jsonObject1=new JSONObject(s);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
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
