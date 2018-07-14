package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.PromoteEarnBean;
import com.tencent.tmgp.jjzww.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by yincong on 2018/3/19 17:57
 * 修改人：
 * 修改时间：
 * 类描述：账目流水
 */
public class AccdetailAdapter extends RecyclerView.Adapter<AccdetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<PromoteEarnBean> mDatas;
    private LayoutInflater mInflater;

    public AccdetailAdapter(Context context, List<PromoteEarnBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_accdetail, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PromoteEarnBean promoteEarnBean=mDatas.get(position);
        holder.des_tv.setText(promoteEarnBean.getLOG_STR());
        holder.amount_tv.setText(getDecimal(promoteEarnBean.getTRANS_AMT()));
        holder.time_tv.setText(Utils.getTime(promoteEarnBean.getCREATE_DATE()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView des_tv, amount_tv, time_tv;

        public MyViewHolder(View view) {
            super(view);
            des_tv = (TextView) view.findViewById(R.id.accdetail_item_des_tv);
            amount_tv= (TextView) view.findViewById(R.id.accdetail_item_amount_tv);
            time_tv = (TextView) view.findViewById(R.id.accdetail_item_time_tv);
        }

    }

    public void notify(List<PromoteEarnBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }

    private String getDecimal(String amount) {
        if (amount.equals("")) {
            return amount;
        } else{
            double d=new Double(amount);
            DecimalFormat df = new DecimalFormat("######0.00");
            return df.format(d / 100);
        }
    }

}
