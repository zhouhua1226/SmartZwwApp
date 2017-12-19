package com.tencent.tmgp.jjzww.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.ysdk.EasyYSDKApi;
import com.easy.ysdk.pay.NotifyListener;
import com.easy.ysdk.pay.PayReviewer;
import com.easy.ysdk.share.ShareInfo;
import com.flamigo.jsdk.FlamigoPlaform;
import com.flamigo.jsdk.api.FlamigoJApi;
import com.proto.security.SecurityApi;
import com.robust.sdk.api.CompatibleApi;
import com.robust.sdk.api.LoginCallback;
import com.robust.sdk.api.PayCallback;
import com.robust.sdk.api.RobustApi;
import com.robust.sdk.avatar.AutoAccessCallback;
import com.robust.sdk.data.PayKey;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.base.MyApplication;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.SPUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.ProgressDialogs;
import com.tencent.ysdk.framework.common.ePlatform;

import org.json.JSONObject;

import java.util.UUID;

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
    @BindView(R.id.login_fx_tv)
    TextView loginFxTv;
    @BindView(R.id.login_zf_tv)
    TextView loginZfTv;
    @BindView(R.id.login_logout_tv)
    TextView loginLogoutTv;

    private String TAG = "LoginActivity--";
    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;
    private ProgressDialogs progressDialog;
    private String antuToken;
    private String uid;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initSDK();
        initWelcome();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }


    private void initWelcome() {
        if(!(boolean) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_ISLOGOUT, false)) {
            setContentView(R.layout.activity_welcome);//闪屏
            if ((boolean) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_LOGIN, false)) {
                //用户已经注册
                uid = (String) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_USERID, "");
                if (Utils.isEmpty(uid)) {
                    return;
                }
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    antuToken = (String) SPUtils.get(getApplicationContext(), YsdkUtils.AUTH_TOKEN, "");
                    Log.e(TAG, "uid=" + uid + "<<<<<authToken=" + antuToken);
                    Log.e(TAG, "新token=" + antuToken);
                    getAccessToken(antuToken);    //获取token自动登录
                }
            } else {
                new Handler().postDelayed(initRunnable, 2000);
            }
        }else {
            initCreatView();
        }

    }

    private Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            initCreatView();
        }
    };

    private void initCreatView(){
        View MainView = getLayoutInflater().inflate(R.layout.activity_login, null);
        setContentView(MainView);
        initView();
    }

    @OnClick({R.id.login_qq_tv, R.id.login_wx_tv, R.id.login_fx_tv,
                R.id.login_zf_tv,R.id.login_logout_tv,R.id.login_auth_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_qq_tv:
                //MyToast.getToast(getApplicationContext(), "点击qq登录！").show();
                if(Utils.isNetworkAvailable(this)) {
                    progressDialog = new ProgressDialogs(LoginActivity.this, R.style.easy_dialog_style);
                    progressDialog.show();
                    qqLogin();
                }else {
                    MyToast.getToast(getApplicationContext(),"请检查网络！").show();
                }
                break;
            case R.id.login_wx_tv:
                //MyToast.getToast(getApplicationContext(), "点击微信登录！").show();
                if(Utils.isNetworkAvailable(this)) {
                    progressDialog = new ProgressDialogs(LoginActivity.this, R.style.easy_dialog_style);
                    progressDialog.show();
                    weixinLogin();
                }else {
                    MyToast.getToast(getApplicationContext(),"请检查网络！").show();
                }
                break;
            case R.id.login_fx_tv:
                share();
                break;
            case R.id.login_zf_tv:
                pay();
                break;
            case R.id.login_logout_tv:
                loginOut();
                break;
            case R.id.login_auth_tv:

                break;
        }
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



    }

    private void qqLogin() {
        CompatibleApi.setEplatform(ePlatform.QQ);
        RobustApi.getInstance().setLoginCallback(new GameLoginCallback());
        RobustApi.getInstance().startLogin();
    }

    private void weixinLogin() {
        CompatibleApi.setEplatform(ePlatform.WX);
        RobustApi.getInstance().setLoginCallback(new GameLoginCallback());
        RobustApi.getInstance().startLogin();
    }

    private void share() {
        RobustApi.getInstance().shareWx(this, new ShareInfo(YsdkUtils.uid));
    }

    private void pay() {
        //开始封装支付参数
        Bundle payInfo = new Bundle();
        payInfo.putString(PayKey.USER_ID, YsdkUtils.uid);//用户id，登录接口中返回给游戏的uid
        payInfo.putInt(PayKey.AMOUNT, 100);//支付金额，单位 : 分 ,最小单位10
        //TODO 此交易号生成仅为测试，接入方应定义自己的 外部交易号规则，保证唯一 ↓↓↓
        payInfo.putString(PayKey.OUT_TRADE_NO, UUID.randomUUID().toString().replaceAll("-", ""));//调用方生成的交易号，作为查询订单的唯一依据，必须唯一(服务端回调会透传),最大长度32位
        payInfo.putString(PayKey.SUBJECT, "测试商品");//订单主题
        payInfo.putString(PayKey.EXTRA, "透传参数");//附加透传参数，服务端回调会完整透传.没有可不传

        payInfo.putString(PayKey.ZONEID, "1");  //账户分区ID_角色ID。每个应用都有一个分区ID为1的默认分区，分区可以在cpay.qq.com/mpay上自助配置。如果应用选择支持角色，则角色ID接在分区ID号后用"_"连接，角色ID需要进行urlencode。
        payInfo.putInt(PayKey.PAY_ICON_RESID, R.drawable.app_jj_icon);  //支付时显示的icon
        payInfo.putString(PayKey.ACCESS_TOKEN, YsdkUtils.access_token);  //登录后获取到的用户访问token
        //调用支付接口
        RobustApi.getInstance().startPay(payInfo, new GamePayCallback());

    }

    //判断是否登录
    private boolean isLogin() {
        return RobustApi.getInstance().isLogin();
    }

    //退出登录
    private void loginOut() {
        EasyYSDKApi.logout();
    }

    class GameLoginCallback implements LoginCallback {
        @Override
        public void onCompelete(int code, JSONObject data) {
            Log.e(TAG, "QQWX登录=" + data);
            if (code == LoginCallback.SUCCESS) {
                //登录成功
                JSONObject obj = data.optJSONObject("data");
                YsdkUtils.uid = obj.optString("uid");  //用户唯一标识id，支付时会用到
                YsdkUtils.access_token = obj.optString("access_token");//用户通行token，支付登录时会用到
                YsdkUtils.auth_token = obj.optString("auth_token");//微信QQ标识token
                YsdkUtils.nickName = obj.optString("nick_name");//用户昵称
                //YsdkUtils.imageUrl="http://wx.qlogo.cn/mmopen/vi_32/ZQTY6hsXAECWXNic3416yKEfAuyHaWWcZ4rMAvw2DpHEEacG9g6bmXpPia5HraHdnn1P965JILptY02Sd7yUamDQ/132";
                YsdkUtils.imageUrl = obj.optString("image_url").replace("\"","");//用户头像(只有微信或QQ登录，并且用户有头像时才会有效)
                Log.e(TAG, "uid=" + YsdkUtils.uid + "<<<<access_token=" + YsdkUtils.access_token);
                Log.e(TAG, "imageUrl=" + YsdkUtils.imageUrl);
                getYSDKLogin(YsdkUtils.uid, YsdkUtils.access_token, YsdkUtils.nickName, YsdkUtils.imageUrl);
            } else if (code == LoginCallback.FAIL) {
                //登录失败
                if(progressDialog!=null)
                progressDialog.dismiss();
                MyToast.getToast(getApplicationContext(),"拉取第三方登录失败!").show();
            } else {
                //登录异常
                if(progressDialog!=null)
                progressDialog.dismiss();
                MyToast.getToast(getApplicationContext(),"拉取第三方登录异常!").show();
            }
        }
    }

    class GamePayCallback implements PayCallback {
        @Override
        public void onCompelete(int code, JSONObject data) {
            Toast.makeText(LoginActivity.this, "LoginActivity:" + code + ":" + data.toString(), Toast.LENGTH_SHORT).show();
            switch (code) {
                case PayCallback.FAIL:
                    Toast.makeText(getBaseContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    break;

                case PayCallback.SUCCESS:
                    Toast.makeText(getBaseContext(), "支付成功！", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    //erroInfo
                    Toast.makeText(LoginActivity.this, "erro:" + data == null ? "" : data.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    }

    private void getYSDKLogin(String uid, String accessToken, String nickName, String imageUrl) {
        HttpManager.getInstance().getYSDKLogin(uid, accessToken, nickName, imageUrl, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG, "wxlogin=" + loginInfoResult.getMsg());
                if(loginInfoResult.getMsg().equals("success")) {
                    PayReviewer.reviewer();   //通知失败进行重发
                    progressDialog.dismiss();
                    YsdkUtils.loginResult=loginInfoResult;
                    UserUtils.USER_ID=loginInfoResult.getData().getAppUser().getUSER_ID();
                    Log.e("<<<<<<<<<<<<","登录userId="+UserUtils.USER_ID);
                    SPUtils.put(getApplicationContext(), YsdkUtils.AUTH_TOKEN, YsdkUtils.auth_token);
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_USERID, YsdkUtils.uid);
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_LOGIN, true);
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_ISLOGOUT,false);
                    MyToast.getToast(getApplicationContext(), "登录成功！").show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("loginback",loginInfoResult);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else {
                    if(progressDialog!=null)
                    progressDialog.dismiss();
                    MyToast.getToast(getApplicationContext(),"APP后台返回失败!").show();
                }

            }

            @Override
            public void _onError(Throwable e) {
                if(progressDialog!=null)
                progressDialog.dismiss();
                MyToast.getToast(getApplicationContext(),"网络请求异常!").show();
            }
        });
    }

    private void getYSDKAuthLogin(String userId,String accessToken){
        HttpManager.getInstance().getYSDKAuthLogin(userId, accessToken, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG, "wxauthlogin=" + loginInfoResult.getMsg());
                if(loginInfoResult.getMsg().equals("success")) {
                    //PayReviewer.reviewer();   //通知失败进行重发
                    MyToast.getToast(getApplicationContext(), "自动登录成功！").show();
                    YsdkUtils.loginResult=loginInfoResult;
                    UserUtils.USER_ID=loginInfoResult.getData().getAppUser().getUSER_ID();
                    Log.e("<<<<<<<<<<<<","自动登录userId="+UserUtils.USER_ID);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("loginback",loginInfoResult);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    public  void getAccessToken(String authToken) {
        EasyYSDKApi.autoAccess(authToken, new AutoAccessCallback() {
            @Override
            public void onSuccess(String accessToken) {

                YsdkUtils.access_token=accessToken;
                Log.e("YsdkUtils--", "获取AccessToken="+YsdkUtils.access_token);
                getYSDKAuthLogin(uid,accessToken);  //自动登录
            }
            @Override
            public void onFail(int code,String message) {
                Log.e("YsdkUtils--", "获取AccessToken失败原因="+message);
                MyToast.getToast(LoginActivity.this, "登录过期，请重新登录！").show();
                initCreatView();
            }
        });

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

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mBackPressed) > 2000) {  //这里3000，表示两次点击的间隔时间
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exit();
        }
    }
}
