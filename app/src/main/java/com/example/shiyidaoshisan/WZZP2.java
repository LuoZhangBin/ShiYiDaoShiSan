package com.example.shiyidaoshisan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WZZP2 extends AppCompatActivity {

    private ImageView mWZZPCaidan;
    private ImageView mJKZPZP;
    private Button mJKZPAnniu1;
    private int[] images={R.drawable.weizhang1,R.drawable.weizhang2,R.drawable.weizhang3,R.drawable.weizhang4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzzp2);
        initView();
        mJKZPZP.setImageResource(images[getIntent().getIntExtra("index",0)]);
        mJKZPZP.setOnTouchListener(new ImageListener(mJKZPZP));

    }

    private void initView() {
        mWZZPCaidan = (ImageView) findViewById(R.id.WZZP_caidan);
        mJKZPZP = (ImageView) findViewById(R.id.JKZP_ZP);
        mJKZPAnniu1 = (Button) findViewById(R.id.JKZP_anniu1);
        mJKZPAnniu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}