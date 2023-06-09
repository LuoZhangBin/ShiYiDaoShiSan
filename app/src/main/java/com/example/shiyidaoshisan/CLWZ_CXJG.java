package com.example.shiyidaoshisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.fanfa.CLWZ_Left;
import com.example.shiyidaoshisan.fanfa.CLWZ_Right;

import java.util.ArrayList;
import java.util.List;

public class CLWZ_CXJG extends AppCompatActivity {

    private ImageView mWZCXTiangjia;
    private ListView mWZCXList1;
    private ListView mWZCXList2;
    private List<CLWZ_Left> clwzLefts;
    private List<CLWZ_Right> clwzRights, indexRight;
    private WZCX_Left leftAdapter;
    private WZCX_Right rightAdapter;
    private String nowCp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clwz_cxjg);
        initView();
        clwzLefts = CLWZ.getClwzLefts();
        clwzRights = CLWZ.getClwzRights();
        indexRight = new ArrayList<>();
        shuju1();
    }

    private void shuju1() {
        nowCp = clwzLefts.get(0).getCp();
        leftAdapter = new WZCX_Left(this, clwzLefts);
        mWZCXList1.setAdapter(leftAdapter);
        leftAdapter.setMyClick(new WZCX_Left.MyClick() {
            @Override
            public void imageClick(int position) {
                CLWZ_Left clwzLeft = clwzLefts.get(position);
                for (int i = clwzRights.size() - 1; i >= 0; i--) {
                    CLWZ_Right clwzRight = clwzRights.get(i);
                    if (clwzRight.getCp().equals(clwzLeft.getCp())) {
                        clwzRights.remove(i);
                    }
                }
                clwzLefts.remove(position);
                leftAdapter.notifyDataSetChanged();
                if (clwzLefts.size() != 0) {
                    nowCp = clwzLefts.get(0).getCp();
                }
                initRightView();
            }
        });
        mWZCXList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nowCp = clwzLefts.get(i).getCp();
                initRightView();
            }

        });
        initRightView();
    }

    private void initView() {
        mWZCXTiangjia = (ImageView) findViewById(R.id.WZCX_tiangjia);
        mWZCXTiangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWZCXList1 = (ListView) findViewById(R.id.WZCX_list1);
        mWZCXList2 = (ListView) findViewById(R.id.WZCX_list2);
    }
    private void initRightView() {
        indexRight.clear();
        for (int i = 0; i < clwzRights.size(); i++) {
            CLWZ_Right clwzRight = clwzRights.get(i);
            if (clwzRight.getCp().equals(nowCp)) {
                indexRight.add(clwzRight);
            }
        }
        if (rightAdapter == null) {
            rightAdapter = new WZCX_Right(this, indexRight);
            mWZCXList2.setAdapter(rightAdapter);
            mWZCXList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(CLWZ_CXJG.this, WZZP.class));
                }
            });
        } else {
            rightAdapter.notifyDataSetChanged();
        }
    }

}