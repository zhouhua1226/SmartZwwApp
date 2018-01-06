package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by yincong on 2018/1/5 15:45
 * 修改人：
 * 修改时间：
 * 类描述：签到弹窗
 */
public class SignInDialog extends Dialog implements View.OnClickListener{

    private ImageView imageView1,imageView2,imageView3,imageView4
            ,imageView5,imageView6,imageView7,imageView_sure;

    public SignInDialog(Context context) {
        super(context);
    }

    public SignInDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public SignInDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_dialog);
        findView();
        setListner();
    }

    /**
     * 设置天数背景
     *
     */
    public void setBackGroundColor(int[] num) {
        if(num[0]==0){
            imageView1.setEnabled(true);
            imageView1.setImageResource(R.drawable.sign_dialog_ten_unselect);
        }else {
            imageView1.setEnabled(false);
            imageView1.setImageResource(R.drawable.sign_dialog_ten_select);
        }
        if(num[1]==0){
            imageView2.setEnabled(true);
            imageView2.setImageResource(R.drawable.sign_dialog_ten_unselect);
        }else {
            imageView2.setEnabled(false);
            imageView2.setImageResource(R.drawable.sign_dialog_ten_select);
        }
        if(num[2]==0){
            imageView3.setEnabled(true);
            imageView3.setImageResource(R.drawable.sign_dialog_twenty_unselect);
        }else {
            imageView3.setEnabled(false);
            imageView3.setImageResource(R.drawable.sign_dialog_twenty_select);
        }
        if(num[3]==0){
            imageView4.setEnabled(true);
            imageView4.setImageResource(R.drawable.sign_dialog_ten_unselect);
        }else {
            imageView4.setEnabled(false);
            imageView4.setImageResource(R.drawable.sign_dialog_ten_select);
        }
        if(num[4]==0){
            imageView5.setEnabled(true);
            imageView5.setImageResource(R.drawable.sign_dialog_ten_unselect);
        }else {
            imageView5.setEnabled(false);
            imageView5.setImageResource(R.drawable.sign_dialog_ten_select);
        }
        if(num[5]==0){
            imageView6.setEnabled(true);
            imageView6.setImageResource(R.drawable.sign_dialog_ten_unselect);
        }else {
            imageView6.setEnabled(false);
            imageView6.setImageResource(R.drawable.sign_dialog_ten_select);
        }
        if(num[6]==0){
            imageView7.setEnabled(true);
            imageView7.setImageResource(R.drawable.sign_dialog_fourty_unselect);
        }else {
            imageView7.setEnabled(false);
            imageView7.setImageResource(R.drawable.sign_dialog_fourty_select);
        }
    }


    public void findView() {
        imageView1 = (ImageView) findViewById(R.id.sign_dialog_oneday_imag);
        imageView2 = (ImageView) findViewById(R.id.sign_dialog_twoday_imag);
        imageView3 = (ImageView) findViewById(R.id.sign_dialog_threeday_imag);
        imageView4 = (ImageView) findViewById(R.id.sign_dialog_fourday_imag);
        imageView5 = (ImageView) findViewById(R.id.sign_dialog_fiveday_imag);
        imageView6 = (ImageView) findViewById(R.id.sign_dialog_sexday_imag);
        imageView7 = (ImageView) findViewById(R.id.sign_dialog_sevenday_imag);
        imageView_sure= (ImageView) findViewById(R.id.sign_dialog_sure_imag);
    }

    /**
     * 绑定监听
     **/
    private void setListner() {
//        imageView1.setOnClickListener(this);
//        imageView2.setOnClickListener(this);
//        imageView3.setOnClickListener(this);
//        imageView4.setOnClickListener(this);
//        imageView5.setOnClickListener(this);
//        imageView6.setOnClickListener(this);
//        imageView7.setOnClickListener(this);
        imageView_sure.setOnClickListener(this);
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_dialog_sure_imag:
                if (null != this.listener) {
                    listener.getResult(0);
                }
                SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_oneday_imag:
                if (null != this.listener) {
                    listener.getResult(1);
                }
                //SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_twoday_imag:
                if (null != this.listener) {
                    listener.getResult(2);
                }
                //SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_threeday_imag:
                if (null != this.listener) {
                    listener.getResult(3);
                }
                //SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_fourday_imag:
                if (null != this.listener) {
                    listener.getResult(4);
                }
                //SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_fiveday_imag:
                if (null != this.listener) {
                    listener.getResult(5);
                }
                //SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_sexday_imag:
                if (null != this.listener) {
                    listener.getResult(6);
                }
                //SignInDialog.this.dismiss();
                break;
            case R.id.sign_dialog_sevenday_imag:
                if (null != this.listener) {
                    listener.getResult(7);
                }
                //SignInDialog.this.dismiss();
                break;
            default:
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
         * @param resultCode 0.确认  1.第一天  2.第二天  3.第三天 .....
         */
        void getResult(int resultCode);
    }

}
