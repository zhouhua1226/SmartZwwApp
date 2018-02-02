package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.utils.Utils;

/**
 * Created by yincong on 2018/1/5 18:26
 * 修改人：
 * 修改时间：
 * 类描述：签到成功弹窗
 */
public class SignSuccessDialog extends Dialog implements View.OnClickListener{

    private ImageView sure_imag;
    private TextView textView;

    public SignSuccessDialog(Context context) {
        super(context);
    }

    public SignSuccessDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SignSuccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_success_dialog);
        findView();
        setListner();
    }


    public void findView() {
        textView= (TextView) findViewById(R.id.sign_success_dialog_tv);
        sure_imag= (ImageView) findViewById(R.id.sign_success_dialog_imag);
    }

    public void setTextView(String num){
        if(!Utils.isEmpty(num)) {
            textView.setText("恭喜您获得" + num + "金币!");
        }else {
            textView.setText("恭喜您获得签到金币!");
        }
    }

    /**
     * 绑定监听
     **/
    private void setListner() {
        sure_imag.setOnClickListener(this);
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_success_dialog_imag:
                if (null != this.listener) {
                    listener.getResult(0);
                }
                SignSuccessDialog.this.dismiss();
                break;

        }
    }

    private DialogResultListener listener;

    public void setDialogResultListener(DialogResultListener listener) {
        this.listener = listener;
    }

    public interface DialogResultListener {
        /**
         * 获取结果的方法
         *
         * @param resultCode 0.取消
         */
        void getResult(int resultCode);
    }


}
