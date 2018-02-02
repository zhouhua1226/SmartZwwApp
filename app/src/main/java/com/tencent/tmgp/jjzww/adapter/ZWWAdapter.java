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
import com.tencent.tmgp.jjzww.bean.RoomBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/24.
 */
public class ZWWAdapter extends RecyclerView.Adapter<ZWWAdapter.ZWWViewHolder> {
    private Context mContext;
    private List<RoomBean> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public ZWWAdapter(Context context, List<RoomBean> list) {
        this.mContext = context;
        this.mDatas = list;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public ZWWViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zwwadapter_item, parent, false);
        ZWWViewHolder zwwViewHolder = new ZWWViewHolder(view);
        return zwwViewHolder;
    }

    @Override
    public void onBindViewHolder(ZWWViewHolder holder, final int position) {
        RoomBean bean = mDatas.get(position);
        holder.money.setText(bean.getDollGold()+"");
        holder.name.setText(bean.getDollName());
        Glide.with(mContext).load(UrlUtils.APPPICTERURL + bean.getDollUrl())
                .error(R.drawable.loading)
                .into(holder.imageView);
        holder.itemView.setEnabled(true);
        if (bean.getDollState().equals("11")) {
            holder.connectIv.setImageResource(R.drawable.ctrl_work_icon);
            holder.connectTv.setBackgroundResource(R.drawable.room_statue_free_bg);
            holder.connectTv.setText("空闲中");
        } else if (bean.getDollState().equals("10")) {
            holder.connectIv.setImageResource(R.drawable.ctrl_idling_icon);
            holder.connectTv.setBackgroundResource(R.drawable.room_statue_busy_bg);
            holder.connectTv.setText("游戏中");
        } else {
            holder.connectIv.setImageResource(R.drawable.ctrl_repair_icon);
            holder.itemView.setEnabled(false);
            holder.connectTv.setText("维护中");
            holder.connectTv.setBackgroundResource(R.drawable.room_statue_sleep_bg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ZWWViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView money,connectTv;
        private ImageView connectIv;

        public ZWWViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.moppet_image);
            name = (TextView) itemView.findViewById(R.id.moppet_name_tv);
            money = (TextView) itemView.findViewById(R.id.moppet_money_tv);
            connectIv = (ImageView) itemView.findViewById(R.id.moppet_connect_iv);
            connectTv= (TextView) itemView.findViewById(R.id.moppet_connect_tv);
        }
    }

    public void notify(List<RoomBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }
}