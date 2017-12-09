package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by yincong on 2017/11/30 19:44
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class UpdateDialog extends Dialog implements View.OnClickListener{

    private final static String TAG = "ConfirmDialog";

    private Context context;
    private TextView tv_title;//提示
    private TextView dl_tv_content;//内容
    private Button dl_btn_cancel;//取消
    private Button dl_btn_confirm;//确定

    public UpdateDialog(Context context) {
        super(context);
    }

    public UpdateDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog);
        findView();
        setListner();
    }


    /**
     * 设置确认按钮的文字
     */
    public void setDialogConfirmText(String confirmText) {
        dl_btn_confirm.setText(confirmText);
    }

    /**
     * 设置取消按钮的文字
     */
    public void setDialogCancelText(String cancelText) {
        dl_btn_cancel.setText(cancelText);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setDialogTitle(String title) {
        tv_title.setText(title);
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setDialogContent(String content) {
        Log.i(TAG, content);
        Log.i(TAG, dl_tv_content + "");
        dl_tv_content.setText(content);
    }

    public void findView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        dl_btn_cancel = (Button) findViewById(R.id.update_cancel_btn);
        dl_btn_confirm = (Button) findViewById(R.id.update_sure_btn);
    }

    /**
     * 绑定监听
     **/
    private void setListner() {
        dl_btn_cancel.setOnClickListener(this);
        dl_btn_confirm.setOnClickListener(this);
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_cancel_btn:
                if (null != this.listener) {
                    listener.getResult(0);
                }
                UpdateDialog.this.dismiss();
                break;
            case R.id.update_sure_btn:
                if (null != this.listener) {
                    listener.getResult(1);
                }
                UpdateDialog.this.dismiss();
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
         * @param resultCode 0.取消  1.确定
         */
        void getResult(int resultCode);
    }

}
