package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.adapter.LogisticsAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.LogisticsBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2018/1/18 11:38
 * 修改人：
 * 修改时间：
 * 类描述：物流订单查询类
 */
public class MyLogisticsOrderActivity extends BaseActivity {

    @BindView(R.id.back_image_bt)
    ImageButton backImageBt;
    @BindView(R.id.logisticsorder_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.logisticsorder_fail_tv)
    TextView logisticsorderFailTv;

    private String TAG="MyLogisticsOrderActivity";
    private LogisticsAdapter logisticsAdapter;
    private List<LogisticsBean> list=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logisticsorder;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initdata();
        getLogisticsOrder(UserUtils.USER_ID);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initdata() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        logisticsAdapter = new LogisticsAdapter(this,list);
        recyclerView.setAdapter(logisticsAdapter);

    }

    @OnClick({R.id.back_image_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image_bt:
                finish();
                break;
            default:
                break;
        }
    }

    private void getLogisticsOrder(String userId){
        Utils.showLogE(TAG,"订单查询参数userId="+userId);
        if(Utils.isEmpty(userId)){
            recyclerView.setVisibility(View.GONE);
            logisticsorderFailTv.setVisibility(View.VISIBLE);
            return;
        }
        HttpManager.getInstance().getLogisticsOrder(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> loginInfoResult) {
                if(loginInfoResult.getMsg().equals("success")){
                    list=loginInfoResult.getData().getLogistics();
                    if(list.size()>0){
                        logisticsAdapter.notify(list);
                    }else {
                        recyclerView.setVisibility(View.GONE);
                        logisticsorderFailTv.setVisibility(View.VISIBLE);
                    }
                }else {
                    recyclerView.setVisibility(View.GONE);
                    logisticsorderFailTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void _onError(Throwable e) {
                recyclerView.setVisibility(View.GONE);
                logisticsorderFailTv.setVisibility(View.VISIBLE);
            }
        });
    }


}
