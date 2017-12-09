package com.tencent.tmgp.jjzww.activity.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/10.
 */
public class WeChatPayActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.amount_tv)
    EditText amountTv;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.wx_ye_tv)
    TextView wxYeTv;
    @BindView(R.id.wx_play_btn)
    Button wxPlayBtn;
    private String TAG = "WeChatPayActivity";
    private Intent intent;
    private String moneyzf;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechatpay;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        intent = getIntent();
        moneyzf = intent.getStringExtra("money");
        amountTv.setText(moneyzf);
        wxYeTv.setText(UserUtils.UserBalance);
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

    @OnClick({R.id.btn_back, R.id.btn_ok, R.id.wx_play_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_ok:
                moneyzf=amountTv.getText().toString();
                //MyToast.getToast(this, moneyzf + "元").show();
                wxPayMoney(UserUtils.UserPhone, moneyzf);
                break;
            case R.id.wx_play_btn:
//                getPlayNum(UserUtils.UserPhone, moneyzf);
                wxYeTv.setText(UserUtils.UserBalance);
                break;
        }
    }

    private void wxPayMoney(String phone, String money) {
        String phones = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
        HttpManager.getInstance().getUserPay(phones, money, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                Log.e(TAG, "充值结果=" + result.getMsg());
                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                MyToast.getToast(getApplicationContext(), moneyzf+"充值成功！").show();
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(), "充值失败！").show();
            }
        });
    }

//
//    private void getPlayNum(String phone, String number) {
//        String phones = Base64.encodeToString(phone.getBytes(), Base64.DEFAULT);
//        HttpManager.getInstance().getUserPlayNum(phones, number, new RequestSubscriber<Result<LoginInfo>>() {
//            @Override
//            public void _onSuccess(Result<LoginInfo> result) {
//                Log.e(TAG, "消费结果=" + result.getMsg());
//                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
//                MyToast.getToast(getApplicationContext(), "消费成功！").show();
//            }
//
//            @Override
//            public void _onError(Throwable e) {
//                MyToast.getToast(getApplicationContext(), "消费失败！").show();
//            }
//        });
//    }


}
