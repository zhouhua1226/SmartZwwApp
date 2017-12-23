package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/18.
 */
public class RecordGameTwoActivity extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_service)
    ImageButton imageService;

    @Override
    protected int getLayoutId() {
        //已经发货页面
        return R.layout.activity_recordgametwo;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }

    @OnClick({R.id.image_back, R.id.image_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.image_service:
                MyToast.getToast(this,"客服小姐姐").show();
                break;
        }
    }
}
