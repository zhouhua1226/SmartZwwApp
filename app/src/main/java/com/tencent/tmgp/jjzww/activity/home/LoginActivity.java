package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2017/12/11 09:51
 * 修改人：
 * 修改时间：
 * 类描述：登录页
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_qq_tv)
    TextView loginQqTv;
    @BindView(R.id.login_wx_tv)
    TextView loginWxTv;

    private String TAG = "LoginActivity--";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_qq_tv, R.id.login_wx_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_qq_tv:
                MyToast.getToast(getApplicationContext(),"QQ登录！").show();
                break;
            case R.id.login_wx_tv:
                MyToast.getToast(getApplicationContext(),"微信登录！").show();
                break;
        }
    }



}
