package com.tencent.tmgp.jjzww.activity.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2018/3/16 10:36
 * 修改人：
 * 修改时间：
 * 类描述：绑定手机号类
 */
public class SettingMobileActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.settingmobile_phone_et)
    EditText settingmobilePhoneEt;
    @BindView(R.id.settingmobile_smscode_et)
    EditText settingmobileSmscodeEt;
    @BindView(R.id.settingmobile_smscode_tv)
    TextView settingmobileSmscodeTv;
    @BindView(R.id.settingmobile_submit_btn)
    Button settingmobileSubmitBtn;

    private String TAG="SettingMobile--";
    private MyCountDownTimer myCountDownTimer;
    private String phone;
    private String smsCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settingmobile;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        myCountDownTimer =new MyCountDownTimer(60000,1000);
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

    @OnClick({R.id.image_back, R.id.settingmobile_smscode_tv, R.id.settingmobile_submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.settingmobile_smscode_tv:
                phone=settingmobilePhoneEt.getText().toString();
                if(Utils.isEmpty(phone)){
                    MyToast.getToast(getApplicationContext(),"请输入手机号！").show();
                }else {
                    if(Utils.checkPhoneREX(phone)){
                        myCountDownTimer.start();
                        getPhoneCode(UserUtils.USER_ID,phone,"2000");
                    }else {
                        MyToast.getToast(getApplicationContext(),"请输入正确的手机号！").show();
                    }
                }
                break;
            case R.id.settingmobile_submit_btn:
                phone =settingmobilePhoneEt.getText().toString();
                smsCode=settingmobileSmscodeEt.getText().toString();
                if(Utils.isEmpty(phone)||Utils.isEmpty(smsCode)){
                    MyToast.getToast(getApplicationContext(),"请输入手机号和验证码！").show();
                }else {
                    if(Utils.checkPhoneREX(phone)){
                        getEditUserPhone(UserUtils.USER_ID,phone,smsCode);
                    }else {
                        MyToast.getToast(getApplicationContext(),"请输入正确的手机号！").show();
                    }
                }
                break;
            default:
                break;
        }
    }

    //倒计时
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            settingmobileSmscodeTv.setText("倒计时("+millisUntilFinished/1000+")");
            settingmobileSmscodeTv.setEnabled(false);
        }

        @Override
        public void onFinish() {
            settingmobileSmscodeTv.setText("重新获取");
            settingmobileSmscodeTv.setEnabled(true);
        }
    }



    private void getPhoneCode(String userId,String phoneNumber,String smsType){
        if(Utils.isEmpty(userId)||Utils.isEmpty(phoneNumber)){
            return;
        }
        HttpManager.getInstance().getPhoneCode(userId, phoneNumber,smsType, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> result) {
                if(result.getMsg().equals(Utils.HTTP_OK)){
                    MyToast.getToast(getApplicationContext(),"已发送！").show();
                }
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(),"网络异常！").show();
            }
        });
    }

    private void getEditUserPhone(String userId,String phoneNumber, String phoneCode){
        HttpManager.getInstance().getEditUserPhone(userId, phoneNumber, phoneCode, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> result) {
                if(result.getMsg().equals(Utils.HTTP_OK)){
                    MyToast.getToast(getApplicationContext(),"绑定成功！").show();
                    UserUtils.UserPhone=phone;
                    finish();
                }
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(),"网络异常！").show();
            }
        });
    }

}
