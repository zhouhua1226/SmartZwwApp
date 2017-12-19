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

    Unbinder unbinder1;
    private ListRankAdapter listRankAdapter;
    private List<UserBean> list = new ArrayList<>();
    private List<UserBean> rankList=new ArrayList<>();
    private UserBean myBean = new UserBean();
    private UserBean firstBean=new UserBean();
    private UserBean secondBean=new UserBean();
    private UserBean thirdBean=new UserBean();
    private String myNum = "";
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranktwo;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initlist();
        initData();
        //OnClick();

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
                    if (list.get(i).getUSER_ID().equals(YsdkUtils.loginResult.getData().getAppUser().getUSER_ID())&&i>9) {
                        myBean = list.get(i);
                        myNum = (i + 1) + "";
                        Log.e(TAG,"我的排名="+myNum);
                    }
                }
                firstBean=list.get(0);
                secondBean=list.get(1);
                thirdBean=list.get(2);
                int length=list.size();
                rankList.clear();
                if(length>10){
                    for(int i=3;i<10;i++){
                        rankList.add(list.get(i));
                    }
                }else {
                    rankList=list;
                }
                Utils.showLogE(TAG + "看看？？", rankList.size() + "");
                listRankAdapter.notify(rankList);
                setViewDate(myNum);

            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "RankFragmentTwo::::" + e.getMessage());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
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
                    .load(UrlUtils.USERFACEIMAGEURL + myBean.getIMAGE_URL())
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
                .load(UrlUtils.USERFACEIMAGEURL + firstBean.getIMAGE_URL())
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
                .load(UrlUtils.USERFACEIMAGEURL + secondBean.getIMAGE_URL())
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
                .load(UrlUtils.USERFACEIMAGEURL + thirdBean.getIMAGE_URL())
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(getContext()))
                .into(rankThirdtxImag);

    }

}
