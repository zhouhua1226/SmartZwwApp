package com.tencent.tmgp.jjzww.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.tmgp.jjzww.R;

import java.util.List;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class ListRecordAdapter extends RecyclerView.Adapter<ListRecordAdapter.ListRecordViewHolder> {

    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public ListRecordAdapter(Context context, List<String>list){
        this.mContext=context;
        this.mDatas=list;
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
    public ListRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.listrecordadapter_item,parent,false);
        ListRecordViewHolder listRecordViewHolder=new ListRecordViewHolder(view);
        return listRecordViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListRecordViewHolder holder, final int position) {

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

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ListRecordViewHolder extends RecyclerView.ViewHolder{

        public ListRecordViewHolder(View itemView) {
            super(itemView);
        }
    }
}




