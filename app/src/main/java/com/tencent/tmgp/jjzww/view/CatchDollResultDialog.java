package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by yincong on 2017/12/13 16:58
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class CatchDollResultDialog extends Dialog implements View.OnClickListener{

    private final static String TAG = "CatchDollResultDialog";
    private Context mContext;
    private TextView fail_tv,success_tv,title,content;
    private RelativeLayout bg_layout;
    private ImageView imageView;
    private AnimationDrawable animation;

    public CatchDollResultDialog(Context context) {
        super(context);
    }

    public CatchDollResultDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public CatchDollResultDialog(Context context, int theme) {
        super(context, theme);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_catchdoll_result);
        findView();
        setListner();

    }


    public void findView() {
        fail_tv= (TextView) findViewById(R.id.catchdialog_fail_tv);
        success_tv= (TextView) findViewById(R.id.catchdialog_success_tv);
        title= (TextView) findViewById(R.id.catchdialog_title1_tv);
        content= (TextView) findViewById(R.id.catchdialog_content_tv);
        bg_layout= (RelativeLayout) findViewById(R.id.catchdialog_layout);
        imageView= (ImageView) findViewById(R.id.catchdialog_anim_imag);
    }

    public void setTitle(String titles){
        title.setText(titles);
    }

    public void setContent(String contents){
        content.setText(contents);
    }

    public void setBackground(int picter){
        bg_layout.setBackgroundResource(picter);
    }

    /**
     * 绑定监听
     **/
    private void setListner() {
        fail_tv.setOnClickListener(this);
        success_tv.setOnClickListener(this);
    }

    /**
     * 点击监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.catchdialog_fail_tv:
                if (null != this.listener) {
                    listener.getResult(0);
                }
                animation.stop();
                CatchDollResultDialog.this.dismiss();
                break;
            case R.id.catchdialog_success_tv:
                if (null != this.listener) {
                    listener.getResult(1);
                }
                animation.stop();
                CatchDollResultDialog.this.dismiss();
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
         * @param resultCode 0.取消 1.再试一次
         */
        void getResult(int resultCode);
    }

    public void setStartAnimation() {
        animation = new AnimationDrawable();
        animation.addFrame(mContext.getResources().getDrawable(R.drawable.catchdialogresult_three_bg), 1000);
        animation.addFrame(mContext.getResources().getDrawable(R.drawable.catchdialogresult_two_bg), 1000);
        animation.addFrame(mContext.getResources().getDrawable(R.drawable.catchdialogresult_one_bg), 1000);
        animation.setOneShot(false);
        imageView.setBackgroundDrawable(animation);
        // start the animation!
        animation.start();
    }

}

