package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easy.ysdk.EasyYSDKApi;
import com.easy.ysdk.pay.NotifyListener;
import com.easy.ysdk.pay.PayReviewer;
import com.flamigo.jsdk.FlamigoPlaform;
import com.flamigo.jsdk.api.FlamigoJApi;
import com.proto.security.SecurityApi;
import com.robust.sdk.api.RobustApi;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
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
    private static String TAG="SelectRechargeTypeActiivty";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_selectrechargetype;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initSDK();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyYSDKApi.onResume(this);
        getUserDate(UserUtils.USER_ID);

    }

    private void getUserNameAndBalance(){
        String userId=UserUtils.USER_ID;
        String userBalance=UserUtils.UserBalance;
        if(!Utils.isEmpty(userId)&&!Utils.isEmpty(userBalance)){
            selectAccountTv.setText(UserUtils.NickName);
            selectMoneyTv.setText(userBalance);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //初始化sdk
    private void initSDK() {
        //ysdk必须要初始化
        EasyYSDKApi.onCreate(this);
        EasyYSDKApi.handleIntent(this.getIntent());
        EasyYSDKApi.setUserListener();
        EasyYSDKApi.setBuglyListener();

        //add hx_ysdk  初始化
        Bundle initParams = new Bundle();
        initParams.putString(RobustApi.InitParamsKey.CKEY, "rcWhucD6efT="); //"L0VRoX/sAWg="
        RobustApi.init(this, initParams);

        //YSDK支付通知失败监听回调
        EasyYSDKApi.setNotifyListener(new NotifyListener() {
            @Override
            public void onResult(int code, String msg) {
                Log.e(TAG,"支付通知失败原因="+msg);
            }
        });

        PayReviewer.reviewer();   //通知失败进行重发
    }

    @OnClick({R.id.image_back, R.id.image_service, R.id.layout_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_service:
                startActivity(new Intent(this,ServiceActivity.class));
                break;
            case R.id.layout_wechat:
                getMoney();

                break;
        }
    }

    private void getUserDate(String userId) {
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                Log.e(TAG, "充值后获取余额结果=" + result.getMsg()+"余额="+result.getData().getAppUser().getBALANCE());
                getUserNameAndBalance();
                //MyToast.getToast(getApplicationContext(), moneyzf+"充值成功！").show();
            }

            @Override
            public void _onError(Throwable e) {
            }
        });
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


    @Override
    protected void onPause() {
        super.onPause();
        EasyYSDKApi.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyYSDKApi.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyYSDKApi.onDestroy(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EasyYSDKApi.onRestart(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        EasyYSDKApi.handleIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyYSDKApi.onActivityResult(requestCode, resultCode, data);
    }


}
