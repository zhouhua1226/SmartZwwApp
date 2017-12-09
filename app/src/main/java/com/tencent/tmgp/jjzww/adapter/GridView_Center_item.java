package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

import java.util.List;

/**
 * Created by hongxiu on 2017/9/25.
 */
public class GridView_Center_item extends BaseAdapter {

    private Context context;
    private List<String>list;


    public GridView_Center_item(Context context,List<String>list){
         this.context=context;
        this.list=list;

    }
    @Override
    public int getCount() {
        if (0 == list.size() % 2) {
            return list.size();
        } else {
            return list.size() + 1;
        }
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
        final ViewHolder holder;

        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.center_item,null);
            holder.moppet_image= (ImageView) convertView.findViewById(R.id.moppet_image);
            holder.moppet_name_tv= (TextView) convertView.findViewById(R.id.moppet_name_tv);
            holder.mopper_time= (TextView) convertView.findViewById(R.id.mopper_time);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    static class ViewHolder{

        ImageView moppet_image;//娃娃图片
        TextView moppet_name_tv;//娃娃名字
        TextView mopper_time;//时间


    }


}
