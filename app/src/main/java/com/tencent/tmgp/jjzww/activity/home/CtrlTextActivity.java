package com.tencent.tmgp.jjzww.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.tencent.tmgp.jjzww.R;
import com.gatz.netty.utils.NettyUtils;
import com.iot.game.pooh.server.entity.json.enums.MoveType;

public class CtrlTextActivity extends AppCompatActivity {
    private Button forntBtn, backBtn, leftBtn, rightBtn, okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrl_text);
        forntBtn = (Button) findViewById(R.id.front_btn);
        backBtn = (Button) findViewById(R.id.back_btn);
        leftBtn = (Button) findViewById(R.id.left_btn);
        rightBtn = (Button) findViewById(R.id.right_btn);
        okBtn = (Button) findViewById(R.id.ok_btn);
        forntBtn.setOnTouchListener(onTouchListener);
        backBtn.setOnTouchListener(onTouchListener);
        leftBtn.setOnTouchListener(onTouchListener);
        rightBtn.setOnTouchListener(onTouchListener);
        okBtn.setOnTouchListener(onTouchListener);
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.front_btn:
                            NettyUtils.sendCtrlCmd(MoveType.FRONT);
                            break;
                        case R.id.back_btn:
                            NettyUtils.sendCtrlCmd(MoveType.BACK);
                            break;
                        case R.id.left_btn:
                            NettyUtils.sendCtrlCmd(MoveType.LEFT);
                            break;
                        case R.id.right_btn:
                            NettyUtils.sendCtrlCmd(MoveType.RIGHT);
                            break;
                        case R.id.ok_btn:
                            NettyUtils.sendCtrlCmd(MoveType.CATCH);
                            break;
                        default:
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    NettyUtils.sendCtrlCmd(MoveType.STOP);
                    break;
                default:
                    break;
            }
            return true;
        }
    };
}
