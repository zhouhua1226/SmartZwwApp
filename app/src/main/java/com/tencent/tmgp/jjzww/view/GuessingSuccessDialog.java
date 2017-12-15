package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by yincong on 2017/12/13 16:14
 * 修改人：
 * 修改时间：
 * 类描述：竞彩成功
 */
public class GuessingSuccessDialog extends Dialog implements View.OnClickListener{

    private final static String TAG = "GuessingSuccessDialog";
    private RelativeLayout layout;

    public GuessingSuccessDialog(Context context) {
        super(context);
    }

    public GuessingSuccessDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public GuessingSuccessDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guessing);
        findView();
        setListner();
    }


    public void findView() {
        layout= (RelativeLayout) findViewById(R.id.guess_success_layout);
    }

    /**
     * 绑定监听
     **/
    private void setListner() {
        layout.setOnClickListener(this);
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guess_success_layout:
                if (null != this.listener) {
                    listener.getResult(0);
                }
                GuessingSuccessDialog.this.dismiss();
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
