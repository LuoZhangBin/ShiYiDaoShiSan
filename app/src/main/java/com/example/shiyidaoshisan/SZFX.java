package com.example.shiyidaoshisan;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.shiyidaoshisan.shipei.SJFX_shipei1;
import com.example.shiyidaoshisan.shipei.SJFX_shipei2;

import java.util.ArrayList;

import java.util.List;


public class SZFX extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mHuadueng;
    private Button mSJFXAnniu1;
    private Button mSJFXAnniu2;
    private SJFX_shipei1 shipei1;
    private SJFX_shipei2 shipei2;
    private SHZS.FragmentAdapter fragmentAdapter;
    private List<Fragment> mFragmentList=new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szfx);
        initView();
        fragmentAdapter=new SHZS.FragmentAdapter(this.getSupportFragmentManager(),mFragmentList);
        mHuadueng.setOffscreenPageLimit(2);
        mHuadueng.setAdapter(fragmentAdapter);
        mHuadueng.setCurrentItem(0);
        mHuadueng.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                piehuan(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void initView() {
        mHuadueng = (ViewPager) findViewById(R.id.huadueng);
        mSJFXAnniu1 = (Button) findViewById(R.id.SJFX_anniu1);
        mSJFXAnniu2 = (Button) findViewById(R.id.SJFX_anniu2);
        mSJFXAnniu1.setOnClickListener(this);
        mSJFXAnniu2.setOnClickListener(this);

        shipei1 = new SJFX_shipei1();
        shipei2 = new SJFX_shipei2();
        mFragmentList.add(shipei1);
        mFragmentList.add(shipei2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.SJFX_anniu1:
                mHuadueng.setCurrentItem(0,true);
                break;
            case R.id.SJFX_anniu2:
                mHuadueng.setCurrentItem(1,true);
                break;
        }
    }
    private void piehuan(int position) {
        if (position==0){
            mSJFXAnniu1.setBackgroundResource(R.drawable.yuan);
            mSJFXAnniu2.setBackgroundResource(R.drawable.yuan2);

        }
        if (position==1){
            mSJFXAnniu1.setBackgroundResource(R.drawable.yuan2);
            mSJFXAnniu2.setBackgroundResource(R.drawable.yuan);
        }

    }
}