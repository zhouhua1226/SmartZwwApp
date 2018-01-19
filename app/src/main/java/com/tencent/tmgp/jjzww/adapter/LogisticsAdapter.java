package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.LogisticsBean;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by yincong on 2018/1/18 13:47
 * 修改人：
 * 修改时间：
 * 类描述：物流类适配器
 */
public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.MyViewHolder1> {
    private Context mContext;
    private List<LogisticsBean> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public LogisticsAdapter(Context context, List<LogisticsBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.logisticsadapter_item,parent,false);
        MyViewHolder1 myviewHolder=new MyViewHolder1(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, final int position) {
        holder.title_tv.setText(mDatas.get(position).getID());
        holder.times_tv.setText(mDatas.get(position).getCREATE_TIME());
        holder.sendname_tv.setText("收货人："+mDatas.get(position).getCNEE_NAME());
        holder.sendphone_tv.setText(mDatas.get(position).getCNEE_PHONE());
        holder.sendaddress_tv.setText("收货地址："+mDatas.get(position).getCNEE_ADDRESS());
        if(mDatas.get(position).getREMARK().equals("")){
            holder.sendremark_tv.setText("备注：暂无备注！");
        }else {
            holder.sendremark_tv.setText("备注："+mDatas.get(position).getREMARK());
        }
        if(mDatas.get(position).getSENDBOOLEAN().equals("0")){
            holder.results_tv.setText("待发货");
            holder.wl_layout.setVisibility(View.GONE);
        }else {
            holder.results_tv.setText("已发货");
            holder.wl_layout.setVisibility(View.VISIBLE);
            holder.wl_dnum_tv.setText("物流单号："+mDatas.get(position).getFMS_ORDER_NO());
            holder.wl_name_tv.setText("物流公司："+mDatas.get(position).getFMS_NAME());
            holder.wl_time_tv.setText("发货时间："+mDatas.get(position).getFMS_TIME());
            holder.wl_remark_tv.setText(getRemarks(mDatas.get(position).getPOST_REMARK()));

        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder{

        RelativeLayout send_layout,wl_layout;
        TextView title_tv,times_tv,results_tv,sendname_tv,sendphone_tv,
                  sendaddress_tv,sendremark_tv,wl_dnum_tv,wl_name_tv,wl_remark_tv,wl_time_tv;

        public MyViewHolder1(View itemView) {
            super(itemView);
            title_tv= (TextView) itemView.findViewById(R.id.title_tv);
            times_tv= (TextView) itemView.findViewById(R.id.times_tv);
            results_tv= (TextView) itemView.findViewById(R.id.value_tv);
            sendname_tv= (TextView) itemView.findViewById(R.id.sendname_tv);
            sendphone_tv= (TextView) itemView.findViewById(R.id.sendphoto_tv);
            sendaddress_tv= (TextView) itemView.findViewById(R.id.sendaddress_tv);
            sendremark_tv= (TextView) itemView.findViewById(R.id.sendremark_tv);
            wl_dnum_tv= (TextView) itemView.findViewById(R.id.wl_dnum_tv);
            wl_name_tv= (TextView) itemView.findViewById(R.id.wl_name_tv);
            wl_time_tv= (TextView) itemView.findViewById(R.id.wl_time_tv);
            wl_remark_tv= (TextView) itemView.findViewById(R.id.wl_remark_tv);
            send_layout= (RelativeLayout) itemView.findViewById(R.id.send_layout);
            wl_layout= (RelativeLayout) itemView.findViewById(R.id.wl_layout);

        }
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public void notify(List<LogisticsBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }

    private String getTime(String times){
        String year=times.substring(0,4);
        String month=times.substring(4,6);
        String day=times.substring(6,8);
        String hour=times.substring(8,10);
        String minte=times.substring(10,12);
        String second=times.substring(12,14);
        return year+"/"+month+"/"+day+"  "+hour+":"+minte+":"+second;
    }

    private String getRemarks(String remark){
        return remark.replace("数量为：","").replace("，","个 ");
    }

}
