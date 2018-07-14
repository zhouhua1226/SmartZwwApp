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
 * Created by yincong on 2018/3/16 17:33
 * 修改人：
 * 修改时间：
 * 类描述：代理收益
 */
public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.MyViewHolder> {

    private Context mContext;
    private List<PromoteEarnBean> mDatas;
    private LayoutInflater mInflater;

    public AgencyAdapter(Context context, List<PromoteEarnBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_agency, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PromoteEarnBean promoteEarnBean=mDatas.get(position);
        holder.name_tv.setText(promoteEarnBean.getRES_COLUMN1());
        holder.paymoney_tv.setText(getDecimal(promoteEarnBean.getORG_TRANS_AMT()));
        holder.earnmoney_tv.setText(getDecimal(promoteEarnBean.getACC_AMT()));
        holder.time_tv.setText(Utils.getTime(promoteEarnBean.getLAST_TXN_DATE()+promoteEarnBean.getLAST_TXN_TIME()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv, paymoney_tv, earnmoney_tv, time_tv;

        public MyViewHolder(View view) {
            super(view);
            name_tv = (TextView) view.findViewById(R.id.agencyitem_username_tv);
            paymoney_tv= (TextView) view.findViewById(R.id.agencyitem_paymoney_tv);
            earnmoney_tv = (TextView) view.findViewById(R.id.agencyitem_earnmoney_tv);
            time_tv = (TextView) view.findViewById(R.id.agencyitem_time_tv);
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