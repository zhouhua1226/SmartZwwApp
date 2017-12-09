package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by yincong on 2017/12/1 11:47
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class SelectLoginDialog extends Dialog implements View.OnClickListener{

    private final static String TAG = "ConfirmDialog";

    private Context context;
    private TextView tv_title,type_tv;//提示
    private TextView login_other_tv;
    private ImageButton login_qq_btn,login_wx_btn;
    private Button login_visitor_btn;
    private ImageView login_cancle_imag;

    public SelectLoginDialog(Context context) {
        super(context);
    }

    public SelectLoginDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public SelectLoginDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_login_dialog);
        findView();
        setListner();
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
        Log.i(TAG, type_tv + "");
        type_tv.setText(content);
    }

    public void findView() {
        tv_title = (TextView) findViewById(R.id.login_title_tv);
        type_tv = (TextView) findViewById(R.id.login_usethree_tv);
        login_other_tv= (TextView) findViewById(R.id.login_other_tv);
        login_qq_btn= (ImageButton) findViewById(R.id.login_qq_btn);
        login_wx_btn= (ImageButton) findViewById(R.id.login_wx_btn);
        login_visitor_btn= (Button) findViewById(R.id.login_visitor_btn);
        login_cancle_imag= (ImageView) findViewById(R.id.login_cancle_imag);
    }

    /**
     * 绑定监听
     **/
    private void setListner() {
        login_other_tv.setOnClickListener(this);
        login_wx_btn.setOnClickListener(this);
        login_qq_btn.setOnClickListener(this);
        login_visitor_btn.setOnClickListener(this);
        login_cancle_imag.setOnClickListener(this);

    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_qq_btn:
                if (null != this.listener) {
                    listener.getResult(0);
                }
                SelectLoginDialog.this.dismiss();
                break;
            case R.id.login_wx_btn:
                if (null != this.listener) {
                    listener.getResult(1);
                }
                SelectLoginDialog.this.dismiss();
                break;
            case R.id.login_visitor_btn:
                if (null != this.listener) {
                    listener.getResult(3);
                }
                SelectLoginDialog.this.dismiss();
                break;
            case R.id.login_other_tv:
                if (null != this.listener) {
                    listener.getResult(2);
                }
                SelectLoginDialog.this.dismiss();
                break;
            case R.id.login_cancle_imag:
                if (null != this.listener) {
                    listener.getResult(4);
                }
                SelectLoginDialog.this.dismiss();
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
         * @param resultCode 0.QQ登录  1.微信登录  2.其它方式  3.游客登录   4.取消
         */
        void getResult(int resultCode);
    }

}

