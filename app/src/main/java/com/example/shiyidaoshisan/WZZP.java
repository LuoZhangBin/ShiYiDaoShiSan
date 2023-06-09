package com.example.shiyidaoshisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WZZP extends AppCompatActivity implements View.OnClickListener {

    private ImageView mZP1;
    private ImageView mZP2;
    private ImageView mZP3;
    private ImageView mZP4;
    private Button mJKZPAnniu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzzp);
        initView();
    }

    private void initView() {
        mZP1 = (ImageView) findViewById(R.id.ZP1);
        mZP2 = (ImageView) findViewById(R.id.ZP2);
        mZP3 = (ImageView) findViewById(R.id.ZP3);
        mZP4 = (ImageView) findViewById(R.id.ZP4);
        mJKZPAnniu = (Button) findViewById(R.id.JKZP_anniu);
        mZP1.setOnClickListener(this);
        mZP2.setOnClickListener(this);
        mZP3.setOnClickListener(this);
        mZP4.setOnClickListener(this);
        mJKZPAnniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,WZZP2.class);
        switch (view.getId()){
            case R.id.ZP1:
                intent.putExtra("index", 0);
                break;
            case R.id.ZP2:
                intent.putExtra("index", 1);
                break;
            case R.id.ZP3:
                intent.putExtra("index", 2);
                break;
            case R.id.ZP4:
                intent.putExtra("index", 3);
                break;
        }
        startActivity(intent);
    }
}