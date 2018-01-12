package com.tencent.tmgp.jjzww.activity.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.ysdk.EasyYSDKApi;
import com.easy.ysdk.share.ShareInfo;
import com.flamigo.jsdk.FlamigoPlaform;
import com.flamigo.jsdk.api.FlamigoJApi;
import com.proto.security.SecurityApi;
import com.robust.sdk.api.RobustApi;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.SPUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.utils.YsdkUtils;
import com.tencent.tmgp.jjzww.view.CatchDollResultDialog;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hongxiu on 2017/9/25.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.image_kf)
    ImageButton imageKf;
    @BindView(R.id.money_rl)
    RelativeLayout moneyRl;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    @BindView(R.id.invitation_rl)
    RelativeLayout invitationRl;
    @BindView(R.id.feedback_rl)
    RelativeLayout feedbackRl;
    @BindView(R.id.gywm_rl)
    RelativeLayout gywmRl;
    @BindView(R.id.bt_out)
    Button btOut;
    @BindView(R.id.vibrator_control_layout)
    RelativeLayout vibratorControlLayout;
    @BindView(R.id.vibrator_control_imag)
    ImageView vibratorControlImag;
    @BindView(R.id.setting_update_tv)
    TextView settingUpdateTv;
    @BindView(R.id.setting_update_layout)
    RelativeLayout settingUpdateLayout;
    @BindView(R.id.betrecord_rl)
    RelativeLayout betrecordRl;
    @BindView(R.id.setting_share_layout)
    RelativeLayout settingShareLayout;
    @BindView(R.id.roommusic_control_imag)
    ImageView roommusicControlImag;
    @BindView(R.id.roommusic_control_layout)
    RelativeLayout roommusicControlLayout;

    private String TAG = "SettingActivity";
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private Context context = SettingActivity.this;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        setIsVibrator();
        setIsOpenMusic();
        try {
            settingUpdateTv.setText("当前版本：" + Utils.getAppCodeOrName(this, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        initSDK();
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
        FlamigoJApi.getInstance().setConfig(true);
        FlamigoJApi.getInstance().init(this, FlamigoPlaform.DOMESTIC);
        SecurityApi.getInstance().installation(this, "sqwoinjzdmhekzpzyvd7eqB6Vr_avatar");


    }

    //判断是否登录
    private boolean isLogin() {
        return RobustApi.getInstance().isLogin();
    }

    @OnClick({R.id.image_back, R.id.image_kf, R.id.money_rl,
            R.id.record_rl, R.id.invitation_rl, R.id.feedback_rl,
            R.id.gywm_rl, R.id.bt_out, R.id.vibrator_control_layout,
            R.id.vibrator_control_imag, R.id.setting_update_layout,
            R.id.betrecord_rl, R.id.setting_share_layout,R.id.roommusic_control_layout,
            R.id.roommusic_control_imag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_kf:
                startActivity(new Intent(this,ServiceActivity.class));
                break;
            case R.id.money_rl:
                //我的游戏币
                startActivity(new Intent(this, GameCurrencyActivity.class));
                break;
            case R.id.record_rl:
                //我的主娃娃记录
                startActivity(new Intent(this, RecordActivity.class));
                break;
            case R.id.betrecord_rl:
                //我的投注记录
                startActivity(new Intent(this, BetRecordActivity.class));
                break;
            case R.id.invitation_rl:
                //邀请码
                startActivity(new Intent(this, LnvitationCodeActivity.class));
                break;
            case R.id.feedback_rl:
                //问题反馈
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.gywm_rl:
                //关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.bt_out:
                //getLogout(UserUtils.USER_ID);
                Toast.makeText(context, "退出登录", Toast.LENGTH_SHORT).show();
                loginOut();
                SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_ISLOGOUT, true);
                SPUtils.put(getApplicationContext(), YsdkUtils.AUTH_TOKEN, "");
                SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_USERID, "");
                SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_LOGIN, false);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.vibrator_control_layout:
            case R.id.vibrator_control_imag:
                Utils.isVibrator = !Utils.isVibrator;
                editor.putBoolean("isVibrator", Utils.isVibrator);
                editor.commit();
                setBtnText(vibratorControlImag, Utils.isVibrator);
                break;
            case R.id.roommusic_control_layout:
            case R.id.roommusic_control_imag:
                boolean isMusic=(boolean)SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_ISOPENMUSIC, true);
                if(isMusic){
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_ISOPENMUSIC, false);
                }else {
                    SPUtils.put(getApplicationContext(), UserUtils.SP_TAG_ISOPENMUSIC, true);
                }
                setIsOpenMusic();
                break;
            case R.id.setting_update_layout:
                MyToast.getToast(getApplicationContext(), "当前为最新版!").show();
//                UpdateDialog updateDialog=new UpdateDialog(this,R.style.easy_dialog_style);
//                updateDialog.setCancelable(false);
//                updateDialog.show();
//                updateDialog.setDialogResultListener(new UpdateDialog.DialogResultListener() {
//                    @Override
//                    public void getResult(int resultCode) {
//                        if (1 == resultCode) {// 确定
//                            MyToast.getToast(getApplicationContext(),"正在下载新版apk!").show();
//                        }else {
//
//                        }
//                    }
//                });

                //startActivity(new Intent(this, LoginActivity.class));
                //Utils.getGuessSuccessDialog(this);
                // Utils.getCatchResultDialog(this);
                break;
            case R.id.setting_share_layout:
                Log.e(TAG,"分享参数userId="+UserUtils.USER_ID);
                RobustApi.getInstance().shareWx(this, new ShareInfo(UserUtils.USER_ID));
                break;

        }
    }

    //退出登录
    private void loginOut() {
        EasyYSDKApi.logout();
    }

    private void setIsVibrator() {
        settings = getSharedPreferences("app_user", 0);
        editor = settings.edit();
        if (settings.contains("isVibrator")) {
            Utils.isVibrator = settings.getBoolean("isVibrator", true);
        }

        if (!Utils.isVibrator)
            vibratorControlImag.setSelected(false);
        else
            vibratorControlImag.setSelected(true);

    }

    private void setBtnText(ImageView btn, boolean isOpen) {
        if (isOpen)
            btn.setSelected(true);
        else
            btn.setSelected(false);
    }

    private void setIsOpenMusic(){
        boolean isOpen=(boolean)SPUtils.get(getApplicationContext(), UserUtils.SP_TAG_ISOPENMUSIC, true);
        if(isOpen){
            roommusicControlImag.setSelected(true);
        }else {
            roommusicControlImag.setSelected(false);
        }
    }

    private void getLogout(String userId) {
        HttpManager.getInstance().getLogout(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG, "退出登录结果=" + loginInfoResult.getMsg());
                if (loginInfoResult.getMsg().equals("success")) {
                    Toast.makeText(context, "退出登录", Toast.LENGTH_SHORT).show();
                    SPUtils.remove(context, UserUtils.SP_TAG_LOGIN);
                    UserUtils.UserPhone = "";
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isLogin()) {
            EasyYSDKApi.onResume(this);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(!isLogin()) {
            EasyYSDKApi.onPause(this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(!isLogin()) {
            EasyYSDKApi.onStop(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isLogin()) {
            EasyYSDKApi.onDestroy(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!isLogin()) {
            EasyYSDKApi.onRestart(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(!isLogin()) {
            EasyYSDKApi.handleIntent(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!isLogin()) {
            EasyYSDKApi.onActivityResult(requestCode, resultCode, data);
        }
    }


}
