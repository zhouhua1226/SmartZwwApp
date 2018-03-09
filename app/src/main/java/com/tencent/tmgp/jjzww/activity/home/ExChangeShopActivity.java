package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2018/3/5 10:15
 * 修改人：
 * 修改时间：
 * 类描述：兑换商城
 */
public class ExChangeShopActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.exshop_left_tv)
    TextView exshopLeftTv;
    @BindView(R.id.exshop_right_tv)
    TextView exshopRightTv;

    private String TAG="ExChangeShopActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchangeshop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_back, R.id.exshop_left_tv, R.id.exshop_right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.exshop_left_tv:
                MyToast.getToast(getApplicationContext(),"模块开发中，敬请期待！").show();
                break;
            case R.id.exshop_right_tv:
                MyToast.getToast(getApplicationContext(),"模块开发中，敬请期待！").show();
                break;
            default:
                break;
        }
    }
}
