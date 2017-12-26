package com.tencent.tmgp.jjzww.activity.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.tencent.tmgp.jjzww.adapter.WeChatPayAdapter;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.PayCardBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.GifView;
import com.tencent.tmgp.jjzww.view.SpaceItemDecoration;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/10/10.
 */
public class WeChatPayActivity extends BaseActivity {
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.wechatpay_gif_view)
    GifView wechatpayGifView;
    @BindView(R.id.wechatpay_recyclerview)
    RecyclerView wechatpayRecyclerview;
    private String TAG = "WeChatPayActivity--";
    private WeChatPayAdapter weChatPayAdapter;
    private List<PayCardBean> mylist;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechatpay;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initSDK();
        initData();
        getPayCardList();

    }

    private void initData() {
        mylist = new ArrayList<PayCardBean>();
        weChatPayAdapter = new WeChatPayAdapter(this, mylist);
        wechatpayRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        wechatpayRecyclerview.addItemDecoration(new SpaceItemDecoration(15));
        wechatpayRecyclerview.setAdapter(weChatPayAdapter);
        weChatPayAdapter.setmOnItemClickListener(new WeChatPayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                wechatpayGifView.setVisibility(View.VISIBLE);
                int money= Integer.parseInt(mylist.get(position).getAMOUNT())*100;
                getYSDKPay(UserUtils.USER_ID, YsdkUtils.access_token, String.valueOf(money));
            }
        });
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        wechatpayGifView.setEnabled(false);
        wechatpayGifView.setMovieResource(R.raw.waitloadinggif);
        wechatpayGifView.setVisibility(View.VISIBLE);
    }

    //初始化sdk
    private void initSDK() {
        //ysdk必须要初始化
        EasyYSDKApi.onCreate(this);
        EasyYSDKApi.handleIntent(this.getIntent());
        EasyYSDKApi.setUserListener();
        EasyYSDKApi.setBuglyListener();
        EasyYSDKApi.registPayActivity(this);

        //add hx_ysdk  初始化
        Bundle initParams = new Bundle();
        initParams.putString(RobustApi.InitParamsKey.CKEY, "y3WfBKF1FY4="); //测试环境ckey="rcWhucD6efT="  正式环境ckey="y3WfBKF1FY4="
        RobustApi.init(this, initParams);

        //分享初始化
        FlamigoJApi.getInstance().setConfig(true);
        FlamigoJApi.getInstance().init(this, FlamigoPlaform.DOMESTIC);
        SecurityApi.getInstance().installation(this, "sqwoinjzdmhekzpzyvd7eqB6Vr_avatar");
        //YSDK支付通知失败监听回调
        EasyYSDKApi.setNotifyListener(new NotifyListener() {
            @Override
            public void onResult(int code, String msg) {
                Log.e(TAG, "支付通知失败原因=" + msg);
            }
        });
        PayReviewer.reviewer();   //通知失败进行重发
    }

    private void pay(String userId, String accessToken, int amount, String order) {
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

    private void getYSDKPay(String userId, String accessToken, String amount) {
        HttpManager.getInstance().getYSDKPay(userId, accessToken, amount, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG, "订单生成结果=" + loginInfoResult.getMsg());
                String uid = loginInfoResult.getData().getOrder().getUSER_ID();
                String order = loginInfoResult.getData().getOrder().getORDER_ID();
                int amount = Integer.parseInt(loginInfoResult.getData().getOrder().getREGAMOUNT());
                wechatpayGifView.setVisibility(View.GONE);
                pay(UserUtils.USER_ID, YsdkUtils.access_token, amount, order);
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    //获取充值卡列表
    public void getPayCardList(){
        HttpManager.getInstance().getPayCardList(new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG, "充值卡列表获取结果=" + loginInfoResult.getMsg());
                if(loginInfoResult.getMsg().equals("success")){
                    wechatpayGifView.setVisibility(View.GONE);
                    mylist=loginInfoResult.getData().getPaycard();
                    Log.e(TAG, "充值卡列表获取结果=" + mylist.size());
                    weChatPayAdapter.notify(mylist);
                }else {
                    wechatpayGifView.setVisibility(View.GONE);
                }
            }

            @Override
            public void _onError(Throwable e) {
                wechatpayGifView.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    class GamePayCallback implements PayCallback {
        @Override
        public void onCompelete(int code, JSONObject data) {
            //Toast.makeText(WeChatPayActivity.this, TAG + code + ":" + data.toString(), Toast.LENGTH_SHORT).show();
            switch (code) {
                case PayCallback.FAIL:
                    Log.e(TAG, "米大师支付结果=" + "支付失败");
                    Toast.makeText(getBaseContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    break;

                case PayCallback.SUCCESS:
                    Log.e(TAG, "米大师支付结果=" + "支付成功");
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
        EasyYSDKApi.unRegistPayActivity();
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
