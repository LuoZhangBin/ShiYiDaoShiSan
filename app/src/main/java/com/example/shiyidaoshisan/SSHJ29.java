package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shiyidaoshisan.shipei.SSHJ29_sheipei;
import com.example.shiyidaoshisan.shipei.SSHJ29_sheipei2;
import com.example.shiyidaoshisan.shipei.SSHJ29_sheipei3;

import java.util.ArrayList;
import java.util.List;

public class SSHJ29 extends AppCompatActivity {

    private ViewPager mVp29;
    private ImageView mAnniu129;
    private ImageView mAnniu229;
    private ImageView mAnniu329;
    private SSHJ29_sheipei sheipei1;
    private SHZS.FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private SSHJ29_sheipei2 sheipei2;
    private SSHJ29_sheipei3 sheipei3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sshj29);
        initView();
        fragmentAdapter = new SHZS.FragmentAdapter(this.getSupportFragmentManager(), fragments);
        mVp29.setOffscreenPageLimit(4);
        mVp29.setAdapter(fragmentAdapter);
        mVp29.setCurrentItem(0);
        mVp29.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                anniubianhuan(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void anniubianhuan(int pos) {
        if (pos==0){
            mAnniu129.setBackgroundResource(R.drawable.yuan);
            mAnniu229.setBackgroundResource(R.drawable.yuan2);
            mAnniu329.setBackgroundResource(R.drawable.yuan2);
        }
        if (pos==1){
            mAnniu129.setBackgroundResource(R.drawable.yuan2);
            mAnniu229.setBackgroundResource(R.drawable.yuan);
            mAnniu329.setBackgroundResource(R.drawable.yuan2);
        }
        if (pos==2){
            mAnniu129.setBackgroundResource(R.drawable.yuan2);
            mAnniu229.setBackgroundResource(R.drawable.yuan2);
            mAnniu329.setBackgroundResource(R.drawable.yuan);
        }
    }

    private void initView() {
        mVp29 = (ViewPager) findViewById(R.id.vp29);
        mAnniu129 = (ImageView) findViewById(R.id.anniu1_29);
        mAnniu229 = (ImageView) findViewById(R.id.anniu2_29);
        mAnniu329 = (ImageView) findViewById(R.id.anniu3_29);

        sheipei1 = new SSHJ29_sheipei();
        sheipei2 = new SSHJ29_sheipei2();
        sheipei3 = new SSHJ29_sheipei3();
        fragments.add(sheipei1);
        fragments.add(sheipei2);
        fragments.add(sheipei3);
    }
}