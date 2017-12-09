package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.GameCurrencyAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.ExChangeMoneyBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/26.
 */
public class GameCurrencyActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.game_tv)
    TextView gameTv;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.gc_none_tv)
    TextView gcNoneTv;

    private String TAG="GameCurrencyActivity--";
    private List<ExChangeMoneyBean> list=new ArrayList<>();
    private GameCurrencyAdapter gameCurrencyAdapter;

    //我的游戏币
    @Override
    protected int getLayoutId() {
        return R.layout.activity_gamecurrency;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        gameTv.setText(UserUtils.UserBalance);
        initData();
        getExChangeList(UserUtils.USER_ID);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.image_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initData(){
        gameCurrencyAdapter = new GameCurrencyAdapter(this, list);
        recyclerview.setAdapter(gameCurrencyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void getExChangeList(String userId){
        HttpManager.getInstance().getExChangeList(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG,"兑换列表="+loginInfoResult.getMsg());
                list=loginInfoResult.getData().getConversionList();
                if(list.size()>0){
                    gameCurrencyAdapter.notify(list);
                }else {
                    recyclerview.setVisibility(View.GONE);
                    gcNoneTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }


}
