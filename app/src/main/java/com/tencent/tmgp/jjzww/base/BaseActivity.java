package com.tencent.tmgp.jjzww.base;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GuessingSuccessDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.game.UMGameAgent;
import com.umeng.message.PushAgent;


/**
 * Created by zhouh on 2017/9/7.
 */
public abstract class BaseActivity extends AppCompatActivity{
    private static GuessingSuccessDialog guessingSuccessDialog;
    private static final String TAG = "BaseActivity---";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        afterCreate(savedInstanceState);
        MyApplication.getInstance().activities.add(this);
        PushAgent.getInstance(this).onAppStart();
        setStatusBarColor();
//        RxBus.get().register(this);
//        //initDialog();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(UserUtils.ACTION_LOTTERY);
//        this.registerReceiver(LotteryReceiver, intentFilter);
        //友盟统计
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_GAME);  //游戏场景
        UMGameAgent.setDebugMode(true);//设置输出运行时日志
        UMGameAgent.init( this );
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.get().unregister(this);
//        this.unregisterReceiver(LotteryReceiver);
    }

    private void initDialog() {
        if (guessingSuccessDialog == null) {
            guessingSuccessDialog = new GuessingSuccessDialog(this, R.style.easy_dialog_style);
        }
    }

//    private BroadcastReceiver LotteryReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String roomId = intent.getStringExtra(UserUtils.LOTTERY_ROOMID);
//            String pId = intent.getStringExtra(UserUtils.LOTTERY_PERIODSNUM);
//            Utils.showLogE(TAG, "房间号:" + roomId + "第" + pId + "期开奖了.......");
//            //showLotteryDialog();
//        }
//    };
//
//    private void showLotteryDialog() {
//        initDialog();
//        guessingSuccessDialog.setCancelable(true);
//        guessingSuccessDialog.show();
//        guessingSuccessDialog.setDialogResultListener(new GuessingSuccessDialog.DialogResultListener() {
//            @Override
//            public void getResult(int resultCode) {
//                if(resultCode==0){
//                    guessingSuccessDialog.dismiss();
//                }
//            }
//        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                guessingSuccessDialog.dismiss();
//            }
//        },3000);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
        UMGameAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
        UMGameAgent.onPause(this);
    }

    /** 重写getResources方法，使app字体大小不受手机设置字体大小的影响  */
    @Override
    public Resources getResources() {
        //获取到resources对象
        Resources res = super.getResources();
        //修改configuration的fontScale属性
        res.getConfiguration().fontScale = 1;
        //将修改后的值更新到metrics.scaledDensity属性上
        res.updateConfiguration(null,null);
        return res;
    }

    //设置状态栏
    private  void setStatusBarColor(){
        Window window =getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.apptheme_bg));
            Utils.showLogE(TAG,"类名="+getClass().getSimpleName());
        }

    }

}
