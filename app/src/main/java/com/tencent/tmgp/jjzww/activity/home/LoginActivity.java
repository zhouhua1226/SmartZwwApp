package com.tencent.tmgp.jjzww.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.ysdk.EasyYSDKApi;
import com.easy.ysdk.pay.PayReviewer;
import com.robust.sdk.api.CompatibleApi;
import com.robust.sdk.api.LoginCallback;
import com.robust.sdk.api.RobustApi;
import com.robust.sdk.avatar.AutoAccessCallback;
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
import com.tencent.tmgp.jjzww.view.GifView;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.ysdk.framework.common.ePlatform;
import org.json.JSONObject;


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
    @BindView(R.id.login_loading_gv)
    GifView loginLoadingGv;

    private String TAG = "LoginActivity--";
    private long mBackPressed;
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
        if (!(boolean) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_ISLOGOUT, false)) {
            setContentView(R.layout.activity_welcome);//闪屏
            if ((boolean) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_LOGIN, false)) {
                //用户已经注册
                uid = (String) SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_USERID, "");
                if (Utils.isEmpty(uid)) {
                    return;
                }
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    antuToken = (String) SPUtils.get(getApplicationContext(), YsdkUtils.AUTH_TOKEN, "");
                    getAccessToken(antuToken);    //获取token自动登录
                }
            } else {
                new Handler().postDelayed(initRunnable, 2000);
            }
        } else {
            initCreatView();
        }

    }

    private Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            initCreatView();
        }
    };

    private void initCreatView() {
        View MainView = getLayoutInflater().inflate(R.layout.activity_login, null);
        setContentView(MainView);
        initView();
        loginLoadingGv.setEnabled(false);
        loginLoadingGv.setMovieResource(R.raw.login_loadinggif);
    }

    @OnClick({R.id.login_qq_tv, R.id.login_wx_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_qq_tv:
                //MyToast.getToast(getApplicationContext(), "点击qq登录！").show();
                if (Utils.isNetworkAvailable(this)) {
                    loginLoadingGv.setVisibility(View.VISIBLE);
                    qqLogin();
                } else {
                    MyToast.getToast(getApplicationContext(), "请检查网络！").show();
                }
                break;
            case R.id.login_wx_tv:
                //MyToast.getToast(getApplicationContext(), "点击微信登录！").show();
                if (Utils.isNetworkAvailable(this)) {
                    loginLoadingGv.setVisibility(View.VISIBLE);
                    weixinLogin();
                } else {
                    MyToast.getToast(getApplicationContext(), "请检查网络！").show();
                }
                break;
            default:
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
        initParams.putString(RobustApi.InitParamsKey.CKEY, "y3WfBKF1FY4="); //测试环境ckey="rcWhucD6efT="  正式环境ckey="y3WfBKF1FY4="
        RobustApi.init(this, initParams);

        //分享初始化
//        FlamigoJApi.getInstance().setConfig(true);
//        FlamigoJApi.getInstance().init(this, FlamigoPlaform.DOMESTIC);
//        SecurityApi.getInstance().installation(this, "sqwoinjzdmhekzpzyvd7eqB6Vr_avatar");

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


    //判断是否登录
    private boolean isLogin() {
        return RobustApi.getInstance().isLogin();
    }

    class GameLoginCallback implements LoginCallback {
        @Override
        public void onCompelete(int code, JSONObject data) {
            Log.e(TAG, "YSDK登录回调=" + data);
            if (code == LoginCallback.SUCCESS) {
                //登录成功
                JSONObject obj = data.optJSONObject("data");
                YsdkUtils.uid = obj.optString("uid");  //用户唯一标识id，支付时会用到
                YsdkUtils.access_token = obj.optString("access_token");//用户通行token，支付登录时会用到
                YsdkUtils.auth_token = obj.optString("auth_token");//微信QQ标识token
                YsdkUtils.nickName = obj.optString("nick_name");//用户昵称
                //YsdkUtils.imageUrl="http://wx.qlogo.cn/mmopen/vi_32/ZQTY6hsXAECWXNic3416yKEfAuyHaWWcZ4rMAvw2DpHEEacG9g6bmXpPia5HraHdnn1P965JILptY02Sd7yUamDQ/132";
                YsdkUtils.imageUrl = obj.optString("image_url").replace("\"", "");//用户头像(只有微信或QQ登录，并且用户有头像时才会有效)
                Log.e(TAG, "uid=" + YsdkUtils.uid + "<<<<access_token=" + YsdkUtils.access_token);
                Log.e(TAG, "imageUrl=" + YsdkUtils.imageUrl);
                getYSDKLogin(YsdkUtils.uid, YsdkUtils.access_token, YsdkUtils.nickName, YsdkUtils.imageUrl);
            } else if (code == LoginCallback.FAIL) {
                //登录失败
                loginLoadingGv.setVisibility(View.GONE);
                MyToast.getToast(getApplicationContext(), "拉取第三方登录失败!").show();
            } else {
                //登录异常
                loginLoadingGv.setVisibility(View.GONE);
                MyToast.getToast(getApplicationContext(), "拉取第三方登录异常!").show();
            }
        }
    }


    private void getYSDKLogin(String uid, String accessToken, String nickName, String imageUrl) {
        HttpManager.getInstance().getYSDKLogin(uid, accessToken, nickName, imageUrl, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                if(loginInfoResult==null||loginInfoResult.getData()==null
                        ||loginInfoResult.getData().getAppUser()==null){
                    MyToast.getToast(getApplicationContext(), "登录失败！").show();
                    return;
                }
                if (loginInfoResult.getMsg().equals("success")) {
                    PayReviewer.reviewer();   //通知失败进行重发
                    //progressDialog.dismiss();
                    loginLoadingGv.setVisibility(View.GONE);
                    YsdkUtils.loginResult = loginInfoResult;
                    UserUtils.USER_ID = loginInfoResult.getData().getAppUser().getUSER_ID();
                    SPUtils.put(getApplicationContext(), YsdkUtils.AUTH_TOKEN, YsdkUtils.auth_token);
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_USERID, YsdkUtils.uid);
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_LOGIN, true);
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_ISLOGOUT, false);
                    MyToast.getToast(getApplicationContext(), "登录成功！").show();
                    UserUtils.isUserChanger = true;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    loginLoadingGv.setVisibility(View.GONE);
                    MyToast.getToast(getApplicationContext(), "登录失败!").show();
                }

            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(), "网络请求异常!").show();
            }
        });
    }

    private void getYSDKAuthLogin(String userId, String accessToken) {
        HttpManager.getInstance().getYSDKAuthLogin(userId, accessToken, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                if (loginInfoResult == null || loginInfoResult.getData() == null
                        || loginInfoResult.getData().getAppUser() == null) {
                    MyToast.getToast(getApplicationContext(), "登录失败！").show();
                    return;
                }
                if (loginInfoResult.getMsg().equals("success")) {
                    MyToast.getToast(getApplicationContext(), "自动登录成功！").show();
                    YsdkUtils.loginResult = loginInfoResult;
                    UserUtils.USER_ID = loginInfoResult.getData().getAppUser().getUSER_ID();
                    UserUtils.isUserChanger = true;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    public void getAccessToken(String authToken) {
        EasyYSDKApi.autoAccess(authToken, new AutoAccessCallback() {
            @Override
            public void onSuccess(String accessToken) {
                YsdkUtils.access_token = accessToken;
                getYSDKAuthLogin(uid, accessToken);  //自动登录
            }

            @Override
            public void onFail(int code, String message) {
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
