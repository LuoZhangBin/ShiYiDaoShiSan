package com.example.shiyidaoshisan;

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

import com.example.shiyidaoshisan.fanfa.A;
import com.example.shiyidaoshisan.fanfa.jieko1;

import java.util.ArrayList;

public class HLDGL_shpeiqi1 extends ArrayAdapter<A> {


    public HLDGL_shpeiqi1(@NonNull Context context, ArrayList<A> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHOlder viewHOlder=new ViewHOlder();
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.hldshipei,parent,false);
            viewHOlder.wb1=convertView.findViewById(R.id.sj1);
            viewHOlder.wb2=convertView.findViewById(R.id.sj2);
            viewHOlder.wb3=convertView.findViewById(R.id.sj3);
            viewHOlder.wb4=convertView.findViewById(R.id.sj4);
            viewHOlder.duoxuan=convertView.findViewById(R.id.sj5);
            viewHOlder.anniu=convertView.findViewById(R.id.sj6);
            convertView.setTag(viewHOlder);
        }else {
            viewHOlder= (ViewHOlder) convertView.getTag();
        }
        A a=getItem(position);
        viewHOlder.wb1.setText(a.getNumber()+"");
        viewHOlder.wb2.setText(a.getRed()+"");
        viewHOlder.wb3.setText(a.getYellow()+"");
        viewHOlder.wb4.setText(a.getGreen()+"");
        viewHOlder.duoxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jieko1.jiekos(a.getNumber());
            }
        });
        viewHOlder.anniu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jieko1.jieko(a.getNumber());
            }
        });
        return convertView;
    }
    private class ViewHOlder {
        TextView wb1;
        TextView  wb2;
        TextView  wb3;
        TextView  wb4;
        CheckBox duoxuan;
        Button anniu;
    }
    jieko1 jieko1;
    public jieko1 getOnClick(){
        return jieko1;
    }

    public void setOnClick(jieko1 onClick) {
        this.jieko1=onClick;
    }
}
