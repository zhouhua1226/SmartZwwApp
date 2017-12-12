package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;

import java.util.List;

/**
 * Created by yincong on 2017/12/6 17:03
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class BetRecordAdapter extends RecyclerView.Adapter<BetRecordAdapter.MyViewHolder> {

    private Context mContext;
    private List<BetRecordBean> mDatas;
    private LayoutInflater mInflater;

    public BetRecordAdapter(Context context, List<BetRecordBean>datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.betrecord_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title_tv.setText("第"+mDatas.get(position).getPLAYID()+"场");
        holder.room_tv.setText(mDatas.get(position).getDOLLNAME()+"房间");
        holder.value_tv.setText(mDatas.get(position).getBETMONEY());
        holder.times_tv.setText(mDatas.get(position).getBETTIME());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title_tv,times_tv,value_tv,room_tv;

        public MyViewHolder(View view){
            super(view);
            room_tv= (TextView) view.findViewById(R.id.room_tv);
            title_tv= (TextView) view.findViewById(R.id.title_tv);
            times_tv= (TextView) view.findViewById(R.id.times_tv);
            value_tv= (TextView) view.findViewById(R.id.value_tv);
        }

    }

    public void notify(List<BetRecordBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }




}

