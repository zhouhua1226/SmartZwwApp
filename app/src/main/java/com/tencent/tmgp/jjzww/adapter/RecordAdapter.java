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
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/17.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder1> {
    private Context mContext;
    private List<VideoBackBean> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public RecordAdapter(Context context, List<VideoBackBean> datas){
        this.mContext=context;
        this.mDatas=datas;
        mInflater=LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.recordadpter_item,parent,false);
        MyViewHolder1 myviewHolder=new MyViewHolder1(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, final int position) {
        holder.name_tv.setText(mDatas.get(position).getDOLLNAME());
        holder.times_tv.setText(getTime(mDatas.get(position).getCREATETIME()));
        holder.results_tv.setText("抓取成功");
        Glide.with(mContext)
                .load(UrlUtils.PICTUREURL+mDatas.get(position).getDOLL_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(mContext))
                .into(holder.title_img);

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

        ImageView title_img;
        TextView name_tv,times_tv,results_tv;

        public MyViewHolder1(View itemView) {
            super(itemView);
            title_img= (ImageView) itemView.findViewById(R.id.title_img);
            name_tv= (TextView) itemView.findViewById(R.id.name_tv);
            times_tv= (TextView) itemView.findViewById(R.id.times_tv);
            results_tv= (TextView) itemView.findViewById(R.id.results_tv);
        }
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public void notify(List<VideoBackBean> lists) {
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
}

