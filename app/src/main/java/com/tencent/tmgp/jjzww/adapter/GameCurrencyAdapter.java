package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.ExChangeMoneyBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/17.
 */
public class GameCurrencyAdapter extends RecyclerView.Adapter<GameCurrencyAdapter.MyViewHolder> {

    private Context mContext;
    private List<ExChangeMoneyBean>mDatas;
    private LayoutInflater mInflater;

    public GameCurrencyAdapter(Context context, List<ExChangeMoneyBean>datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.gamecurrency_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title_tv.setText(mDatas.get(position).getDOLLNAME());
        holder.value_tv.setText("+"+mDatas.get(position).getCONMONEY());
        holder.times_tv.setText(mDatas.get(position).getCREATETIME());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title_tv,times_tv,value_tv;

        public MyViewHolder(View view){
            super(view);

            title_tv= (TextView) view.findViewById(R.id.title_tv);
            times_tv= (TextView) view.findViewById(R.id.times_tv);
            value_tv= (TextView) view.findViewById(R.id.value_tv);
        }

    }

    public void notify(List<ExChangeMoneyBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }




}



