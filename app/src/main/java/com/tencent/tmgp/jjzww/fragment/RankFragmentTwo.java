package com.tencent.tmgp.jjzww.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.ListRankAdapter;
import com.tencent.tmgp.jjzww.base.BaseFragment;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.UserBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hongxiu on 2017/11/23.
 */
public class RankFragmentTwo extends BaseFragment {
    private static final String TAG = "RankFragmentTwo-";
    @BindView(R.id.ranktwo_recyclerbiew)
    RecyclerView ranktwoRecyclerbiew;
    @BindView(R.id.rank_secondtx_imag)
    ImageView rankSecondtxImag;
    @BindView(R.id.rank_secondyp_imag)
    ImageView rankSecondypImag;
    @BindView(R.id.rank_secondName_tv)
    TextView rankSecondNameTv;
    @BindView(R.id.rank_secondNum_tv)
    TextView rankSecondNumTv;
    @BindView(R.id.rank_secondName_layout)
    LinearLayout rankSecondNameLayout;
    @BindView(R.id.rank_firsttx_imag)
    ImageView rankFirsttxImag;
    @BindView(R.id.rank_firstyp_imag)
    ImageView rankFirstypImag;
    @BindView(R.id.rank_firstName_tv)
    TextView rankFirstNameTv;
    @BindView(R.id.rank_firstNum_tv)
    TextView rankFirstNumTv;
    @BindView(R.id.rank_firstName_layout)
    LinearLayout rankFirstNameLayout;
    @BindView(R.id.rank_thirdtx_imag)
    ImageView rankThirdtxImag;
    @BindView(R.id.rank_thirdyp_imag)
    ImageView rankThirdypImag;
    @BindView(R.id.rank_thirdName_tv)
    TextView rankThirdNameTv;
    @BindView(R.id.rank_thirdNum_tv)
    TextView rankThirdNumTv;
    @BindView(R.id.rank_thirdName_layout)
    LinearLayout rankThirdNameLayout;
    @BindView(R.id.topLy)
    LinearLayout topLy;
    @BindView(R.id.rank_userImag)
    ImageView rankUserImag;
    @BindView(R.id.rank_name)
    TextView rankName;
    @BindView(R.id.rank_number)
    TextView rankNumber;
    @BindView(R.id.rankitem_ordinalnum)
    TextView rankitemOrdinalnum;
    @BindView(R.id.rank_my_layout)
    RelativeLayout rankMyLayout;

    private ListRankAdapter listRankAdapter;
    private List<UserBean> list = new ArrayList<>();
    private List<UserBean> rankList=new ArrayList<>();
    private UserBean myBean = new UserBean();
    private UserBean firstBean=new UserBean();
    private UserBean secondBean=new UserBean();
    private UserBean thirdBean=new UserBean();
    private String myNum = "";
    private boolean isOutTen=true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranktwo;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
        initlist();
        //OnClick();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            initlist();
        }
    }

    private void initData() {
        listRankAdapter = new ListRankAdapter(getContext(), rankList);
        ranktwoRecyclerbiew.setAdapter(listRankAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ranktwoRecyclerbiew.setLayoutManager(linearLayoutManager);
        ranktwoRecyclerbiew.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void initlist() {
        HttpManager.getInstance().getListRank(new RequestSubscriber<Result<ListRankBean>>() {
            @Override
            public void _onSuccess(Result<ListRankBean> listRankBeanResult) {
                list = listRankBeanResult.getData().getAppUser();
                Utils.showLogE(TAG + "看看...", list.size() + "");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUSER_ID().equals(YsdkUtils.loginResult.getData().getAppUser().getUSER_ID())) {
                        isOutTen=false;
                    }
                }

                int length=list.size();
                if(length>=1)
                    firstBean=list.get(0);
                if(length>=2)
                    secondBean=list.get(1);
                if(length>=3)
                    thirdBean=list.get(2);
                rankList.clear();
                if(length>10){
                    for(int i=3;i<10;i++){
                        rankList.add(list.get(i));
                    }
                }else if(length>3&&length<=10){
                    for(int i=3;i<length;i++){
                        rankList.add(list.get(i));
                    }
                } else {
                    rankList=list;
                }
                Utils.showLogE(TAG + "看看？？", rankList.size() + "我的="+myNum);
                listRankAdapter.notify(rankList);
                if(isOutTen){
                    getNumRankList(UserUtils.USER_ID);   //如果当前用户在前十以外，则查询当前用户排名
                }else {
                    setViewDate(myNum);
                }

            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "RankFragmentTwo::::" + e.getMessage());
            }
        });

    }

    private void getNumRankList(String userId){
        HttpManager.getInstance().getNumRankList(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                if(result.getMsg().equals("success")){
                    myBean=result.getData().getAppUser();
                    myNum=myBean.getRANK();
                    Log.e(TAG,"我的排名="+myNum);
                    setViewDate(myNum);
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    public void OnClick() {
        listRankAdapter.setOnItemClickListener(new ListRankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                MyToast.getToast(getContext(),"我要查看"+position).show();
            }

        });
    }

    private void setViewDate(String myNum) {
        if(!myNum.equals("")) {
            rankMyLayout.setVisibility(View.VISIBLE);
            if (myBean.getNICKNAME().equals("")) {
                rankName.setText(myBean.getPHONE());
            } else {
                rankName.setText(myBean.getNICKNAME());
            }
            rankNumber.setText(myBean.getDOLLTOTAL());
            rankitemOrdinalnum.setText("第"+myNum+"名");
            Glide.with(getContext())
                    .load(UrlUtils.APPPICTERURL + myBean.getIMAGE_URL())
                    .error(R.drawable.ctrl_default_user_bg)
                    .dontAnimate()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(rankUserImag);
        }else {
            rankMyLayout.setVisibility(View.GONE);
        }

        if (firstBean.getNICKNAME().equals("")) {
            rankFirstNameTv.setText(firstBean.getPHONE());
        } else {
            rankFirstNameTv.setText(firstBean.getNICKNAME());
        }
        rankFirstNumTv.setText(firstBean.getDOLLTOTAL());
        Glide.with(getContext())
                .load(UrlUtils.APPPICTERURL + firstBean.getIMAGE_URL())
                .error(R.drawable.ctrl_default_user_bg)
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(rankFirsttxImag);

        if (secondBean.getNICKNAME().equals("")) {
            rankSecondNameTv.setText(secondBean.getPHONE());
        } else {
            rankSecondNameTv.setText(secondBean.getNICKNAME());
        }
        rankSecondNumTv.setText(secondBean.getDOLLTOTAL());
        Glide.with(getContext())
                .load(UrlUtils.APPPICTERURL + secondBean.getIMAGE_URL())
                .error(R.drawable.ctrl_default_user_bg)
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(rankSecondtxImag);

        if (thirdBean.getNICKNAME().equals("")) {
            rankThirdNameTv.setText(thirdBean.getPHONE());
        } else {
            rankThirdNameTv.setText(thirdBean.getNICKNAME());
        }
        rankThirdNumTv.setText(thirdBean.getDOLLTOTAL());
        Glide.with(getContext())
                .load(UrlUtils.APPPICTERURL + thirdBean.getIMAGE_URL())
                .error(R.drawable.ctrl_default_user_bg)
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(rankThirdtxImag);

    }

}
