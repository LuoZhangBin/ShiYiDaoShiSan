package com.example.shiyidaoshisan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shiyidaoshisan.fanfa.BUS;


import java.util.List;
import java.util.Map;

public class GJCXAdapter extends BaseExpandableListAdapter {
    private Map<String, List<BUS>> map;
    private String[] arr = {"一号站台", "二号站台"};

    public GJCXAdapter(Map<String, List<BUS>> map) {
        this.map = map;
    }

    @Override
    public int getGroupCount() {
        return map.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(arr[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderFu holderFu;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ejlb, parent, false);
            holderFu = new ViewHolderFu();
            holderFu.itemIv=convertView.findViewById(R.id.item_iv);
            holderFu.itemTitle=convertView.findViewById(R.id.item_title);
            convertView.setTag(holderFu);
        } else {
            holderFu = (ViewHolderFu) convertView.getTag();
        }
        if (isExpanded) {
            holderFu.itemIv.setImageResource(R.drawable.jiantou1);
        } else {
            holderFu.itemIv.setImageResource(R.drawable.jiantou2);
        }
        holderFu.itemTitle.setText(arr[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderZi holderZi;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ejlb2, parent, false);
            holderZi = new ViewHolderZi();
            holderZi.itemDistance=convertView.findViewById(R.id.item_distance);
            holderZi.itemName=convertView.findViewById(R.id.item_name);
            convertView.setTag(holderZi);
        } else {
            holderZi = (ViewHolderZi) convertView.getTag();
        }
        BUS bus = map.get(arr[groupPosition]).get(childPosition);
        holderZi.itemName.setText(bus.getBus()==1?"一号公交车":"二号公交车");
        holderZi.itemDistance.setText(bus.getDistance()+"米");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static
    class ViewHolderFu {

        ImageView itemIv;

        TextView itemTitle;

    }

    static
    class ViewHolderZi {
        
        TextView itemName;
        TextView itemDistance;

    }
}
