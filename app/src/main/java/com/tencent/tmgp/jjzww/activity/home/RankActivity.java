package com.tencent.tmgp.jjzww.activity.home;

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
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.ListRankBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.UserBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yincong on 2018/3/21 16:03
 * 修改人：
 * 修改时间：
 * 类描述：排行榜类
 */
public class RankActivity extends BaseActivity{

    private static final String TAG = "RankActivity-";
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
    @BindView(R.id.ranktwo_catchtitle_tv)
    TextView ranktwoCatchtitleTv;
    @BindView(R.id.ranktwo_guesstitle_tv)
    TextView ranktwoGuesstitleTv;
    Unbinder unbinder;

    private ListRankAdapter listRankAdapter;
    private List<UserBean> list = new ArrayList<>();
    private List<UserBean> rankList = new ArrayList<>();
    private UserBean myBean=new UserBean();
    private UserBean firstBean=new UserBean();
    private UserBean secondBean=new UserBean();
    private UserBean thirdBean=new UserBean();
    private String myNum = "";
    private boolean isOutTen = true;
    private int isShowType=1;  //1:展示娃娃榜  2:展示竞猜榜

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranktwo;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initData();
        getRankDollList(UserUtils.USER_ID);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initData() {
        setShowChangeView(isShowType);
        listRankAdapter = new ListRankAdapter(this, rankList,isShowType);
        ranktwoRecyclerbiew.setAdapter(listRankAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ranktwoRecyclerbiew.setLayoutManager(linearLayoutManager);
        ranktwoRecyclerbiew.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initlist() {
        HttpManager.getInstance().getListRank(new RequestSubscriber<Result<ListRankBean>>() {
            @Override
            public void _onSuccess(Result<ListRankBean> listRankBeanResult) {
                list = listRankBeanResult.getData().getList();
                Utils.showLogE(TAG + "看看...", list.size() + "");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUSER_ID().equals(UserUtils.USER_ID)) {
                        isOutTen = false;
                    }
                }

                int length = list.size();
                if (length >= 1)
                    firstBean = list.get(0);
                if (length >= 2)
                    secondBean = list.get(1);
                if (length >= 3)
                    thirdBean = list.get(2);
                rankList.clear();
                if (length > 10) {
                    for (int i = 3; i < 10; i++) {
                        rankList.add(list.get(i));
                    }
                } else if (length > 3 && length <= 10) {
                    for (int i = 3; i < length; i++) {
                        rankList.add(list.get(i));
                    }
                } else {
                    rankList = list;
                }
                Utils.showLogE(TAG + "看看？？", rankList.size() + "我的=" + myNum);
                listRankAdapter.notify(rankList,isShowType);
                if (isOutTen) {
                    getNumRankList(UserUtils.USER_ID);   //如果当前用户在前十以外，则查询当前用户排名
                } else {
                    setViewDate(myNum);
                }

            }

            @Override
            public void _onError(Throwable e) {
                Utils.showLogE(TAG, "RankFragmentTwo::::" + e.getMessage());
            }
        });

    }

