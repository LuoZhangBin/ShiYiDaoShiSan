package com.example.shiyidaoshisan.shipei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shiyidaoshisan.R;
import com.example.shiyidaoshisan.fanfa.B;
import com.example.shiyidaoshisan.fanfa.jieko2;

import java.util.List;

public class ZHGL_shipei22 extends ArrayAdapter<B> {

    public ZHGL_shipei22(@NonNull Context context, List<B> resource) {
        super(context,0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if (view==null){
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shipei22,parent,false);
                viewHolder.ID1=view.findViewById(R.id.bianhao22);
                viewHolder.YE=view.findViewById(R.id.yvei22);
                viewHolder.PL=view.findViewById(R.id.duoxuan22);
                viewHolder.AN=view.findViewById(R.id.anniu22);
                view.setTag(viewHolder);
        }else {
                viewHolder= (ViewHolder) view.getTag();
        }
        B b=getItem(position);
        viewHolder.ID1.setText(b.getNumber()+"");
        viewHolder.YE.setText(b.getBalance()+"");
        viewHolder.PL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.jiekos(b.getNumber()+"");
            }
        });
        viewHolder.AN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.jieko(b.getNumber()+"");

            }
        });
        return view;
    }
    private class ViewHolder{
        TextView ID1;
        TextView YE;
        CheckBox PL;
        Button AN;
    }
    jieko2 onClick;
    public jieko2 getOnClick(){
        return onClick;
    }

    public void setOnClick(jieko2 onClick) {
        this.onClick=onClick;
    }
}
