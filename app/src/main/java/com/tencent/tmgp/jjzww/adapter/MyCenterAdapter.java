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
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/17.
 */
public class MyCenterAdapter extends RecyclerView.Adapter<MyCenterAdapter.CenterViewHolder> {

    private Context mContext;
    private List<VideoBackBean> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

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

        holder.name.setText(mDatas.get(position).getDOLLNAME());
        holder.times.setText(Utils.getTime(mDatas.get(position).getCREATETIME()));
        Glide.with(mContext)
                .load(UrlUtils.PICTUREURL+mDatas.get(position).getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(mContext))
                .into(holder.imageview);
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });

        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview;
        TextView name,times;

        public CenterViewHolder(View itemView) {
            super(itemView);
            imageview= (ImageView) itemView.findViewById(R.id.moppet_image);
            name= (TextView) itemView.findViewById(R.id.moppet_name_tv);
            times= (TextView) itemView.findViewById(R.id.mopper_time);
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

