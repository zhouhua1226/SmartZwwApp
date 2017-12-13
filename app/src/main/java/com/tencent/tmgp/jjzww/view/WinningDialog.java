package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by hongxiu on 2017/12/13.
 */
public  class WinningDialog extends Dialog{


    private TextView winning_text;
    private ImageView back_image;
    public WinningDialog(Context context) {
        super(context);
    }

    public WinningDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WinningDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_winning);
        findView();
    }

    private void findView() {
        winning_text= (TextView) findViewById(R.id.winning_text);
        back_image= (ImageView) findViewById(R.id.back_image);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void setShowText(String content){
        winning_text.setText(content);
    }

}
