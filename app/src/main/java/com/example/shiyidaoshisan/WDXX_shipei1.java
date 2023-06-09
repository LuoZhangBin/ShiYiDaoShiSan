package com.example.shiyidaoshisan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.shuju;

import java.util.ArrayList;
import java.util.List;

public class WDXX_shipei1 extends BaseAdapter {

    List<shuju> aa=new ArrayList<>();
    Context cc;

    public WDXX_shipei1(List<shuju> a, Context c){

        aa=a;
        cc=c;

    }
    @Override
    public int getCount() {
        return aa.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=LayoutInflater.from(cc);
        view=layoutInflater.inflate(R.layout.yangshi1,null);

        TextView wb1=view.findViewById(R.id.xxwb1);
        TextView wb2=view.findViewById(R.id.xxwb2);
        TextView wb3=view.findViewById(R.id.xxwb3);
        TextView wb4=view.findViewById(R.id.xxwb4);


        wb1.setText(aa.get(i).s1);
        wb2.setText(aa.get(i).s2);
        wb3.setText(aa.get(i).s3);
        wb4.setText(aa.get(i).s4);
        return view;
    }
}
