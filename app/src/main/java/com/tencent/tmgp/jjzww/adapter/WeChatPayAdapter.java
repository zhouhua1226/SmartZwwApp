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
import com.tencent.tmgp.jjzww.bean.PayCardBean;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by hongxiu on 2017/12/25.
 */
public class WeChatPayAdapter extends RecyclerView.Adapter<WeChatPayAdapter.WeChatPayHolder> {

    private Context mContext;
    private List<PayCardBean> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public WeChatPayAdapter(Context context, List<PayCardBean>list){
        this.mContext=context;
        this.mDatas=list;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public WeChatPayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.wechatpay_item,parent,false);
            WeChatPayHolder weChatPayHolder=new WeChatPayHolder(view);
        return weChatPayHolder;
    }

    @Override
    public void onBindViewHolder(final WeChatPayHolder holder, final int position) {

        Glide.with(mContext)
                .load(UrlUtils.APPPICTERURL+mDatas.get(position).getIMAGEURL())
                .dontAnimate()
                .into(holder.paycard_image);

        if (mOnItemClickListener!=null){
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

    class WeChatPayHolder extends RecyclerView.ViewHolder{
        private ImageView paycard_image;

        public WeChatPayHolder(View itemView) {
            super(itemView);
            paycard_image= (ImageView) itemView.findViewById(R.id.paycard_image);

        }
    }

    public void notify(List<PayCardBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }
}
