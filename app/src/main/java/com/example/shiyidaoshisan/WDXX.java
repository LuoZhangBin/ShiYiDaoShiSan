package com.example.shiyidaoshisan;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.DB.DB;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class WDXX extends AppCompatActivity implements View.OnClickListener {


    private Button mWdxxAnniu1;
    private Button mWdxxAnniu2;
    private FrameLayout mWdxxVp;
    private LinearLayout mBaojing;
    private Spinner mXiaoxi;
    private ListView mWdxxList;

    List<shuju> cc = new ArrayList<shuju>();
    private Cursor c;
    ArrayAdapter<String> arrayAdapter;
    private LinearLayout mBaojing2;
    private PieChart mTuo;
    private int wd=0;
    private int gz=0;
    private int co2=0;
    private int pm25=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdxx);
        initView();
        mBaojing2.setVisibility(View.INVISIBLE);
        extracted();
        extracted1();
    }

    private void extracted1() {
        mTuo.getDescription().setEnabled(false);
        mTuo.setDrawHoleEnabled(false);
        mTuo.setExtraOffsets(20,20,20,20);
        List<PieEntry> pieEntries=new ArrayList<PieEntry>();
        pieEntries.add(new PieEntry(pm25,"PM2.5:"+pm25));
        pieEntries.add(new PieEntry(gz,"光照:"+gz));
        pieEntries.add(new PieEntry(wd,"温度:"+wd));
        pieEntries.add(new PieEntry(co2,"CO.2:"+co2));
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setColors(Color.GRAY,Color.BLUE,Color.YELLOW,Color.DKGRAY);//分别为每个分组设置颜色

        PieData pieData = new PieData(pieDataSet);

        Legend legend = mTuo.getLegend();
        legend.setTextSize(16f);
        legend.setTextColor(Color.BLUE);
        //设置图例是在文字的左边还是右边
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        //设置图例为圆形，默认为方形
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(20);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setFormToTextSpace(15);
        legend.setXOffset(40);


        //填充数据
        mTuo.setData(pieData);
    }

    private void extracted() {
        DB db = new DB(this);
        boolean s = db.isExist("huanjing");
        if (s == false) {
            String sq1 = "create table huanjing (\n" +
                    "id integer primary key autoincrement,\n" +
                    "                   jingbao varchar,\n" +
                    "                   yvzhi varchar,\n" +
                    "                   dangqianzhi varchar )";
            db.createTable(sq1);
        }
        int s1 = (int) mXiaoxi.getSelectedItemId();

        if (s1 == 0) {
            c = db.cursorDB("huanjing", null, null, null, null, null, null, null);
        }
        if (s1 == 1) {
            c = db.cursorDB("huanjing", null, "jingbao=?", new String[]{"[温度]报警"}, null, null, null, null);
        }
        if (s1 == 2) {
            c = db.cursorDB("huanjing", null, "jingbao=?", new String[]{"[光照]报警"}, null, null, null, null);
        }
        if (s1 == 3) {
            c = db.cursorDB("huanjing", null, "jingbao=?", new String[]{"[CO.2]报警"}, null, null, null, null);
        }
        if (s1 == 4) {
            c = db.cursorDB("huanjing", null, "jingbao=?", new String[]{"[PM2.5]报警"}, null, null, null, null);
        }

        cc = shujvgx(c);
        db.closeDb();
        WDXX_shipei1 ss = new WDXX_shipei1(cc, this);
        if (s1==0){
            for (int i=0;i<cc.size();i++){
                if (cc.get(i).s2.equals("[温度]报警")){
                    wd++;
                }
                if (cc.get(i).s2.equals("[光照]报警")){
                    gz++;
                }
                if (cc.get(i).s2.equals("[CO.2]报警")){
                    co2++;
                }
                if (cc.get(i).s2.equals("[PM2.5]报警")){
                    pm25++;
                }

            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWdxxList.setAdapter(ss);
            }
        });

    }

    private void initView() {

        mWdxxAnniu1 = (Button) findViewById(R.id.wdxx_anniu1);
        mWdxxAnniu1.setOnClickListener(this);
        mWdxxAnniu2 = (Button) findViewById(R.id.wdxx_anniu2);
        mWdxxAnniu2.setOnClickListener(this);
        mWdxxVp = (FrameLayout) findViewById(R.id.wdxx_vp);
        mBaojing = (LinearLayout) findViewById(R.id.baojing);
        mXiaoxi = (Spinner) findViewById(R.id.xiaoxi);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, getResources().getStringArray(R.array.xiaoxi));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mXiaoxi.setAdapter(arrayAdapter);
        mXiaoxi.setSelection(0, true);
        mXiaoxi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mXiaoxi.setSelection(position, true);
                extracted();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mWdxxList = (ListView) findViewById(R.id.wdxx_list);
        mBaojing2 = (LinearLayout) findViewById(R.id.baojing2);
        mTuo = (PieChart) findViewById(R.id.tuo);
    }

    @SuppressLint("Range")
    private List<shuju> shujvgx(Cursor c) {
        List<shuju> list = new ArrayList<shuju>();
        int i = 1;
        while (c.moveToNext()) {
            shuju ss = new shuju();
            ss.s1 = String.valueOf(i++);
            ss.s2 = c.getString(c.getColumnIndex("jingbao"));
            Log.v("6666", ss.s2);
            ss.s3 = c.getString(c.getColumnIndex("yvzhi"));
            ss.s4 = c.getString(c.getColumnIndex("dangqianzhi"));
            list.add(ss);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wdxx_anniu1:
                mBaojing2.setVisibility(View.INVISIBLE);
                mBaojing.setVisibility(View.VISIBLE);
                mWdxxAnniu1.setBackgroundResource(R.drawable.beij2);
                mWdxxAnniu2.setBackgroundResource(R.drawable.beij1);
                break;
            case R.id.wdxx_anniu2:
                mBaojing2.setVisibility(View.VISIBLE);
                mBaojing.setVisibility(View.INVISIBLE);
                mWdxxAnniu1.setBackgroundResource(R.drawable.beij1);
                mWdxxAnniu2.setBackgroundResource(R.drawable.beij2);
                break;

        }
    }
}