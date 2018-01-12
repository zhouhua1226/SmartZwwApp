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
import com.tencent.tmgp.jjzww.bean.RoomBean;
import com.tencent.tmgp.jjzww.bean.ZwwRoomBean;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideRoundTransform;

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
                .error(R.drawable.loading).placeholder(R.drawable.loading)
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
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
//        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        if(position % 2 == 0){
//            layoutParams.setMargins(20,15,0,0);
//        }else {
//            layoutParams.setMargins(15,20,20,0);
//        }
//          holder.parent_layout.setLayoutParams(layoutParams);
//        String rtmpUrl1 = bean.getCameras().get(0).getRtmpUrl();
//        String rtmpUrl2 = bean.getCameras().get(1).getRtmpUrl();
//        String serviceName1=bean.getCameras().get(0).getServerName();
//        String serviceName2=bean.getCameras().get(1).getServerName();
//        String liveStream1=bean.getCameras().get(0).getLivestream();
//        String liveStream2=bean.getCameras().get(1).getLivestream();
//        String idToken="?token="+ UserUtils.SRSToken.getToken()
//                +"&expire="+UserUtils.SRSToken.getExpire()
//                +"&tid="+UserUtils.SRSToken.getTid()
//                +"&time="+UserUtils.SRSToken.getTime()
//                +"&type="+UserUtils.SRSToken.getType()
//                +"/";
//        String url1=rtmpUrl1+serviceName1+idToken+liveStream1;
//        String url2=rtmpUrl2+serviceName2+idToken+liveStream2;
//        Utils.showLogE("<<<<<<<<<<<<<<<<","房间推流地址1="+url1);
//        Utils.showLogE("<<<<<<<<<<<<<<<<","房间推流地址2="+url2);

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
        private RelativeLayout parent_layout;

        public ZWWViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.moppet_image);
            name = (TextView) itemView.findViewById(R.id.moppet_name_tv);
            money = (TextView) itemView.findViewById(R.id.moppet_money_tv);
            connectIv = (ImageView) itemView.findViewById(R.id.moppet_connect_iv);
            connectTv= (TextView) itemView.findViewById(R.id.moppet_connect_tv);
            parent_layout= (RelativeLayout) itemView.findViewById(R.id.zwwadapter_item_parent_layout);
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