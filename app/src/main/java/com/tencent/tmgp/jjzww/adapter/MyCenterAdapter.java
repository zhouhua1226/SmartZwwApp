package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.home.RecordGameActivty;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongxiu on 2017/10/17.
 */
public class MyCenterAdapter extends RecyclerView.Adapter<MyCenterAdapter.CenterViewHolder> {

    private Context mContext;
    private List<VideoBackBean> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    //记录上次的位置和容器
    private LinearLayout oldContainer;
    private int oldPosition;

    public MyCenterAdapter(Context context,List<VideoBackBean>datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);
    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
            this.mOnItemClickListener=listener;
    }

    @Override
    public CenterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.center_item,parent,false);
        CenterViewHolder centerViewHolder=new CenterViewHolder(view);
        return centerViewHolder;
    }

    @Override
    public void onBindViewHolder(final CenterViewHolder holder, final int position) {
//        if((int)(holder.getView().getTag()) == position){
//            //TODO: 这里处理对应position的view设置
//
//        }
//        else{
//            //view被recycled了，重新设置view
//        }
        holder.name.setText(mDatas.get(position).getDOLL_NAME());
        holder.times.setText(Utils.getTime(mDatas.get(position).getCAMERA_DATE()));
        holder.gold_tv.setText("可兑换金币:"+mDatas.get(position).getCONVERSIONGOLD());
        if(mDatas.get(position).getPOST_STATE().equals("0")){
            holder.select_image.setVisibility(View.VISIBLE);
            holder.type.setVisibility(View.GONE);
            holder.select_image.setImageResource(R.drawable.mycatchrecord_unselect);

        }else if(mDatas.get(position).getPOST_STATE().equals("1")){
            holder.select_image.setVisibility(View.GONE);
            holder.type.setVisibility(View.VISIBLE);
            holder.type.setText("待发货");
        }else if(mDatas.get(position).getPOST_STATE().equals("2")){
            holder.select_image.setVisibility(View.GONE);
            holder.type.setVisibility(View.VISIBLE);
            holder.type.setText("已兑换");
        }

        Glide.with(mContext)
                .load(UrlUtils.APPPICTERURL+mDatas.get(position).getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(mContext))
                .into(holder.imageview);
        if (mOnItemClickListener!=null){
            holder.right_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.select_image,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
            holder.left_laytout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RecordGameActivty.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("record", mDatas.get(position));
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview,select_image;
        TextView name,times,type,gold_tv;
        RelativeLayout left_laytout,right_layout;

        public CenterViewHolder(View itemView) {
            super(itemView);
            type= (TextView) itemView.findViewById(R.id.mopper_type_tv);
            select_image= (ImageView) itemView.findViewById(R.id.mopper_select_image);
            imageview= (ImageView) itemView.findViewById(R.id.moppet_image);
            name= (TextView) itemView.findViewById(R.id.moppet_name_tv);
            times= (TextView) itemView.findViewById(R.id.mopper_time);
            left_laytout= (RelativeLayout) itemView.findViewById(R.id.center_item_left_layout);
            right_layout= (RelativeLayout) itemView.findViewById(R.id.center_item_right_layout);
            gold_tv= (TextView) itemView.findViewById(R.id.center_item_glod_tv);
        }
    }

    public void notify(List<VideoBackBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}

