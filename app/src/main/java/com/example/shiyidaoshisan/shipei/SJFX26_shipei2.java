package com.example.shiyidaoshisan.shipei;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.Http;
import com.example.shiyidaoshisan.fanfa.SJFX;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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


public class SJFX26_shipei2 extends Fragment {


    private List<SJFX> sjfxes, yes, no;
    private HorizontalBarChart mHb;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sjfx_yemian2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_peccancy", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    sjfxes = new Gson().fromJson(jsonObject1.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<SJFX>>() {
                    }.getType());
                    yes = new ArrayList<>();
                    no = new ArrayList<>();
                    for (int i = 0; i < sjfxes.size(); i++) {
                        SJFX sjfx = sjfxes.get(i);
                        if (sjfx.getPaddr().length() == 0) {
                            no.add(sjfx);
                        } else {
                            yes.add(sjfx);
                        }

                    }
                    Log.v("111111", yes.size() + "");
                    Log.v("111111", no.size() + "");

                    weizhangcishu();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void weizhangcishu() {
        for (int i = 0; i < yes.size(); i++) {
            for (int j = i + 1; j < yes.size(); j++) {
                if (yes.get(i).getCarnumber().equals(yes.get(j).getCarnumber())) {
                    yes.remove(j);
                    j--;
                }
            }
        }


    }


        //设置标题
        private void setDes() {
            Description description = new Description();
            description.setText("");


            //获取屏幕的中间坐标
            WindowManager wm = (WindowManager) getActivity() .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            float x = dm.widthPixels / 2;

            description.setPosition(x,40);
            mHb.setDescription(description);
        }

        //设置y轴
        private void setYAxis() {
            //两边的y轴都要设置y轴的最小值才能在柱状图上面显示数值
            //不然是看不到效果的
            YAxis tepAxis = mHb.getAxisLeft();
            tepAxis.setAxisMaximum(80f);
            tepAxis.setAxisMinimum(0f);
            tepAxis.setEnabled(false);

            YAxis yAxis = mHb.getAxisRight();
            yAxis.setTextSize(12f);
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(80f);
            //自定义样式
            yAxis.setValueFormatter(new ValueFormatter() {

                @Override
                public String getFormattedValue(float value) {
                    return value+"%";
                }



            });
        }

        //设置x轴
        private void setXAxis() {
            XAxis xAxis = mHb.getXAxis();

            xAxis.setTextSize(16);
            xAxis.setAxisMaximum(10);
            //把最小值设置为负数能够为下方留出点空白距离
            xAxis.setAxisMinimum(-0.6f);
            xAxis.setDrawGridLines(false);
            //将x轴显示在左侧
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelCount(3);
            xAxis.setGranularity(1);
            //自定义样式
            final String ss[] = {"5条以上条违章","3—5条违章","1—2条违章"};
            xAxis.setValueFormatter(new IndexAxisValueFormatter(ss));
            //设置x轴的偏移量
            xAxis.setXOffset(15f);
        }

        //加载数据
        private void loadData() {
            //不绘制图例
            mHb.getLegend().setEnabled(false);
            //自动对齐
            mHb.setFitBars(true);
            mHb.setExtraOffsets(20,50,30,30);
            //将文本绘制在柱块上还是柱块里面
            mHb.setDrawValueAboveBar(true);

            //从下往上绘制
            final List<BarEntry> entries = new ArrayList<BarEntry>();
            float da[] = {};


            for(int i = 0; i < da.length; i++){
                entries.add(new BarEntry(i,da[i]));
            }

            BarDataSet barDataSet = new BarDataSet(entries,"");
            barDataSet.setValueTextSize(16f);//柱块上的字体大小
            barDataSet.setValueTextColor(Color.RED);//柱块上的字体颜色
            barDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);//加粗
            //自自定义样式
            barDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format("%.2f",value) + "%";
                }




            });
            //依次设置每次柱块的颜色
            barDataSet.setColors(Color.parseColor("#A8000C"),Color.parseColor("#3E6A9F"),Color.parseColor("#62802B"),
                    Color.parseColor("#D3550F"),Color.parseColor("#4B3566"),Color.parseColor("#E8AA19"),
                    Color.parseColor("#A6B96F"),Color.parseColor("#F09855"),Color.parseColor("#7C95BA"),
                    Color.parseColor("#A8000C"));
            BarData barData = new BarData(barDataSet);
            //设置柱块的宽度
            barData.setBarWidth(0.4f);

            mHb.setData(barData);

        }



    private void initView() {
        mHb = (HorizontalBarChart) getView().findViewById(R.id.hb);
    }
}
