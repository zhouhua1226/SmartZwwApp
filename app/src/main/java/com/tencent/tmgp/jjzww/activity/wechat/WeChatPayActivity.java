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
import android.widget.Toast;

import com.easy.ysdk.EasyYSDKApi;
import com.easy.ysdk.pay.NotifyListener;
import com.easy.ysdk.pay.PayReviewer;
import com.flamigo.jsdk.FlamigoPlaform;
import com.flamigo.jsdk.api.FlamigoJApi;
import com.proto.security.SecurityApi;
import com.robust.sdk.api.PayCallback;
import com.robust.sdk.api.RobustApi;
import com.robust.sdk.data.PayKey;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.MyToast;

import org.json.JSONObject;

import java.util.UUID;

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
    private String TAG = "WeChatPayActivity--";
    private Intent intent;
    private String moneyzf;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechatpay;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initSDK();
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

        //分享初始化
        FlamigoJApi.getInstance().setConfig(true);
        FlamigoJApi.getInstance().init(this, FlamigoPlaform.DOMESTIC);
        SecurityApi.getInstance().installation(this, "sqwoinjzdmhekzpzyvd7eqB6Vr_avatar");
        //YSDK支付通知失败监听回调
        EasyYSDKApi.setNotifyListener(new NotifyListener() {
            @Override
            public void onResult(int code, String msg) {
                Log.e(TAG,"支付通知失败原因="+msg);
            }
        });
        PayReviewer.reviewer();   //通知失败进行重发

    }

    private void pay(String userId,String accessToken,int amount,String order) {
        //开始封装支付参数
        Bundle payInfo = new Bundle();
        payInfo.putString(PayKey.USER_ID, userId);//用户id，登录接口中返回给游戏的uid
        payInfo.putInt(PayKey.AMOUNT, amount);//支付金额，单位 : 分 ,最小单位10
        //TODO 此交易号生成仅为测试，接入方应定义自己的 外部交易号规则，保证唯一 ↓↓↓
        payInfo.putString(PayKey.OUT_TRADE_NO, order);
        //payInfo.putString(PayKey.OUT_TRADE_NO, UUID.randomUUID().toString().replaceAll("-", ""));//调用方生成的交易号，作为查询订单的唯一依据，必须唯一(服务端回调会透传),最大长度32位
        payInfo.putString(PayKey.SUBJECT, "街机抓娃娃支付测试");//订单主题
        payInfo.putString(PayKey.EXTRA, "透传参数");//附加透传参数，服务端回调会完整透传.没有可不传

        payInfo.putString(PayKey.ZONEID, "1");  //账户分区ID_角色ID。每个应用都有一个分区ID为1的默认分区，分区可以在cpay.qq.com/mpay上自助配置。如果应用选择支持角色，则角色ID接在分区ID号后用"_"连接，角色ID需要进行urlencode。
        payInfo.putInt(PayKey.PAY_ICON_RESID, R.drawable.app_jj_icon);  //支付时显示的icon
        payInfo.putString(PayKey.ACCESS_TOKEN, accessToken);  //登录后获取到的用户访问token
        //调用支付接口
        RobustApi.getInstance().startPay(payInfo, new GamePayCallback());

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
                //wxPayMoney(UserUtils.UserPhone, moneyzf);
                Log.e(TAG,"获取订单参数userId="+UserUtils.USER_ID+"accessToken="+YsdkUtils.access_token+"金额="+moneyzf);
                getYSDKPay(UserUtils.USER_ID,YsdkUtils.access_token,moneyzf);
                break;
            case R.id.wx_play_btn:
//                getPlayNum(UserUtils.UserPhone, moneyzf);
                wxYeTv.setText(UserUtils.UserBalance);
                break;
        }
    }

    private void getUserDate(String userId) {
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> result) {
                Log.e(TAG, "充值后获取余额结果=" + result.getMsg()+result.getData().getAppUser().getBALANCE());
                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
                //MyToast.getToast(getApplicationContext(), moneyzf+"充值成功！").show();
            }

            @Override
            public void _onError(Throwable e) {
            }
        });
    }

    private void getYSDKPay(String userId,String accessToken,String amount){
        HttpManager.getInstance().getYSDKPay(userId, accessToken, amount, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG,"订单生成结果="+loginInfoResult.getMsg());
                String uid=loginInfoResult.getData().getOrder().getUSER_ID();
                String order=loginInfoResult.getData().getOrder().getORDER_ID();
                int amount= Integer.parseInt(loginInfoResult.getData().getOrder().getREGAMOUNT());
                pay(UserUtils.USER_ID,YsdkUtils.access_token,amount,order);
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    class GamePayCallback implements PayCallback {
        @Override
        public void onCompelete(int code, JSONObject data) {
            Toast.makeText(WeChatPayActivity.this, TAG + code + ":" + data.toString(), Toast.LENGTH_SHORT).show();
            switch (code) {
                case PayCallback.FAIL:
                    Log.e(TAG,"米大师支付结果="+"支付失败");
                    Toast.makeText(getBaseContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    break;

                case PayCallback.SUCCESS:
                    Log.e(TAG,"米大师支付结果="+"支付成功");
                    Toast.makeText(getBaseContext(), "支付成功！", Toast.LENGTH_SHORT).show();
                    //getUserDate(UserUtils.USER_ID);
                    break;

                default:
                    //erroInfo
                    Toast.makeText(WeChatPayActivity.this, "erro:" + data == null ? "" : data.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyYSDKApi.onResume(this);
    }


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
