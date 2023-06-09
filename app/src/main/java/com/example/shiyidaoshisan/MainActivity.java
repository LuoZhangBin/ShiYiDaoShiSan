package com.example.shiyidaoshisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mWz1;
    private TextView mWz2;
    private TextView mWz3;
    private TextView mWz4;
    private TextView mWz5;
    private TextView mWz6;
    private TextView mWz7;
    private TextView mWz8;
    private TextView mWz9;
    private TextView mWz0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mWz1 = (TextView) findViewById(R.id.wz1);
        mWz2 = (TextView) findViewById(R.id.wz2);
        mWz3 = (TextView) findViewById(R.id.wz3);
        mWz1.setOnClickListener(this);
        mWz2.setOnClickListener(this);
        mWz3.setOnClickListener(this);
        mWz4 = (TextView) findViewById(R.id.wz4);
        mWz4.setOnClickListener(this);
        mWz5 = (TextView) findViewById(R.id.wz5);
        mWz5.setOnClickListener(this);
        mWz6 = (TextView) findViewById(R.id.wz6);
        mWz7 = (TextView) findViewById(R.id.wz7);
        mWz6.setOnClickListener(this);
        mWz7.setOnClickListener(this);
        mWz8 = (TextView) findViewById(R.id.wz8);
        mWz9 = (TextView) findViewById(R.id.wz9);
        mWz8.setOnClickListener(this);
        mWz9.setOnClickListener(this);
        mWz0 = (TextView) findViewById(R.id.wz0);
        mWz0.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.wz1:
                intent.setClass(this, HLDGL.class);
                startActivity(intent);
                break;
            case R.id.wz2:
                intent.setClass(this, CLWZ.class);
                startActivity(intent);
                break;
            case R.id.wz3:
                intent.setClass(this, LKCX.class);
                startActivity(intent);
                break;
            case R.id.wz4:
                intent.setClass(this, SHZS.class);
                startActivity(intent);
                break;
            case R.id.wz5:
                intent.setClass(this, DLJM.class);
                startActivity(intent);
                break;
            case R.id.wz6:
                intent.setClass(this, SHZS17.class);
                startActivity(intent);
                break;
            case R.id.wz7:
                intent.setClass(this, WDXX.class);
                startActivity(intent);
                break;
            case R.id.wz8:
                intent.setClass(this, SZFX.class);

                startActivity(intent);
                break;
            case R.id.wz9:
                intent.setClass(this, GRZX20.class);
                startActivity(intent);
                break;
            case R.id.wz0:
                intent.setClass(this, HLDGL21.class);
                startActivity(intent);
                break;
        }
    }
}