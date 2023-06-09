package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HLDGL_shepeiqi2 extends DialogFragment {
    ArrayList<String> ccc;
    public HLDGL_shepeiqi2(ArrayList<String> aa){
        this.ccc=aa;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hldshezhi,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText wbk1=getView().findViewById(R.id.wbk1);
        EditText wbk2=getView().findViewById(R.id.wbk2);
        EditText wbk3=getView().findViewById(R.id.wbk3);
        Button   anniu1=getView().findViewById(R.id.bt_exit);
        Button   anniu2=getView().findViewById(R.id.bt_exit2);
        for (int i=0;i<ccc.size();i++){
            JSONObject jsonObject1=new JSONObject();
            try {
                jsonObject1.put("UserName","user1");
                jsonObject1.put("number",ccc.get(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            new Http().sendResUtil("get_traffic_light", jsonObject1.toString(), "POST", new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String s=response.body().string();
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        JSONArray jsonArray=jsonObject.getJSONArray("ROWS_DETAIL");
                        JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                        wbk1.setText(jsonObject2.getInt("red")+"");
                        wbk2.setText(jsonObject2.getInt("yellow")+"");
                        wbk3.setText(jsonObject2.getInt("green")+"");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        anniu1.setOnClickListener(view->{
            for (int i=0;i< ccc.size();i++){
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("UserName","user1");
                    jsonObject.put("number",ccc.get(i));
                    jsonObject.put("red",wbk1.getText()+"");
                    jsonObject.put("yellow",wbk2.getText()+"");
                    jsonObject.put("green",wbk3.getText()+"");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                new Http().sendResUtil("set_traffic_light", jsonObject.toString(), "POST", new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    }
                });
            }
            dismiss();

        });
        anniu2.setOnClickListener(view -> {
            dismiss();
        });

    }
}
