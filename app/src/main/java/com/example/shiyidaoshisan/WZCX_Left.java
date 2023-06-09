package com.example.shiyidaoshisan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shiyidaoshisan.fanfa.CLWZ_Left;

import java.util.List;

public class WZCX_Left extends ArrayAdapter<CLWZ_Left> {
    public interface MyClick{
        void imageClick(int position);
    }
    private MyClick myClick;

    public WZCX_Left(@NonNull Context context, List<CLWZ_Left> resource) {
        super(context,0, resource);
    }

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.wzcxzuo,parent,false);
            viewHolder.itemCp=convertView.findViewById(R.id.chepaihao);
            viewHolder.itemFk=convertView.findViewById(R.id.fakuan);
            viewHolder.itemKf=convertView.findViewById(R.id.kofeng);
            viewHolder.itemCount=convertView.findViewById(R.id.cishu);
            viewHolder.itemRemove=convertView.findViewById(R.id.jianhao);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        CLWZ_Left left=getItem(position);
        viewHolder.itemCp.setText(left.getCp());
        viewHolder.itemFk.setText(left.getFk()+"");
        viewHolder.itemKf.setText(left.getKf()+"");
        viewHolder.itemCount.setText(left.getCs());
        viewHolder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClick.imageClick(position);
            }
        });
        return convertView;
    }


    static
    class ViewHolder {

        TextView itemCp;

        TextView itemCount;

        TextView itemKf;

        TextView itemFk;

        ImageView itemRemove;

    }
}
