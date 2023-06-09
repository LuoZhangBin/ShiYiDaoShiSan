package com.example.shiyidaoshisan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shiyidaoshisan.shipei.SJFX26_shipei1;

import java.util.ArrayList;
import java.util.List;

public class SJFX26 extends AppCompatActivity {

    private ViewPager mXianshi26;
    private SJFX26_shipei1 shipei1;
    private List<Fragment> fragments= new ArrayList<Fragment>();
    private SHZS.FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfx26);
        initView();
        fragmentAdapter=new SHZS.FragmentAdapter(this.getSupportFragmentManager(),fragments);
        mXianshi26.setOffscreenPageLimit(2);
        mXianshi26.setAdapter(fragmentAdapter);
        mXianshi26.setCurrentItem(0);
        mXianshi26.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mXianshi26 = (ViewPager) findViewById(R.id.xianshi26);

        shipei1=new SJFX26_shipei1();

        fragments.add(shipei1);
    }
}