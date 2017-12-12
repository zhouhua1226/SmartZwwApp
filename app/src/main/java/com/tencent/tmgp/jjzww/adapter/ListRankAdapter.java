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
import com.tencent.tmgp.jjzww.bean.UserBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class ListRankAdapter extends RecyclerView.Adapter<ListRankAdapter.ListRankViewHolder> {

    private Context mContext;
    private List<UserBean> mDatas=new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;
    private int picter[]={R.drawable.rank1,R.drawable.rank2,R.drawable.rank3,R.drawable.rank4,R.drawable.rank5,
            R.drawable.rank6,R.drawable.rank7,R.drawable.rank8,R.drawable.rank9,R.drawable.rank10};

    public ListRankAdapter(Context context, List<UserBean>list){
        this.mContext=context;
        this.mDatas=list;
        mInflater=LayoutInflater.from(context);

    }

    public interface OnItemClickListener{
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    @Override
    public ListRankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=mInflater.inflate(R.layout.listrankadapter_item,parent,false);
        ListRankViewHolder listRankViewHolder=new ListRankViewHolder(view);
        return listRankViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListRankViewHolder holder, final int position) {
        UserBean bean = mDatas.get(position);
        holder.rank_ordinalNum.setText((position + 4) + "");
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
        Glide.with(mContext)
                .load(UrlUtils.USERFACEIMAGEURL + mDatas.get(position).getIMAGE_URL())
                .dontAnimate()
                .transform(new GlideCircleTransform(mContext))
                .into(holder.rank_userImag);
        holder.rank_number.setText(bean.getDOLLTOTAL());

        if (bean.getNICKNAME().equals("")) {
            holder.rank_name.setText(bean.getUSERNAME());
        } else {
            holder.rank_name.setText(bean.getNICKNAME());
        }

    }

    public void notify(List<UserBean> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ListRankViewHolder extends RecyclerView.ViewHolder{

        ImageView rank_image,rank_userImag;
        TextView rank_name,rank_number,rank_ordinalNum;
        public ListRankViewHolder(View itemView) {
            super(itemView);
            rank_userImag= (ImageView) itemView.findViewById(R.id.rankitem_userImag);
            rank_image= (ImageView) itemView.findViewById(R.id.rank_image);
            rank_name= (TextView) itemView.findViewById(R.id.rank_name);
            rank_number= (TextView) itemView.findViewById(R.id.rank_number);
            rank_ordinalNum= (TextView) itemView.findViewById(R.id.rankitem_ordinalnum);
        }
    }
}



