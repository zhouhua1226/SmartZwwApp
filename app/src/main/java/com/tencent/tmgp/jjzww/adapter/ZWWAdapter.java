package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.bean.ZwwRoomBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/24.
 */
public class ZWWAdapter extends RecyclerView.Adapter<ZWWAdapter.ZWWViewHolder> {
    private Context mContext;
    private List<ZwwRoomBean> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public ZWWAdapter(Context context, List<ZwwRoomBean> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void addData(){

    }

    @Override
    public ZWWViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zwwadapter_item, parent, false);
        ZWWViewHolder zwwViewHolder = new ZWWViewHolder(view);
        return zwwViewHolder;
    }

    @Override
    public void onBindViewHolder(ZWWViewHolder holder, final int position) {
        ZwwRoomBean bean = mDatas.get(position);
        //holder.money.setText(String.format(mContext.getString(R.string.money_temp), bean.getDOLL_GOLD()));
        holder.money.setText(bean.getDOLL_GOLD()+"/次");
        holder.name.setText(bean.getDOLL_NAME());
        Glide.with(mContext).load(UrlUtils.PICTUREURL + bean.getDOLL_URL()).error(R.drawable.loading).into(holder.imageView);
        holder.itemView.setEnabled(true);
        if (bean.getDOLL_STATE().equals("10")) {
            holder.connectIv.setImageResource(R.drawable.green_point);
            holder.connectTv.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.connectTv.setText(mContext.getString(R.string.free_text));
        } else if (bean.getDOLL_STATE().equals("11")) {
            holder.connectIv.setImageResource(R.drawable.red_point);
            holder.connectTv.setTextColor(mContext.getResources().getColor(R.color.redx));
            holder.connectTv.setText(mContext.getString(R.string.busy_text));
        } else {
            holder.connectIv.setImageResource(R.drawable.red_point);
            holder.connectTv.setTextColor(mContext.getResources().getColor(R.color.redx));
            holder.connectTv.setText(mContext.getString(R.string.preserve_text));
            holder.itemView.setEnabled(false);
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

    class ZWWViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView money;
        private TextView connectTv;
        private ImageView connectIv;

        public ZWWViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.moppet_image);
            name = (TextView) itemView.findViewById(R.id.moppet_name_tv);
            money = (TextView) itemView.findViewById(R.id.moppet_money_tv);
            connectTv = (TextView) itemView.findViewById(R.id.moppet_connect_tv);
            connectIv = (ImageView) itemView.findViewById(R.id.moppet_connect_iv);
        }
    }

    public void notify(List<ZwwRoomBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }
}