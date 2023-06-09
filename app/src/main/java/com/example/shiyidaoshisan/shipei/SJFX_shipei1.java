package com.example.shiyidaoshisan.shipei;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.SJFX;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
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


public class SJFX_shipei1 extends Fragment {

    private PieChart mTuoxing;
    private List<SJFX> sjfxes,yes,no;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sjfx_yemian1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("UserName","user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s=response.body().string();
                try {
                    JSONObject jsonObject1=new JSONObject(s);
                    sjfxes = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(),new TypeToken<List<SJFX>>(){
                    }.getType());
                    yes=new ArrayList<>();
                    no  =new ArrayList<>();
                    for (int i=0;i<sjfxes.size();i++){
                        SJFX sjfx=sjfxes.get(i);
                        if(sjfx.getPaddr().length()==0){
                            no.add(sjfx);
                        }else {
                            yes.add(sjfx);
                        }

                    }
                    weizhangcishu();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void weizhangcishu() {
        for (int i=0;i<yes.size();i++){
            for (int j=i+1;j<yes.size();j++){
                if (yes.get(i).getCarnumber().equals(yes.get(j).getCarnumber())){
                    yes.remove(j);

                    j--;
                }
            }
        }

        mTuoxing.setExtraOffsets(20,30,20,10);
        List<PieEntry> pieEntries=new ArrayList<PieEntry>();
        int sss=no.size()+yes.size();
        float no1=yes.size();
        float no2=no1/sss;
        float no3=no.size();
        float no4=no3/sss;

        pieEntries.add(new PieEntry(no2*100,"无违章"));
        pieEntries.add(new PieEntry(no4*100,"有违章"));

        PieDataSet pieDataSet=new PieDataSet(pieEntries,"666");
        pieDataSet.setColors( Color.RED,Color.BLUE );

        pieDataSet.setSelectionShift(15f);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueLineColor(Color.BLUE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLineVariableLength(true);
        pieDataSet.setValueLinePart1OffsetPercentage(80f);
        pieDataSet.setValueLinePart1Length(0.8f);
        pieDataSet.setValueLineWidth(2f);
        final String strs[] = new String[]{"有违章","无违章"};

        //自定义格式
        pieDataSet.setValueFormatter(new ValueFormatter() {
            private int indd = -1;
            @Override
            public String getPieLabel(float value, PieEntry pieEntry) {
                indd++;
                if (indd>=strs.length){
                    indd=0;
                }
                return strs[indd] + value + "%";
            }
        });
        PieData pieData=new PieData(pieDataSet);

        Legend legend = mTuoxing.getLegend();
        legend.setEnabled(false);
        mTuoxing.setDrawHoleEnabled(false);

        mTuoxing.setData(pieData);
    }

    private void initView() {
        mTuoxing = (PieChart) getView().findViewById(R.id.tuoxing);
    }
}
