package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.CoinRecordAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.CoinListBean;
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
 * Created by mi on 2018/5/31.
 */

public class CoinRecordActivity extends BaseActivity {
    @BindView(R.id.coinrecode_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.coin_fail_tv)
    TextView coinFailTv;
    private List<CoinListBean.CoinBean> list = new ArrayList<>();
    private CoinRecordAdapter mRecordAdapter;

    //修改个人信息
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_record;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initDate();
        getCoinRecord(UserUtils.USER_ID);
    }



    @OnClick({R.id.image_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
        }
    }

    private void initDate() {

        mRecordAdapter = new CoinRecordAdapter(this, list);
        mRecyclerview.setAdapter(mRecordAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void getCoinRecord(String userId) {
        HttpManager.getInstance().getCoinRecord(userId, new RequestSubscriber<Result<CoinListBean>>() {
            @Override
            public void _onSuccess(Result<CoinListBean> loginInfoResult) {
                if (loginInfoResult.getMsg().equals("success")) {
                    list = loginInfoResult.getData().getDataList();
                    if (list.size() > 0) {
                            mRecordAdapter.notify(list);
                    } else {
                        mRecyclerview.setVisibility(View.GONE);
                        coinFailTv.setVisibility(View.VISIBLE);
                    }
                } else {
                    mRecyclerview.setVisibility(View.GONE);
                    coinFailTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void _onError(Throwable e) {
                mRecyclerview.setVisibility(View.GONE);
                coinFailTv.setVisibility(View.VISIBLE);
            }
        });
    }

}
