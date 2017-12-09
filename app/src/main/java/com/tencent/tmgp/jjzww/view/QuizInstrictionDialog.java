package com.tencent.tmgp.jjzww.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tmgp.jjzww.R;

/**
 * Created by hongxiu on 2017/11/29.
 */
public class QuizInstrictionDialog extends Dialog {

    private Button cancel_button;

    public QuizInstrictionDialog(Context context) {
        super(context);
    }

    public QuizInstrictionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected QuizInstrictionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quiz_instruction);
        findView();
    }

    public void findView(){
        cancel_button= (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
