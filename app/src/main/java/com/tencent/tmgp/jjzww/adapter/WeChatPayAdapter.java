package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

import java.util.List;

/**
 * Created by hongxiu on 2017/12/25.
 */
public class WeChatPayAdapter extends RecyclerView.Adapter<WeChatPayAdapter.WeChatPayHolder> {

    private Context mContext;
    private List<String> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public WeChatPayAdapter(Context context, List<String>list){
        this.mContext=context;
        this.mDatas=list;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String string);
    }

    @Override
    public WeChatPayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.wechatpay_item,parent,false);
            WeChatPayHolder weChatPayHolder=new WeChatPayHolder(view);
        return weChatPayHolder;
    }

    @Override
    public void onBindViewHolder(final WeChatPayHolder holder, final int position) {

        switch (mDatas.get(position)){
            case "0":
                break;
            case "1":
                holder.discount_image.setImageResource(R.drawable.wechatpay_item_discount9);
                holder.gold_layout.setBackgroundResource(R.drawable.wechatpay_item_gold2);
                holder.amount_tv.setText("¥30.00");
                holder.quantity_tv.setText("335");

                break;
            case "2":
                holder.discount_image.setImageResource(R.drawable.wechatpay_item_discount68);
                holder.gold_layout.setBackgroundResource(R.drawable.wechatpay_item_gold3);
                holder.amount_tv.setText("¥68.00");
                holder.quantity_tv.setText("800");
                break;
            case "3":
                holder.discount_image.setImageResource(R.drawable.wechatpay_item_discount8);
                holder.gold_layout.setBackgroundResource(R.drawable.wechatpay_item_gold4);
                holder.amount_tv.setText("¥128.00");
                holder.quantity_tv.setText("1600");
                break;
            case "4":
                holder.discount_image.setImageResource(R.drawable.wechatpay_item_discount75);
                holder.gold_layout.setBackgroundResource(R.drawable.wechatpay_item_gold5);
                holder.amount_tv.setText("¥328.00");
                holder.quantity_tv.setText("4375");
                break;
            case "5":
                holder.discount_image.setImageResource(R.drawable.wechatpay_item_discount7);
                holder.gold_layout.setBackgroundResource(R.drawable.wechatpay_item_gold6);
                holder.amount_tv.setText("¥648.00");
                holder.quantity_tv.setText("9260");
                break;
            default:
                break;
        }

        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position,holder.amount_tv.getText().toString());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class WeChatPayHolder extends RecyclerView.ViewHolder{
        private ImageView discount_image;//折扣
        private RelativeLayout gold_layout;//背景
        private TextView quantity_tv;//数量
        private TextView amount_tv;//钱

        public WeChatPayHolder(View itemView) {
            super(itemView);
            discount_image= (ImageView) itemView.findViewById(R.id.discount_image);
            gold_layout= (RelativeLayout) itemView.findViewById(R.id.gold_layout);
            quantity_tv= (TextView) itemView.findViewById(R.id.quantity_tv);
            amount_tv= (TextView) itemView.findViewById(R.id.amount_tv);
        }
    }
    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }
}
