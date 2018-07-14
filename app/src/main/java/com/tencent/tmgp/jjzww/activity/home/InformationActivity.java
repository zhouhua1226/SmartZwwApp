package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hongxiu on 2017/9/25.
 */
public class InformationActivity extends BaseActivity {
    @BindView(R.id.back_image_bt)
    ImageButton backImageBt;
    @BindView(R.id.name_rl)
    RelativeLayout nameRl;
    @BindView(R.id.password_rl)
    RelativeLayout passwordRl;
    @BindView(R.id.image_rl)
    RelativeLayout imageRl;

    //修改个人信息
    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
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

    @OnClick({R.id.back_image_bt, R.id.name_rl, R.id.image_rl, R.id.password_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image_bt:
                this.finish();
                break;
            case R.id.image_rl:
                startActivity(new Intent(this,TakePhotoActivity.class));
                finish();
                break;
            case R.id.name_rl:
                startActivity(new Intent(this,ChangNicknameAvtivity.class));
                finish();
                break;
        }
    }

}
