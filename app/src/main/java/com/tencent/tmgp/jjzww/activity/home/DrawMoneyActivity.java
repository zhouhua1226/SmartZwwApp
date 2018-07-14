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
 * Created by yincong on 2018/3/16 10:43
 * 修改人：
 * 修改时间：
 * 类描述：提款
 */
public class DrawMoneyActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.drawmoney_bank_tv)
    TextView drawmoneyBankTv;
    @BindView(R.id.drawmoney_bankplace_tv)
    TextView drawmoneyBankplaceTv;
    @BindView(R.id.drawmoney_bankcardnum_tv)
    TextView drawmoneyBankcardnumTv;
    @BindView(R.id.drawmoney_username_tv)
    TextView drawmoneyUsernameTv;
    @BindView(R.id.drawmoney_num_et)
    EditText drawmoneyNumEt;
    @BindView(R.id.drawmoney_phone_et)
    EditText drawmoneyPhoneEt;
    @BindView(R.id.drawmoney_send_tv)
    TextView drawmoneySendTv;
    @BindView(R.id.drawmoney_smscode_et)
    EditText drawmoneySmscodeEt;
    @BindView(R.id.accountinfo_sure_btn)
    Button accountinfoSureBtn;
    @BindView(R.id.drawmoney_servicemobile_tv)
    TextView drawmoneyServicemobileTv;

    private String TAG="DrawMoneyActivity";
    private MyCountDownTimer myCountDownTimer;
    private String phone,smsCode,amount;
    private String userId="d7836bdf88df450bb7ce595430a542ae";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawmoney;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
        initDate();
        getUserAccBalCount(UserUtils.USER_ID);
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initDate(){
        drawmoneyBankTv.setText(UserUtils.BankBean.getBANK_NAME());
        drawmoneyBankplaceTv.setText(UserUtils.BankBean.getBANK_ADDRESS());
        drawmoneyBankcardnumTv.setText(UserUtils.BankBean.getBANK_CARD_NO());
        drawmoneyUsernameTv.setText(UserUtils.BankBean.getUSER_REA_NAME());

        drawmoneyPhoneEt.setText(UserUtils.UserPhone);
        drawmoneyNumEt.setHint("可提款金额"+UserUtils.UserAmount+"元");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_back, R.id.drawmoney_send_tv, R.id.accountinfo_sure_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.drawmoney_send_tv:
                phone=drawmoneyPhoneEt.getText().toString();
                if(Utils.isEmpty(phone)){
                    MyToast.getToast(getApplicationContext(),"请输入手机号！").show();
                }else {
                    if(Utils.checkPhoneREX(phone)){
                        myCountDownTimer.start();
                        getPhoneCode(UserUtils.USER_ID,phone,"4000");
                    }else {
                        MyToast.getToast(getApplicationContext(),"请输入正确的手机号！").show();
                    }
                }
                break;
            case R.id.accountinfo_sure_btn:
                amount=drawmoneyNumEt.getText().toString();
                smsCode=drawmoneySmscodeEt.getText().toString();
                phone=drawmoneyPhoneEt.getText().toString();
                if(smsCode.equals("")||amount.equals("")||phone.equals("")){
                    MyToast.getToast(getApplicationContext(),"请输入完整的信息！").show();
                }else {
                    double num= Double.parseDouble(amount);
                    double userAmount= Double.parseDouble(UserUtils.UserAmount);
                    if(num>0) {
                        if(userAmount>=num) {
                            doWithdrawCash(UserUtils.USER_ID, "4000", amount, smsCode);
                        }else {
                            MyToast.getToast(getApplicationContext(),"余额不足！").show();
                        }
                    }else {
                        MyToast.getToast(getApplicationContext(),"单次提款不得少于1元！").show();
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
            drawmoneySendTv.setText("倒计时(" + millisUntilFinished / 1000 + ")");
            drawmoneySendTv.setEnabled(false);
        }

        @Override
        public void onFinish() {
            drawmoneySendTv.setText("重新获取");
            drawmoneySendTv.setEnabled(true);
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

    private void doWithdrawCash(String userId,String smsType,String orderAmt,String phoneCode){
        HttpManager.getInstance().doWithdrawCash(userId, smsType, orderAmt, phoneCode, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> result) {
                if(result.getMsg().equals(Utils.HTTP_OK)){
                    UserUtils.UserAmount=result.getData().getAccBal();
                    MyToast.getToast(getApplicationContext(),"已申请,请耐心等待！").show();
                    finish();
                }else {
                    MyToast.getToast(getApplicationContext(),result.getMsg()).show();
                }
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(),"网络异常！").show();
            }
        });
    }

    private void getUserAccBalCount(String userId) {
        HttpManager.getInstance().getUserAccBalCount(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if (httpDataInfoResult.getMsg().equals(Utils.HTTP_OK)) {
                    String amount = httpDataInfoResult.getData().getAccBal();
                    UserUtils.UserAmount=amount;
                    //initDate();
                }
            }

            @Override
            public void _onError(Throwable e) {
                MyToast.getToast(getApplicationContext(), "网络异常！").show();
            }
        });
    }

}
