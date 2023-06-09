package com.example.shiyidaoshisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shiyidaoshisan.shipei.SSXS_Fragment1;
import com.example.shiyidaoshisan.shipei.SSXS_Fragment2;
import com.example.shiyidaoshisan.shipei.SSXS_Fragment3;
import com.example.shiyidaoshisan.shipei.SSXS_Fragment4;
import com.example.shiyidaoshisan.shipei.SSXS_Fragment5;
import com.example.shiyidaoshisan.shipei.SSXS_Fragment6;

import java.util.ArrayList;
import java.util.List;

;


public class SSXS extends AppCompatActivity implements View.OnClickListener {

    private SSXS_Fragment1 temperature;
    private SSXS_Fragment2 humidity;
    private SSXS_Fragment3 illumination;
    private SSXS_Fragment4 co2;
    private SSXS_Fragment5 pm25;
    private SSXS_Fragment6 state;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    private ViewPager vp;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private ImageView mAnniu1;
    private ImageView mAnniu2;
    private ImageView mAnniu3;
    private ImageView mAnniu4;
    private ImageView mAnniu5;
    private ImageView mAnniu6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhe_xian_tu);
        initView();
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(6);//有几个界面就写几个
        vp.setAdapter(mFragmentAdapter);
        Intent intent=getIntent();
        int s=intent.getIntExtra("sss",1);
        int ss=0;
        if (s==0){
            ss=2;
        }
        if (s==1){
            ss=0;
        }
        if (s==3){
            ss=3;
        }
        if (s==4){
            ss=4;
        }
        vp.setCurrentItem(ss);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }


    //点击底部Text动态修改ViewPager内容
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anniu1:
                vp.setCurrentItem(0,true);
                break;
            case R.id.anniu2:
                vp.setCurrentItem(1,true);
                break;
            case R.id.anniu3:
                vp.setCurrentItem(2,true);
                break;
            case R.id.anniu4:
                vp.setCurrentItem(3,true);
                break;
            case R.id.anniu5:
                vp.setCurrentItem(4,true);
                break;
            case R.id.anniu6:
                vp.setCurrentItem(5,true);
                break;
        }
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.view_pager);
        mLinearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        temperature = new SSXS_Fragment1();
        humidity = new SSXS_Fragment2();
        illumination = new SSXS_Fragment3();
        co2 = new SSXS_Fragment4();
        pm25 = new SSXS_Fragment5();
        state = new SSXS_Fragment6();
        mFragmentList.add(temperature);
        mFragmentList.add(humidity);
        mFragmentList.add(illumination);
        mFragmentList.add(co2);
        mFragmentList.add(pm25);
        mFragmentList.add(state);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAnniu1 = (ImageView) findViewById(R.id.anniu1);
        mAnniu2 = (ImageView) findViewById(R.id.anniu2);
        mAnniu3 = (ImageView) findViewById(R.id.anniu3);
        mAnniu4 = (ImageView) findViewById(R.id.anniu4);
        mAnniu5 = (ImageView) findViewById(R.id.anniu5);
        mAnniu6 = (ImageView) findViewById(R.id.anniu6);
        mAnniu1.setOnClickListener(this);
        mAnniu2.setOnClickListener(this);
        mAnniu3.setOnClickListener(this);
        mAnniu4.setOnClickListener(this);
        mAnniu5.setOnClickListener(this);
        mAnniu6.setOnClickListener(this);
    }

    public class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    //修改底部导航颜色
    private void changeTextColor(int position) {
        if (position==0){
            mAnniu1.setBackgroundResource(R.drawable.page_indicator_focused);
            mAnniu2.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu3.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu4.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu5.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu6.setBackgroundResource(R.drawable.page_indicator_unfocused);
        }
        if (position==1){
            mAnniu1.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu2.setBackgroundResource(R.drawable.page_indicator_focused);
            mAnniu3.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu4.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu5.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu6.setBackgroundResource(R.drawable.page_indicator_unfocused);
        }
        if (position==2){
            mAnniu1.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu2.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu3.setBackgroundResource(R.drawable.page_indicator_focused);
            mAnniu4.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu5.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu6.setBackgroundResource(R.drawable.page_indicator_unfocused);
        }
        if (position==3){
            mAnniu1.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu2.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu3.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu4.setBackgroundResource(R.drawable.page_indicator_focused);
            mAnniu5.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu6.setBackgroundResource(R.drawable.page_indicator_unfocused);
        }
        if (position==4){
            mAnniu1.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu2.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu3.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu4.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu5.setBackgroundResource(R.drawable.page_indicator_focused);
            mAnniu6.setBackgroundResource(R.drawable.page_indicator_unfocused);
        }
        if (position==5){
            mAnniu1.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu2.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu3.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu4.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu5.setBackgroundResource(R.drawable.page_indicator_unfocused);
            mAnniu6.setBackgroundResource(R.drawable.page_indicator_focused);
        }
    }


}