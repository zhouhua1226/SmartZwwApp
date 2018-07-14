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
import com.tencent.tmgp.jjzww.bean.PromomoteBean;
import com.tencent.tmgp.jjzww.bean.VideoBackBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by yincong on 2018/3/16 11:26
 * 修改人：
 * 修改时间：
 * 类描述：加盟适配器
 */
public class JoinEarnAdapter extends RecyclerView.Adapter<JoinEarnAdapter.MyViewHolder1> {
    private Context mContext;
    private List<PromomoteBean> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public JoinEarnAdapter(Context context, List<PromomoteBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_joinearn,parent,false);
        MyViewHolder1 myviewHolder=new MyViewHolder1(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, final int position) {
        holder.name_tv.setText(mDatas.get(position).getPRO_MANAGE_NAME());
        holder.parcent_tv.setText("提成比:"+getParcent(mDatas.get(position).getRETURN_RATIO()));
        if (mOnItemClickListener != null) {
            holder.join_tv.setOnClickListener(new View.OnClickListener() {
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
        TextView name_tv,join_tv,parcent_tv;

        public MyViewHolder1(View itemView) {
            super(itemView);
            name_tv= (TextView) itemView.findViewById(R.id.joinearnitem_proname_tv);
            join_tv= (TextView) itemView.findViewById(R.id.joinearnitem_join_tv);
            parcent_tv= (TextView) itemView.findViewById(R.id.joinearnitem_parcent_tv);
        }
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public void notify(List<PromomoteBean> lists) {
        this.mDatas = lists;
        notifyDataSetChanged();
    }

    private String getParcent(String s){
        if(Utils.isEmpty(s)){
            return s;
        }else {
            double d = Double.parseDouble(s);
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMaximumFractionDigits(2);//这个1的意识是保存结果到小数点后几位，但是特别声明：这个结果已经先＊100了。
            return nf.format(d);
        }
    }

}
