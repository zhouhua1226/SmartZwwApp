package com.tencent.tmgp.jjzww.activity.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.base.BaseActivity;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yincong on 2018/3/7 16:15
 * 修改人：
 * 修改时间：
 * 类描述：加盟推广类
 */
public class JoinEarnActivity extends BaseActivity {

    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.joinearn_fifty_tv)
    TextView joinearnFiftyTv;
    @BindView(R.id.joinearn_onehundred_tv)
    TextView joinearnOnehundredTv;
    @BindView(R.id.joinearn_twohundred_tv)
    TextView joinearnTwohundredTv;
    @BindView(R.id.joinearn_ll)
    LinearLayout joinearnLl;

    private String TAG = "JoinEarnActivity--";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joinearn;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_back, R.id.joinearn_fifty_tv, R.id.joinearn_onehundred_tv, R.id.joinearn_twohundred_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.joinearn_fifty_tv:
                showJoinDialog("W3D5F8G6");
                break;
            case R.id.joinearn_onehundred_tv:
                showJoinDialog("Lsd5P8g8");
                break;
            case R.id.joinearn_twohundred_tv:
                showJoinDialog("F37hF1G3");
                break;
        }
    }

    private void showJoinDialog(final String code) {
        View view = getLayoutInflater().inflate(R.layout.dialog_joinearn, null);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        final PopupWindow mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPop.setOutsideTouchable(true);
        mPop.setFocusable(true);
        mPop.showAtLocation(joinearnLl, Gravity.CENTER, 0, 0);//在屏幕居中，无偏移
        BackgroudAlpha((float)0.5);
        mPop.setOnDismissListener(new popupwindowdismisslistener());
        TextView cancleTv = (TextView) view.findViewById(R.id.dialog_joinearn_cancle_tv);
        TextView copyTv = (TextView) view.findViewById(R.id.dialog_joinearn_copy_tv);
        TextView codeTv= (TextView) view.findViewById(R.id.dialog_joinearn_code_tv);
        codeTv.setText(code);
        copyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!code.equals("")) {
                    // 将文本内容放到系统剪贴板里。
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(code);
                    mPop.dismiss();
                    MyToast.getToast(JoinEarnActivity.this, "复制成功！").show();
                }
            }
        });
        cancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
            }
        });
        // 重写onKeyListener
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mPop.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    //设置屏幕背景透明度
    private void BackgroudAlpha(float alpha) {
        // TODO Auto-generated method stub
        WindowManager.LayoutParams l = this.getWindow().getAttributes();
        l.alpha = alpha;
        getWindow().setAttributes(l);
    }
    //点击其他部分popwindow消失时，屏幕恢复透明度
    class popupwindowdismisslistener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            BackgroudAlpha((float)1);
        }

    }

}
