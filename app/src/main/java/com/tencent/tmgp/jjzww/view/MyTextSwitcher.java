package com.tencent.tmgp.jjzww.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tencent.tmgp.jjzww.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hongxiu on 2017/12/5.
 */
public class MyTextSwitcher extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private int index = -1;
    private Context context;
    private Timer timer; //
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    break;
            }
        }
    };

    private String[] resources = {};

    public MyTextSwitcher(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        if (timer == null)
            timer = new Timer();
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.in_animation));
        this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.out_animation));
    }

    public void setResources(String[] res) {
        this.resources = res;
    }

    public void setTextStillTime(long time) {
        if (timer == null) {
            timer = new Timer();
        } else {
            timer.scheduleAtFixedRate(new MyTask(), 1, time);//每3秒更新
        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }

    private int next() {
        int flag = index + 1;
        if (flag > resources.length - 1) {
            flag = flag - resources.length;
        }
        return flag;
    }

    private void updateText() {
        this.setText(resources[index]);
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(context);
        tv.setTextColor(getResources().getColor(R.color.zww_broadcast_text));
        tv.setTextSize(13);
//        center
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        return tv;
    }
}
