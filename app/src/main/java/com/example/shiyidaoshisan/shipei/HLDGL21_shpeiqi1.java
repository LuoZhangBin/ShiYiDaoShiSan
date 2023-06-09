package com.example.shiyidaoshisan.shipei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.A;
import com.example.shiyidaoshisan.fanfa.jieko1;

import java.util.ArrayList;

public class HLDGL21_shpeiqi1 extends ArrayAdapter<A> {


    public HLDGL21_shpeiqi1(@NonNull Context context, ArrayList<A> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHOlder viewHOlder=new ViewHOlder();
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.yangshi1,parent,false);
            viewHOlder.wb1=convertView.findViewById(R.id.xxwb1);
            viewHOlder.wb2=convertView.findViewById(R.id.xxwb2);
            viewHOlder.wb3=convertView.findViewById(R.id.xxwb3);
            viewHOlder.wb4=convertView.findViewById(R.id.xxwb4);

            convertView.setTag(viewHOlder);
        }else {
            viewHOlder= (ViewHOlder) convertView.getTag();
        }
        A a=getItem(position);
        viewHOlder.wb1.setText(a.getNumber()+"");
        viewHOlder.wb2.setText(a.getRed()+"");
        viewHOlder.wb3.setText(a.getYellow()+"");
        viewHOlder.wb4.setText(a.getGreen()+"");
        return convertView;
    }
    private class ViewHOlder {
        TextView wb1;
        TextView  wb2;
        TextView  wb3;
        TextView  wb4;

    }
}
