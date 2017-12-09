package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.FillingCurrencyDialog;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/11/15.
 */
public class SelectRechargeTypeActiivty extends BaseActivity {
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_service)
    ImageButton imageService;
    @BindView(R.id.select_account_tv)
    TextView selectAccountTv;
    @BindView(R.id.select_money_tv)
    TextView selectMoneyTv;
    @BindView(R.id.layout_wechat)
    RelativeLayout layoutWechat;
    private FillingCurrencyDialog fillingCurrencyDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectrechargetype;
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
    protected void onResume() {
        super.onResume();
        getUserPhoneAndBalance();

    }

    private void getUserPhoneAndBalance(){
        String userPhone=UserUtils.UserPhone;
        String userBalance=UserUtils.UserBalance;
        if(!Utils.isEmpty(userPhone)&&!Utils.isEmpty(userBalance)){
            selectAccountTv.setText(userPhone);
            selectMoneyTv.setText(userBalance);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_back, R.id.image_service, R.id.layout_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_service:
                MyToast.getToast(this,"我是客服").show();
                break;
            case R.id.layout_wechat:
                getMoney();

                break;
        }
    }

    private void getMoney() {
        fillingCurrencyDialog = new FillingCurrencyDialog(this, R.style.easy_dialog_style);
        fillingCurrencyDialog.show();
        fillingCurrencyDialog.setDialogClickListener(myDialogClick);
    }

    private FillingCurrencyDialog.MyDialogClick myDialogClick = new FillingCurrencyDialog.MyDialogClick() {
        @Override
        public void getMoney10(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }

        @Override
        public void getMoney20(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }

        @Override
        public void getMoney50(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }

        @Override
        public void getMoney100(String money) {
            Intent intent = new Intent(SelectRechargeTypeActiivty.this, WeChatPayActivity.class);
            intent.putExtra("money", money);
            startActivity(intent);
        }
    };
}
