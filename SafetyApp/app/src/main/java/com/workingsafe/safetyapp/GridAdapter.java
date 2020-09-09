package com.workingsafe.safetyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GridAdapter extends BaseAdapter {
    private int sets = 0;

    public GridAdapter(int sets) {
        this.sets = sets;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set,parent,false);
        }else{
            view = convertView;
        }
        ((TextView)view.findViewById(R.id.setTextId)).setText(String.valueOf(position+1));
        return view;
    }
}
