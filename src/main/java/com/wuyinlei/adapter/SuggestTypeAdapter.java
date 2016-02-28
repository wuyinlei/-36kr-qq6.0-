package com.wuyinlei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuyinlei.activity.R;

/**
 * Created by 若兰 on 2016/2/27.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class SuggestTypeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    public SuggestTypeAdapter(Context context,String[] types) {
        mInflater = LayoutInflater.from(context);
        this.types = types;
    }

    private String[]types ;

    @Override
    public int getCount() {
        return types.length;
    }

    @Override
    public Object getItem(int position) {
        return types[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView == null){
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.popup_window_item,null);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_suggest_type_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_type.setText(types[position]);
        return convertView;
    }

    class Holder{
        private TextView tv_type;
    }
}
