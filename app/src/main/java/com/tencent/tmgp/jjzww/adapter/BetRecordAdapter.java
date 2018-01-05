package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.okgo.request.GetRequest;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.BetRecordBean;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import rx.Subscriber;

/**
 * Created by yincong on 2017/12/6 17:03
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class BetRecordAdapter extends RecyclerView.Adapter<BetRecordAdapter.MyViewHolder> {

    private Context mContext;
    private List<BetRecordBean.DataListBean> mDatas;
    private LayoutInflater mInflater;

    public BetRecordAdapter(Context context, List<BetRecordBean.DataListBean>datas){
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
            BetRecordBean.DataListBean bean=mDatas.get(position);
                holder.amount_tv.setText(String.valueOf(bean.getSETTLEMENT_GOLD()));
                String s=bean.getGUESS_ID();
                holder.periodsNum_tv.setText(s.substring(5,12));


                if (bean.getGUESS_TYPE().equals("1")){
                    holder.results_tv.setText("抓中");

                }else if (bean.getGUESS_TYPE().equals("0")){
                    holder.results_tv.setText("没抓中");

                }


                if (bean.getGUESS_KEY().equals("1")){
                holder.bettingResults_tv.setText("中");

                }else {
                    holder.bettingResults_tv.setText("不中");
                }


                if (bean.getGUESS_KEY().equals(bean.getGUESS_TYPE())){
                    holder.bettingResults_tv1.setText("/对");
                }else {
                    holder.bettingResults_tv1.setText("/错");
                    holder.bettingResults_tv.setTextColor(mContext.getResources().getColor(R.color.betrecordcolor1));
                    holder.bettingResults_tv1.setTextColor(mContext.getResources().getColor(R.color.betrecordcolor1));
                }
            }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView periodsNum_tv,results_tv,bettingResults_tv,bettingResults_tv1,amount_tv;

        public MyViewHolder(View view){
            super(view);
            periodsNum_tv= (TextView) view.findViewById(R.id.periodsNum_tv);//期号
            results_tv= (TextView) view.findViewById(R.id.results_tv);//抓取结果
            bettingResults_tv= (TextView) view.findViewById(R.id.bettingResults_tv);//投注结果
            amount_tv= (TextView) view.findViewById(R.id.amount_tv);//我的奖金
            bettingResults_tv1= (TextView) view.findViewById(R.id.bettingResults_tv1);
        }

    }

    public void notify(List<BetRecordBean.DataListBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }


}

