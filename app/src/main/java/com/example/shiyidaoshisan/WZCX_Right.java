package com.example.shiyidaoshisan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shiyidaoshisan.fanfa.CLWZ_Right;

import java.util.List;

public class WZCX_Right extends ArrayAdapter<CLWZ_Right> {
    public WZCX_Right(@NonNull Context context, List<CLWZ_Right> resource) {
        super(context,0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.wzcxyu,parent,false);
            holder.FK=convertView.findViewById(R.id.yufakuan);
            holder.KF=convertView.findViewById(R.id.yukofeng);
            holder.luko=convertView.findViewById(R.id.luko);
            holder.shgu=convertView.findViewById(R.id.shgu);
            holder.shijian=convertView.findViewById(R.id.shijian1);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        CLWZ_Right right=getItem(position);
        holder.KF.setText(right.getKf()+"");
        holder.shijian.setText(right.getSj()+"");
        holder.FK.setText(right.getFk()+"");
        holder.luko.setText(right.getKL());
        holder.shgu.setText(right.getMsg());
        return convertView;
    }

    static class ViewHolder{
        TextView shijian;
        TextView luko;
        TextView shgu;
        TextView KF;
        TextView FK;}
}
