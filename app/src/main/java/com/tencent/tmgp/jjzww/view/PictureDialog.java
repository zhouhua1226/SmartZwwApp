package com.tencent.tmgp.jjzww.view;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tencent.tmgp.jjzww.R;


/**
 * Created by hongxiu on 2017/9/26.
 */
public class PictureDialog extends Dialog implements View.OnClickListener{

    private TextView shoot_tv,photo_tv,cancel_tv;
    private MydialogClick mydialogClick;
    public PictureDialog(Context context) {
        super(context);
    }

    public PictureDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PictureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public void setMydialogClick(MydialogClick mydialogClick){
        this.mydialogClick=mydialogClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picture);
        findView();
    }

    public void findView() {
        shoot_tv = (TextView) findViewById(R.id.shoot_tv);
        photo_tv = (TextView) findViewById(R.id.photo_tv);
        cancel_tv = (TextView) findViewById(R.id.cancel_tv);
        shoot_tv.setOnClickListener(this);
        photo_tv.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.shoot_tv:

                    break;
                case R.id.photo_tv:

                    break;
                case R.id.cancel_tv:
                    this.dismiss();
                    break;
            }
    }


    public interface MydialogClick{
        void getPicture();
        void getPhoto();
    }
}
