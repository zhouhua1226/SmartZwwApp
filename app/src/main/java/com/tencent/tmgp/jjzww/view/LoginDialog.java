package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.utils.Utils;

/**
 * Created by hongxiu on 2017/9/21.
 */
public class LoginDialog extends Dialog implements View.OnClickListener {

    private ImageView login_im;//取消
    private Button bt_code;//验证码
    private Button bt_login;//登录
    private EditText et_phone;//手机号
    private EditText et_code;//验证码
    private IdialogClick idialogClick;
    private MyCountDownTimer myCountDownTimer;


    private final static String TAG = "LoginDialog";

    public LoginDialog(Context context) {
        super(context);
    }

    public LoginDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoginDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setDialogClickListener(IdialogClick idialogClick) {
        this.idialogClick = idialogClick;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        findView();
        setListner();
        myCountDownTimer =new MyCountDownTimer(60000,1000);
    }

    public void findView() {
        login_im = (ImageView) findViewById(R.id.login_im);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_login = (Button) findViewById(R.id.bt_login);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);

    }

    /**
     * 绑定监听
     */
    private void setListner() {
        login_im.setOnClickListener(this);
        bt_code.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }


    public void setBt_code(String content){
        bt_code.setText(content);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_im:
                this.dismiss();
                idialogClick.dialogMiss();
                break;

            case R.id.bt_code:
                idialogClick.getAuthCode(et_phone.getText().toString());
                myCountDownTimer.start();
                break;

            case R.id.bt_login:
                if (checkEdit(et_phone, false) && checkEdit(et_code, false)) {
                    idialogClick.login(et_phone.getText().toString(), et_code.getText().toString());
                } else {
                    if (!checkEdit(et_phone, false)) {
                        et_phone.requestFocus();
                        return;
                    }
                    if (!checkEdit(et_code, false)) {
                        et_code.requestFocus();
                        return;
                    }
                }
                this.dismiss();
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

            bt_code.setText("倒计时("+millisUntilFinished/1000+")");
            bt_code.setEnabled(false);

        }

        @Override
        public void onFinish() {
            bt_code.setText("重新获取");
            bt_code.setEnabled(true);
        }
    }

    /**
     * 检查输入情况
     *
     * @param v        指定的控件
     * @param hasFocus true获得焦点，false失去焦点
     */
    private boolean checkEdit(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_phone:
                if (!hasFocus) {
                    String str = ((EditText) v).getText().toString();
                    if (null == str || str.equals("")) {
                        ((EditText) v).setError("手机号不能为空");

                    } else if (!Utils.checkPhoneREX(str)) {
                        ((EditText) v).setError("手机号码不符合规则");

                    } else {
                        ((EditText) v).setCompoundDrawablesWithIntrinsicBounds(0,
                                0, 0, 0);
                        return true;
                    }
                }
                break;

            case R.id.et_code:
                if (!hasFocus) {
                    String str = ((EditText) v).getText().toString();
                    if (null == str || str.equals("")) {
                        ((EditText) v).setError("验证码不能为空");

                    } else {
                        ((EditText) v).setCompoundDrawablesWithIntrinsicBounds(0,
                                0, 0, 0);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public interface IdialogClick{
        void getAuthCode(String phone);
        void login(String phone, String code);
        void dialogMiss();
    }
}
