package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by yincong on 2017/9/6 10:49
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class ProgressDialogs extends Dialog {

    private TextView textView;

    public ProgressDialogs(Context context) {
        super(context);
        setLoadingDialog();
    }

    public ProgressDialogs(Context context, int theme) {
        super(context, theme);
        setLoadingDialog();
    }

    protected ProgressDialogs(Context context, boolean cancelable,
                              OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setLoadingDialog();
    }

    private void setLoadingDialog() {
        // 加载自定义dialog的布局文件
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.progress_small, null);
        // 无标题
        textView= (TextView) dialog_view.findViewById(R.id.progress_small_tv);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置布局
        Window window=super.getWindow();
        window.setContentView(dialog_view);

        // 点击dialog外侧不会消失
        this.setCanceledOnTouchOutside(false);
    }

    public void setText(String text){
        textView.setText(text);
    }

    public void setTextColor(int textColor){
        textView.setTextColor(textColor);
    }


}