    private void getNumRankList(String userId) {
        HttpManager.getInstance().getNumRankList(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> result) {
                if (result.getMsg().equals("success")) {
                    myBean = result.getData().getAppUser();
                    myNum = myBean.getRANK();
                    Log.e(TAG, "我的排名=" + myNum);
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
        if (firstBean.getNICKNAME().equals("")) {
            rankFirstNameTv.setText(firstBean.getPHONE());
        } else {
            rankFirstNameTv.setText(firstBean.getNICKNAME());
        }
        rankFirstNumTv.setText(getShowRankNum(firstBean));
        Glide.with(this)
                .load(UrlUtils.APPPICTERURL + firstBean.getIMAGE_URL())
                .error(R.drawable.ctrl_default_user_bg)
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(rankFirsttxImag);

        if (secondBean.getNICKNAME().equals("")) {
            rankSecondNameTv.setText(secondBean.getPHONE());
        } else {
            rankSecondNameTv.setText(secondBean.getNICKNAME());
        }
        rankSecondNumTv.setText(getShowRankNum(secondBean));
        Glide.with(this)
                .load(UrlUtils.APPPICTERURL + secondBean.getIMAGE_URL())
                .error(R.drawable.ctrl_default_user_bg)
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(rankSecondtxImag);

        if (thirdBean.getNICKNAME().equals("")) {
            rankThirdNameTv.setText(thirdBean.getPHONE());
        } else {
            rankThirdNameTv.setText(thirdBean.getNICKNAME());
        }
        rankThirdNumTv.setText(getShowRankNum(thirdBean));
        Glide.with(this)
                .load(UrlUtils.APPPICTERURL + thirdBean.getIMAGE_URL())
                .error(R.drawable.ctrl_default_user_bg)
                .dontAnimate()
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(rankThirdtxImag);

        int num= Integer.parseInt(myNum);
        if (num>20) {
            rankMyLayout.setVisibility(View.VISIBLE);
            if (myBean.getNICKNAME().equals("")) {
                rankName.setText(myBean.getPHONE());
            } else {
                rankName.setText(myBean.getNICKNAME());
            }
            rankNumber.setText(getShowRankNum(myBean));
            rankitemOrdinalnum.setText("第" + myNum + "名");
            Glide.with(this)
                    .load(UrlUtils.APPPICTERURL + myBean.getIMAGE_URL())
                    .error(R.drawable.ctrl_default_user_bg)
                    .dontAnimate()
                    .transform(new GlideCircleTransform(this))
                    .into(rankUserImag);
        } else {
            rankMyLayout.setVisibility(View.GONE);
        }

    }

    private String getShowRankNum(UserBean userBean){
        if(isShowType==1){
            return userBean.getDOLLTOTAL();
        }else {
            return userBean.getBET_NUM()+"";
        }
    }


    @OnClick({R.id.ranktwo_catchtitle_tv, R.id.ranktwo_guesstitle_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ranktwo_catchtitle_tv:
                if(isShowType==1){
                    return;
                }
                isShowType=1;
                setShowChangeView(isShowType);
                getRankDollList(UserUtils.USER_ID);
                break;
            case R.id.ranktwo_guesstitle_tv:
                if(isShowType==2){
                    return;
                }
                isShowType=2;
                setShowChangeView(isShowType);
                getRankBetList(UserUtils.USER_ID);
                break;
            default:
                break;
        }
    }

    private void setShowChangeView(int i){
        if(i==2){
            ranktwoGuesstitleTv.setBackgroundResource(R.drawable.white_circleborder);
            ranktwoGuesstitleTv.setTextColor(getResources().getColor(R.color.apptheme_bg));
            ranktwoCatchtitleTv.setBackgroundResource(R.color.apptheme_bg);
            ranktwoCatchtitleTv.setTextColor(getResources().getColor(R.color.white));

        }else {
            ranktwoCatchtitleTv.setBackgroundResource(R.drawable.white_circleborder);
            ranktwoCatchtitleTv.setTextColor(getResources().getColor(R.color.apptheme_bg));
            ranktwoGuesstitleTv.setBackgroundResource(R.color.apptheme_bg);
            ranktwoGuesstitleTv.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void getRankBetList(String userId){
        HttpManager.getInstance().getRankBetList(userId, new RequestSubscriber<Result<ListRankBean>>() {
            @Override
            public void _onSuccess(Result<ListRankBean> result) {
                if(result.getMsg().equals(Utils.HTTP_OK)){
                    dealData(result);
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    private void getRankDollList(String userId){
        HttpManager.getInstance().getRankDollList(userId, new RequestSubscriber<Result<ListRankBean>>() {
            @Override
            public void _onSuccess(Result<ListRankBean> result) {
                if(result.getMsg().equals(Utils.HTTP_OK)){
                    dealData(result);
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    /**
     * 接口数据处理
     * @param result
     */
    private void dealData(Result<ListRankBean> result){
        if(list.size()!=0){
            list.clear();
        }
        list = result.getData().getList();
        myBean=result.getData().getAppUser();
        myNum=myBean.getRANK();
        Utils.showLogE(TAG + "看看...", list.size() + "");
        int length = list.size();
        if (length >= 1)
            firstBean = list.get(0);
        if (length >= 2)
            secondBean = list.get(1);
        if (length >= 3)
            thirdBean = list.get(2);
        rankList.clear();
        if (length > 20) {
            for (int i = 3; i < 20; i++) {
                rankList.add(list.get(i));
            }
        } else if (length > 3 && length <= 20) {
            for (int i = 3; i < length; i++) {
                rankList.add(list.get(i));
            }
        } else {
            rankList = list;
        }
        Utils.showLogE(TAG + "看看？？", rankList.size() + "我的=" + myNum);
        listRankAdapter.notify(rankList,isShowType);
        setViewDate(myNum);
    }


}
