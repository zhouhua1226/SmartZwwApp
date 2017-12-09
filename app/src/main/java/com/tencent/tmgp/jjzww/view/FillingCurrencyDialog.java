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
 * Created by hongxiu on 2017/10/10.
 */
public class FillingCurrencyDialog extends Dialog implements View.OnClickListener {


    private RelativeLayout money_10_rl,money_20_rl,money_50_rl,money_100_rl;
    private Button cancel_button;
    private TextView money_10_tv,money_20_tv,money_50_tv,money_100_tv;
    private MyDialogClick mydialogClick;

    public FillingCurrencyDialog(Context context) {
        super(context);
    }

    public FillingCurrencyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected FillingCurrencyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setDialogClickListener( MyDialogClick mydialogClick) {
        this.mydialogClick = mydialogClick;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fillingcurrency);
        findView();
        setListner();
    }

    public void findView(){
        money_10_rl= (RelativeLayout) findViewById(R.id.money_10_rl);
        money_20_rl= (RelativeLayout) findViewById(R.id.money_20_rl);
        money_50_rl= (RelativeLayout) findViewById(R.id.money_50_rl);
        money_100_rl= (RelativeLayout) findViewById(R.id.money_100_rl);
        cancel_button= (Button) findViewById(R.id.cancel_button);
        money_10_tv= (TextView) findViewById(R.id.money_10_tv);
        money_20_tv= (TextView) findViewById(R.id.money_20_tv);
        money_50_tv= (TextView) findViewById(R.id.money_50_tv);
        money_100_tv= (TextView) findViewById(R.id.money_100_tv);
    }
    private void setListner(){
        money_10_rl.setOnClickListener(this);
        money_20_rl.setOnClickListener(this);
        money_50_rl.setOnClickListener(this);
        money_100_rl.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.money_10_rl:

                money_10_tv.setText("10");
                mydialogClick.getMoney10(money_10_tv.getText().toString());
                break;
            case R.id.money_20_rl:
                money_20_tv.setText("20");
                mydialogClick.getMoney20(money_20_tv.getText().toString());
                break;
            case R.id.money_50_rl:
                money_50_tv.setText("50");
                mydialogClick.getMoney50(money_50_tv.getText().toString());
                break;
            case R.id.money_100_rl:
                money_100_tv.setText("100");
                mydialogClick.getMoney100(money_100_tv.getText().toString());
                break;
            case R.id.cancel_button:
                this.dismiss();
                break;
        }
    }

    public interface  MyDialogClick{
        void getMoney10(String money);
        void getMoney20(String money);
        void getMoney50(String money);
        void getMoney100(String money);
    }
}
