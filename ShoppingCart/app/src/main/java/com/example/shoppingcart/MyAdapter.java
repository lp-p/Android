package com.example.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<Goods> list;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<Goods> list, Context context) {
        this.list=list;
        this.layoutInflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.listview_item,null,false);
            viewHolder.tv_showCount=convertView.findViewById(R.id.tv_number);
            viewHolder.tv_showName=convertView.findViewById(R.id.tv_name);
            viewHolder.tv_showPrice=convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Goods goods=(Goods) getItem(position);
        viewHolder.tv_showPrice.setText(goods.getPrice());
        viewHolder.tv_showName.setText(goods.getName());
        viewHolder.tv_showCount.setText(goods.getCount());
        return convertView;
    }
    class ViewHolder{
        TextView tv_showName,tv_showPrice,tv_showCount;
    }
}

