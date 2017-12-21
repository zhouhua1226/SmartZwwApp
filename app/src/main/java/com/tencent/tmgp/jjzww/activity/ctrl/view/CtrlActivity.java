package com.tencent.tmgp.jjzww.activity.ctrl.view;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.global.ConnectResultEvent;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.iot.game.pooh.server.entity.json.MoveControlResponse;
import com.iot.game.pooh.server.entity.json.announce.GatewayPoohStatusMessage;
import com.iot.game.pooh.server.entity.json.app.AppInRoomResponse;
import com.iot.game.pooh.server.entity.json.app.AppOutRoomResponse;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.iot.game.pooh.server.entity.json.enums.PoohAbnormalStatus;
import com.iot.game.pooh.server.entity.json.enums.ReturnCode;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.ctrl.presenter.CtrlCompl;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.bean.AppUserBean;
import com.tencent.tmgp.jjzww.bean.LoginInfo;
import com.tencent.tmgp.jjzww.bean.PondResponseBean;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.CatchDollResultDialog;
import com.tencent.tmgp.jjzww.view.FillingCurrencyDialog;
import com.tencent.tmgp.jjzww.view.GifView;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;
import com.tencent.tmgp.jjzww.view.MyToast;
import com.tencent.tmgp.jjzww.view.QuizInstrictionDialog;
import com.tencent.tmgp.jjzww.view.TimeCircleProgressView;
import com.tencent.tmgp.jjzww.view.VibratorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by zhouh on 2017/9/8. SomeCommits
 */
public class CtrlActivity extends Activity implements IctrlView {
    @BindView(R.id.realplay_sv)
    SurfaceView mRealPlaySv;
    @BindView(R.id.ctrl_gif_view)
    GifView ctrlGifView;
    @BindView(R.id.ctrl_fail_iv)
    ImageView ctrlFailIv;
    @BindView(R.id.image_back)
    ImageButton imageBack;
    @BindView(R.id.coin_tv)
    TextView coinTv;
    @BindView(R.id.recharge_button)
    ImageButton rechargeButton;
    @BindView(R.id.front_image)
    ImageView topImage;
    @BindView(R.id.back_image)
    ImageView belowImage;
    @BindView(R.id.left_image)
    ImageView leftImage;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.startgame_ll)
    LinearLayout startgameLl;
    @BindView(R.id.recharge_ll)
    LinearLayout rechargeLl;
    @BindView(R.id.catch_ll)
    RelativeLayout catchLl;
    @BindView(R.id.operation_rl)
    RelativeLayout operationRl;
    @BindView(R.id.doll_name)
    TextView dollNameTv;
    @BindView(R.id.player_counter_tv)
    TextView playerCounterIv;
    @BindView(R.id.player2_iv)
    ImageView playerSecondIv;
    @BindView(R.id.main_player_iv)
    ImageView playerMainIv;
    @BindView(R.id.player_name_tv)
    TextView playerNameTv;
    @BindView(R.id.ctrl_status_iv)
    ImageView ctrlStatusIv;
    @BindView(R.id.ctrl_time_progress_view)
    TimeCircleProgressView timeCircleProgressView;
    @BindView(R.id.ctrl_dollgold_tv)
    TextView ctrlDollgoldTv;
    @BindView(R.id.ctrl_change_camera_iv)
    ImageView ctrlChangeCameraIv;

    private static final String TAG = "CtrlActivity---";
    @BindView(R.id.ctrl_quiz_layout)
    RelativeLayout ctrlQuizLayout;
    @BindView(R.id.ctrl_instruction_image)
    ImageView ctrlInstructionImage;
    @BindView(R.id.ctrl_buttom_layout)
    RelativeLayout ctrlButtomLayout;
    @BindView(R.id.ctrl_betting_number_one)
    TextView ctrlBettingNumberOne;
    @BindView(R.id.ctrl_betting_number_two)
    TextView ctrlBettingNumberTwo;
    @BindView(R.id.ctrl_betting_number_layout)
    LinearLayout ctrlBettingNumberLayout;
    @BindView(R.id.ctrl_betting_winning)
    Button ctrlBettingWinning;
    @BindView(R.id.ctrl_betting_fail)
    Button ctrlBettingFail;
    @BindView(R.id.ctrl_dollgold_tv1)
    TextView ctrlDollgoldTv1;
    @BindView(R.id.ctrl_confirm_layout)
    LinearLayout ctrlConfirmLayout;
    @BindView(R.id.ctrl_beting_layout)
    RelativeLayout ctrlBetingLayout;
    @BindView(R.id.player_layout)
    RelativeLayout playerLayout;
    @BindView(R.id.realplay_rl)
    RelativeLayout realplayRl;
    @BindView(R.id.money_image)
    ImageView moneyImage;
    @BindView(R.id.ctrl_betting_back_button)
    Button ctrlBettingBackButton;
    @BindView(R.id.startgame_text_imag)
    ImageView startgameTextImag;
    @BindView(R.id.ctrl_back_imag)
    ImageView ctrlBackImag;

    private CtrlCompl ctrlCompl;
    private FillingCurrencyDialog fillingCurrencyDialog;

    private List<String> userInfos = new ArrayList<>();  //房屋内用户电话信息

    //2017/11/18 11：10 加入振动器
    public Vibrator vibrator; // 震动器
    private String camera_name;
    private String dollName = "未知";
    private boolean isCurrentConnect = true;
    private String upTime;
    private String upFileName;
    private int money = 0;
    private String state = "";
    private QuizInstrictionDialog quizInstrictionDialog;
    private String dollId;
    private String zt = "";
    private Boolean isOpenSucess = false; //以第一个摄像头为标准
    //播放地址流
    private String playUrlMain = "";
    private String playUrlSecond = "";
    private String currentUrl;
    //用户操作和竞猜
    private boolean isStart = false;
    private boolean isLottery = false;
    private String periodsNum;
    private MediaPlayer mediaPlayer;
    private MediaPlayer btn_mediaPlayer;

    static {
        System.loadLibrary("SmartPlayer");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate();
    }

    private int getLayoutId() {
        return R.layout.activity_ctrl;
    }

    private void afterCreate() {
        playBGMusic();   //播放房间背景音乐
        Utils.showLogE(TAG, "afterCreate");
        initView();
        initData();
        coinTv.setText("  " + UserUtils.UserBalance);
        setVibrator();   //初始化振动器
    }

    protected void initView() {
        ButterKnife.bind(this);
        RxBus.get().register(this);
        Utils.showLogE(TAG, "=====" + UserUtils.UserPhone);
        NettyUtils.sendRoomInCmd();
        ctrlGifView.setVisibility(View.VISIBLE);
        ctrlGifView.setEnabled(false);
        ctrlGifView.setMovieResource(R.raw.ctrl_video_loading);
        ctrlFailIv.setVisibility(View.GONE);
        if (Utils.connectStatus.equals(ConnectResultEvent.CONNECT_FAILURE)) {
            ctrlStatusIv.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        } else {
            ctrlStatusIv.setImageResource(R.drawable.point_green);
        }
        NettyUtils.pingRequest(); //判断连接
    }

    private void initData() {
        ctrlCompl = new CtrlCompl(this, this);
        playUrlMain = getIntent().getStringExtra(Utils.TAG_URL_MASTER);
        playUrlSecond = getIntent().getStringExtra(Utils.TAG_URL_SECOND);
        dollName = getIntent().getStringExtra(Utils.TAG_ROOM_NAME);
        money = Integer.parseInt(getIntent().getStringExtra(Utils.TAG_DOLL_GOLD));
        dollId = getIntent().getStringExtra(Utils.TAG_DOLL_Id);
        if (!Utils.isEmpty(dollName)) {
            dollNameTv.setText(dollName);
        }
        ctrlDollgoldTv.setText(money + "/次");
        ctrlDollgoldTv1.setText(money + "/次");//下注金额
        playerNameTv.setText(UserUtils.NickName);
        setStartMode(getIntent().getBooleanExtra(Utils.TAG_ROOM_STATUS, true));
        ctrlQuizLayout.setEnabled(false);
        currentUrl = playUrlMain;
        ctrlCompl.startPlayVideo(mRealPlaySv, currentUrl);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.showLogE(TAG, "onDestroy");
        ctrlCompl.stopPlayVideo();
        ctrlCompl.stopRecordView();
        ctrlCompl.sendCmdCtrl(MoveType.CATCH);
        ctrlCompl.stopTimeCounter();
        ctrlCompl.sendCmdOutRoom();
        NettyUtils.sendGetDeviceStatesCmd();
        ctrlCompl = null;
        RxBus.get().unregister(this);

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (btn_mediaPlayer != null && btn_mediaPlayer.isPlaying()) {
            btn_mediaPlayer.stop();
            btn_mediaPlayer.release();
            btn_mediaPlayer = null;
        }
    }

    @Override
    public void getTime(int time) {
        timeCircleProgressView.setProgress(Utils.CATCH_TIME_OUT - time);
    }

    @Override
    public void getTimeFinish() {
        ctrlCompl.sendCmdCtrl(MoveType.CATCH);
        ctrlCompl.stopTimeCounter();
        timeCircleProgressView.setProgress(Utils.CATCH_TIME_OUT);
    }

    @Override
    public void getUserInfos(List<String> list) {
        //当前房屋的人数
        userInfos = list;
        int counter = userInfos.size();
        if (counter > 0) {
            playerCounterIv.setText(String.format(getString(R.string.player_counter_text), counter));
            Glide.with(this).load(UserUtils.UserImage).asBitmap().transform(new GlideCircleTransform(this)).into(playerMainIv);
            if (counter == 1) {
                playerSecondIv.setVisibility(View.INVISIBLE);
            } else {
                //先显示默认图片
                Glide.with(getApplicationContext()).load(R.drawable.ctrl_default_user_bg)
                        .asBitmap().transform(new GlideCircleTransform(CtrlActivity.this)).into(playerSecondIv);
                //显示另外一个人
                for (int i = 0; i < counter; i++) {
                    if (!userInfos.get(i).equals(UserUtils.NickName)) {
                        getCtrlUserImage(userInfos.get(i));
                        break;
                    }
                }
                //显示第二个人
                playerSecondIv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void getRecordErrCode(int code) {
        Utils.showLogE(TAG, "录制视频失败::::::" + code);
    }

    @Override
    public void getRecordSuecss() {
        Utils.showLogE(TAG, "录制视频完毕......");
    }

    @Override
    public void getRecordAttributetoNet(String time, String fileName) {
        Utils.showLogE(TAG, "视频上传的时间::::" + time + "=====" + fileName);
        upTime = time;
        upFileName = fileName;
    }

    @Override
    public void getPlayerErcErrCode(int code) {
        Utils.showLogE(TAG, "直播失败,错误码:::::" + code);
        uiHandler.sendEmptyMessage(0);
    }

    @Override
    public void getPlayerSucess() {
        Utils.showLogE(TAG, "直播Sucess:::::");
        uiHandler.sendEmptyMessage(1);
    }

    @Override
    public void getVideoPlayConnect() {

    }

    @Override
    public void getVideoPlayStart() {

    }

    @Override
    public void getVideoStop() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        ctrlCompl.stopPlayVideo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.showLogE(TAG, "onRestart");
        if (ctrlFailIv.getVisibility() == View.VISIBLE) {
            ctrlFailIv.setVisibility(View.GONE);
        }
        ctrlGifView.setVisibility(View.VISIBLE);
        ctrlCompl.startPlayVideo(mRealPlaySv, currentUrl);
        NettyUtils.pingRequest();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ctrlGifView.setVisibility(View.GONE);
                    ctrlFailIv.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ctrlGifView.setVisibility(View.GONE);
                    if (ctrlFailIv.getVisibility() == View.VISIBLE) {
                        ctrlFailIv.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.image_back, R.id.recharge_button,R.id.ctrl_back_imag,
            R.id.startgame_ll, R.id.ctrl_fail_iv, R.id.ctrl_quiz_layout, R.id.ctrl_instruction_image, R.id.ctrl_betting_winning, R.id.ctrl_betting_fail,
            R.id.ctrl_confirm_layout, R.id.ctrl_betting_back_button,
            R.id.ctrl_change_camera_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
            case R.id.ctrl_back_imag:
                finish();
                break;
            case R.id.recharge_button:
                getMoney();
                break;
            case R.id.startgame_ll:
                //开始游戏按钮
                if (Integer.parseInt(UserUtils.UserBalance) >= money) {
                    if ((Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS))
                            && (isCurrentConnect)) {
                        ctrlCompl.sendCmdCtrl(MoveType.START);
                        coinTv.setText("  " + (Integer.parseInt(UserUtils.UserBalance) - money) + "");
                        //getCreatPlayList(UserUtils.NickName, dollName);//开始游戏分发场次
                        //getPlayNum(UserUtils.USER_ID, String.valueOf(money), dollId); //扣款
                        isStart = true;
                    }
                    setVibratorTime(300, -1);
                    rechargeButton.setVisibility(View.GONE);
                } else {
                    MyToast.getToast(getApplicationContext(), "余额不足，请充值！").show();
                }
                break;
            case R.id.ctrl_fail_iv:
                ctrlFailIv.setVisibility(View.GONE);
                ctrlGifView.setVisibility(View.VISIBLE);
                break;
            case R.id.ctrl_quiz_layout:
                //竞猜
                ctrlButtomLayout.setVisibility(View.GONE);
                ctrlBetingLayout.setVisibility(View.VISIBLE);
                //getPlayId(dollId);//给围观群众分发id
                getPond(periodsNum, dollId);
                break;
            case R.id.ctrl_instruction_image:
                //说明
                quizInstrictionDialog = new QuizInstrictionDialog(this, R.style.easy_dialog_style);
                quizInstrictionDialog.show();
                break;

            case R.id.ctrl_betting_winning:
                zt = "1";
                ctrlBettingWinning.setBackgroundResource(R.drawable.ctrl_betting_item);
                ctrlBettingFail.setBackgroundResource(R.drawable.fillingcureency_dialog_gray);
                //中
                break;
            case R.id.ctrl_betting_fail:
                zt = "0";
                ctrlBettingFail.setBackgroundResource(R.drawable.ctrl_betting_item);
                ctrlBettingWinning.setBackgroundResource(R.drawable.fillingcureency_dialog_gray);
                //不中
                break;
            case R.id.ctrl_confirm_layout:
                //下注
                if (Integer.parseInt(UserUtils.UserBalance) >= money) {
                    if (zt.equals("1") || zt.equals("0")) {
                        getBets(UserUtils.USER_ID, Integer.valueOf(money).intValue(), zt, periodsNum, dollId);
                        coinTv.setText("  " + (Integer.parseInt(UserUtils.UserBalance) - money) + "");
                        ctrlButtomLayout.setVisibility(View.VISIBLE);
                        ctrlBetingLayout.setVisibility(View.GONE);
                        ctrlQuizLayout.setEnabled(false);
                        moneyImage.setImageResource(R.drawable.ctrl_unbet_button);
                        isLottery = true;
                    } else {
                        MyToast.getToast(getApplicationContext(), "请下注！").show();
                    }
                } else {
                    MyToast.getToast(getApplicationContext(), "请充值！").show();
                }
                break;
            case R.id.ctrl_betting_back_button:
                ctrlBetingLayout.setVisibility(View.GONE);
                ctrlButtomLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.ctrl_change_camera_iv:
                currentUrl = currentUrl.equals(playUrlMain) ? playUrlSecond : playUrlMain;
                ctrlCompl.startPlaySwitchUrlVideo(currentUrl);
                break;
            default:
                break;
        }
    }


    private void getWorkstation() {
        ctrlQuizLayout.setVisibility(View.GONE);
        ctrlDollgoldTv.setVisibility(View.GONE);
        startgameLl.setVisibility(View.GONE);
        rechargeLl.setVisibility(View.GONE);
        catchLl.setVisibility(View.VISIBLE);
        operationRl.setVisibility(View.VISIBLE);
        ctrlCompl.startTimeCounter();
    }

    private void getStartstation() {
        ctrlQuizLayout.setVisibility(View.VISIBLE);
        ctrlDollgoldTv.setVisibility(View.VISIBLE);
        startgameLl.setVisibility(View.VISIBLE);
        rechargeLl.setVisibility(View.VISIBLE);
        catchLl.setVisibility(View.GONE);
        operationRl.setVisibility(View.GONE);
    }

    private void getMoney() {
        fillingCurrencyDialog = new FillingCurrencyDialog(this, R.style.easy_dialog_style);
        fillingCurrencyDialog.show();
        fillingCurrencyDialog.setDialogClickListener(myDialogClick);
    }

    private FillingCurrencyDialog.MyDialogClick myDialogClick =
            new FillingCurrencyDialog.MyDialogClick() {
                @Override
                public void getMoney10(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }

                @Override
                public void getMoney20(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }

                @Override
                public void getMoney50(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }

                @Override
                public void getMoney100(String money) {
                    Intent intent = new Intent(CtrlActivity.this, WeChatPayActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }
            };

    //摇杆操作
    @OnTouch({R.id.front_image, R.id.back_image, R.id.left_image, R.id.right_image, R.id.catch_ll})
    public boolean onTouchEvent(View view, MotionEvent motionEvent) {
        if ((!Utils.connectStatus.equals(ConnectResultEvent.CONNECT_SUCCESS))) {
            return false;
        }
        if (!isCurrentConnect) {
            return false;
        }
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                switch (view.getId()) {
                    case R.id.front_image:
                        setVibratorTime(3000, 1);
                        if (currentUrl.equals(playUrlMain)) {
                            ctrlCompl.sendCmdCtrl(MoveType.FRONT);
                        } else if (currentUrl.equals(playUrlSecond)) {
                            ctrlCompl.sendCmdCtrl(MoveType.LEFT);
                        } else {
                            ctrlCompl.sendCmdCtrl(MoveType.FRONT);
                        }
                        topImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_select_up_imag));
                        break;
                    case R.id.back_image:
                        setVibratorTime(3000, 1);
                        if (currentUrl.equals(playUrlMain)) {
                            ctrlCompl.sendCmdCtrl(MoveType.BACK);
                        } else if (currentUrl.equals(playUrlSecond)) {
                            ctrlCompl.sendCmdCtrl(MoveType.RIGHT);
                        } else {
                            ctrlCompl.sendCmdCtrl(MoveType.BACK);
                        }
                        belowImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_select_down_imag));
                        break;
                    case R.id.left_image:
                        setVibratorTime(3000, 1);
                        if (currentUrl.equals(playUrlMain)) {
                            ctrlCompl.sendCmdCtrl(MoveType.LEFT);
                        } else if (currentUrl.equals(playUrlSecond)) {
                            ctrlCompl.sendCmdCtrl(MoveType.BACK);
                        } else {
                            ctrlCompl.sendCmdCtrl(MoveType.LEFT);
                        }
                        leftImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_select_left_imag));
                        break;
                    case R.id.right_image:
                        setVibratorTime(3000, 1);
                        if (currentUrl.equals(playUrlMain)) {
                            ctrlCompl.sendCmdCtrl(MoveType.RIGHT);
                        } else if (currentUrl.equals(playUrlSecond)) {
                            ctrlCompl.sendCmdCtrl(MoveType.FRONT);
                        } else {
                            ctrlCompl.sendCmdCtrl(MoveType.RIGHT);
                        }
                        rightImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_select_right_imag));
                        break;
                    case R.id.catch_ll:
                        setVibratorTime(300, -1);
                        ctrlCompl.sendCmdCtrl(MoveType.CATCH);
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (view.getId()) {
                    case R.id.front_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        topImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_up_imag));
                        break;
                    case R.id.back_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        belowImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_down_imag));
                        break;
                    case R.id.left_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        leftImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_left_imag));
                        break;
                    case R.id.right_image:
                        vibrator.cancel();
                        ctrlCompl.sendCmdCtrl(MoveType.STOP);
                        rightImage.setImageDrawable(getResources().getDrawable(R.drawable.ctrl_right_imag));
                        break;
                    case R.id.catch_ll:
                        ctrlCompl.stopTimeCounter();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    //初始化振动器   2017/11/18 11:11
    private void setVibrator() {
        vibrator = VibratorView.getVibrator(getApplicationContext());
    }

    //设置震动时间
    private void setVibratorTime(long time, int replay) {
        if (null != vibrator)
            vibrator.vibrate(new long[]{0, time, 0, 0}, replay);
    }

    private void setStartMode(boolean isFree) {
        startgameLl.setEnabled(isFree);
        if (isFree) {
            startgameLl.setBackgroundResource(R.drawable.ctrl_startgame_button);
            startgameTextImag.setImageResource(R.drawable.begin_game_text);
            moneyImage.setImageResource(R.drawable.ctrl_unbet_button);
            ctrlQuizLayout.setEnabled(false);
            ctrlQuizLayout.setVisibility(View.VISIBLE);
            return;
        }
        startgameLl.setBackgroundResource(R.drawable.ctrl_unstartgame_button);
        startgameTextImag.setImageResource(R.drawable.ctrl_begin_loading);
        if (userInfos.size() > 1) {
            moneyImage.setImageResource(R.drawable.ctrl_bet_button);
            ctrlQuizLayout.setEnabled(true);
        } else {
            moneyImage.setImageResource(R.drawable.ctrl_unbet_button);
            ctrlQuizLayout.setEnabled(false);
        }
    }

    /**************************************************
     * 控制状态区
     *****************************************************/
    //控制区与状态区
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_ROOM_IN),
            @Tag(Utils.TAG_ROOM_OUT),
            @Tag(Utils.TAG_MOVE_FAILE),
            @Tag(Utils.TAG_MOVE_RESPONSE)})
    public void getKnxConnectStates(Object response) {
        if (response instanceof MoveControlResponse) {
            MoveControlResponse moveControlResponse = (MoveControlResponse) response;
            Utils.showLogE(TAG, moveControlResponse.toString());
            if ((moveControlResponse.getSeq() == -2)) {
                if (moveControlResponse.getMoveType() == null) {
                    return;
                }
                if (moveControlResponse.getMoveType().name().equals(MoveType.START.name())) {
                    Utils.showLogE(TAG, "观看者观察到start了......");
                    setStartMode(false);
                    //获取期号
                    periodsNum = moveControlResponse.getPeriodsNum();
                } else if (moveControlResponse.getMoveType().name().equals(MoveType.CATCH.name())) {
                    //TODO 其他用户下爪了 观看者
                    Utils.showLogE(TAG, "观看者观察到下爪了......");
                    //ctrlQuizLayout.setBackgroundResource(R.drawable.fillingcureency_dialog_gray);//点击下抓，竞猜变色
                    moneyImage.setImageResource(R.drawable.ctrl_unbet_button);
                    ctrlQuizLayout.setEnabled(false);
                    ctrlBetingLayout.setVisibility(View.GONE);
                    ctrlButtomLayout.setVisibility(View.VISIBLE);
                }
            } else {
                if (moveControlResponse.getReturnCode() != ReturnCode.SUCCESS) {
                    return;
                }
                //本人点击start
                if (moveControlResponse.getMoveType().name()
                        .equals(MoveType.START.name())) {
                    getWorkstation();
                    ctrlCompl.startRecordVideo();
                    //TODO 竞猜按钮隐藏
                    ctrlQuizLayout.setVisibility(View.INVISIBLE);

                } else if (moveControlResponse.getMoveType().name()
                        .equals(MoveType.CATCH.name())) {
                    //TODO 本人点击下爪了 下爪成功
                    Utils.showLogE(TAG, "本人点击下爪成功......");
                    //ctrlQuizLayout.setBackgroundResource(R.drawable.fillingcureency_dialog_gray);//点击下抓，竞猜变色
                    moneyImage.setImageResource(R.drawable.ctrl_unbet_button);
                    ctrlQuizLayout.setEnabled(false);
                }
            }
        } else if (response instanceof String) {
            Utils.showLogE(TAG, "move faile....");
        } else if (response instanceof AppOutRoomResponse) {
            AppOutRoomResponse appOutRoomResponse = (AppOutRoomResponse) response;
            Utils.showLogE(TAG, appOutRoomResponse.toString());
            long seq = appOutRoomResponse.getSeq();
            if (seq == -2) {
                userInfos.remove(appOutRoomResponse.getNickName());
                getUserInfos(userInfos);
            }
        } else if (response instanceof AppInRoomResponse) {
            AppInRoomResponse appInRoomResponse = (AppInRoomResponse) response;
            Utils.showLogE(TAG, "=====" + appInRoomResponse.toString());
            String allUsers = appInRoomResponse.getAllUserInRoom(); //返回的昵称
            Boolean free = appInRoomResponse.getFree();
            setStartMode(free);
            long seq = appInRoomResponse.getSeq();
            if ((seq != -2) && (!Utils.isEmpty(allUsers))) {
                //TODO  我本人进来了
                ctrlCompl.sendGetUserInfos(allUsers);
            } else {
                userInfos.add(appInRoomResponse.getNickName());
                getUserInfos(userInfos);
            }
        }
    }

    //监控网关区
    //TODO 网关重连过后 需要前端主动去获取一次网关的状态来最终判断网关是否存在!!!
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_CONNECT_ERR),
            @Tag(Utils.TAG_CONNECT_SUCESS)})
    public void getConnectStates(String state) {
        if (state.equals(Utils.TAG_CONNECT_ERR)) {
            Utils.showLogE(TAG, "TAG_CONNECT_ERR");
            ctrlStatusIv.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        } else if (state.equals(Utils.TAG_CONNECT_SUCESS)) {
            Utils.showLogE(TAG, "TAG_CONNECT_SUCESS");
            ctrlStatusIv.setImageResource(R.drawable.point_green);
            NettyUtils.sendRoomInCmd();
            //TODO 后续修改获取网关状态接口
            NettyUtils.sendGetDeviceStatesCmd();
            isCurrentConnect = true;
        }
    }

    //监控单个网关连接区
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_DISCONNECT)})
    public void getSingleGatwayDisConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayDisConnect id" + id);
        if (id.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            ctrlStatusIv.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_CONNECT)})
    public void getSingleGatwayConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayConnect id" + id);
        if (id.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            ctrlStatusIv.setImageResource(R.drawable.point_green);
            isCurrentConnect = true;
        }
    }

    //设备故障
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(Utils.TAG_DEVICE_ERR)})
    public void getDeviceErr(Object object) {
        if (object instanceof GatewayPoohStatusMessage) {
            //TODO 主板没有返回数据
            GatewayPoohStatusMessage message = (GatewayPoohStatusMessage) object;
            Utils.showLogE(TAG, "主板没有返回数据" + message.toString());
        } else if (object instanceof PoohAbnormalStatus) {
            //TODO 主板报错
            PoohAbnormalStatus status = (PoohAbnormalStatus) object;
            Utils.showLogE(TAG, "主板报错 错误代码:::" + status.getValue());
            //TODO 主板异常  UI返回用户金额
            if (isStart) {
                //TODO 返回玩家金额
                getUserDate(UserUtils.USER_ID);   //获取用户余额
            } else {
                //TODO 返回竞猜金额 如果用户竞猜
                if (isLottery) {
                    getUserDate(UserUtils.USER_ID);    //获取用户余额
                }
            }
            isStart = false;
            isLottery = false;
        }
    }

    //设备正常返回
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_DEVICE_FREE)})
    public void getDeviceFree(GatewayPoohStatusMessage message) {
        //rechargeButton.setVisibility(View.VISIBLE);
        String roomId = message.getRoomId();
        int number = message.getGifinumber();
        Utils.showLogE(TAG, "getDeviceFree::::::" + roomId + "======" + number);
        if (roomId.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            getStartstation();
            setStartMode(true);
            getUserDate(UserUtils.USER_ID);    //再次获取用户余额并更新UI

//            if (Utils.isEmpty(upTime)) {
//                return;
//            }
            ctrlCompl.stopRecordView(); //录制完毕
            if (isStart) {
                if (number != 0) {
                    //抓到娃娃  上传给后台
                    upFileName = "";
                    state = "1";
                    Utils.showLogE(TAG, "抓取成功！");
                    playBtnMusic(R.raw.catch_success_music);
                    setCatchResultDialog(true);
                } else {
                    //删除本地视频
                    state = "0";
                    Utils.showLogE(TAG, "抓取失败！");
                    playBtnMusic(R.raw.catch_fail_music);
                    setCatchResultDialog(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            boolean d = Utils.delFile(upFileName);
//                            Utils.showLogE(TAG, "没抓住 删除" + upFileName + "视频....." + d);
                            upFileName = "";

                        }
                    }, 2000);  //2s后删除 保证录制完毕
                }
            }
            isStart = false;  //标志复位
            isLottery = false;
            updataTime(upTime, state);
            upTime = "";
        }
    }

    /*************************************************
     * 网络请求区
     ***************************************************/
    //消费接口
//    private void getPlayNum(String userId, String number, String dollId) {
//        HttpManager.getInstance().getUserPlayNum(userId, number, dollId, new RequestSubscriber<Result<LoginInfo>>() {
//            @Override
//            public void _onSuccess(Result<LoginInfo> result) {
//                Log.e(TAG, "消费结果=" + result.getMsg());
//                UserUtils.UserBalance = result.getData().getAppUser().getBALANCE();
//            }
//
//            @Override
//            public void _onError(Throwable e) {
//                MyToast.getToast(getApplicationContext(), "扣费失败！").show();
//            }
//        });
//    }

    //下注接口
    private void getBets(String userID, Integer wager, String guessKey, String guessId,
                         String dollID) {
        HttpManager.getInstance().getBets(userID, wager, guessKey, guessId, dollID, new RequestSubscriber<Result<AppUserBean>>() {
            @Override
            public void _onSuccess(Result<AppUserBean> appUserBeanResult) {
                coinTv.setText("  " + appUserBeanResult.getData().getAppUser().getBALANCE());
                UserUtils.UserBalance = appUserBeanResult.getData().getAppUser().getBALANCE();
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

//    //围观群众获取游戏场次
//    private void getPlayId(String dollId) {
//        HttpManager.getInstance().getPlayId(dollId, new RequestSubscriber<Result<LoginInfo>>() {
//            @Override
//            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
//                //UserUtils.PlayBackId = loginInfoResult.getData().getPlayBack().getID();//游戏场次id
//                getPond(UserUtils.PlayBackId);//获取下注人数
//            }
//
//            @Override
//            public void _onError(Throwable e) {
//
//            }
//        });
//    }

//    //开始游戏分发场次
//    private void getCreatPlayList(String nickName, String dollName) {
//        HttpManager.getInstance().getCreatPlayList(nickName, dollName, new RequestSubscriber<Result<LoginInfo>>() {
//            @Override
//            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
//                //UserUtils.id = loginInfoResult.getData().getPlayBack().getID();
//                //UserUtils.GUESSID = loginInfoResult.getData().getPlayBack().getGUESSID();
//            }
//
//            @Override
//            public void _onError(Throwable e) {
//
//            }
//        });
//    }

    //获取下注人数

    private void getPond(String playId, String dollId) {

        HttpManager.getInstance().getPond(playId, dollId, new RequestSubscriber<Result<PondResponseBean>>() {
            @Override
            public void _onSuccess(Result<PondResponseBean> loginInfoResult) {
                ctrlBettingNumberOne.setText(loginInfoResult.getData().getPond().getGUESS_Y() + "");
                ctrlBettingNumberTwo.setText(loginInfoResult.getData().getPond().getGUESS_N() + "");
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    public void getCtrlUserImage(String name) {
        HttpManager.getInstance().getCtrlUserImage(name, new RequestSubscriber<Result<AppUserBean>>() {
            @Override
            public void _onSuccess(Result<AppUserBean> appUserBeanResult) {
                UserUtils.UserImage1 = UrlUtils.USERFACEIMAGEURL + appUserBeanResult.getData().getAppUser().getIMAGE_URL();
                Glide.with(getApplicationContext()).load(UserUtils.UserImage1)
                        .asBitmap().transform(new GlideCircleTransform(CtrlActivity.this)).into(playerSecondIv);
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    private void updataTime(String time, String state) {

        HttpManager.getInstance().getRegPlayBack(time, UserUtils.NickName, state, dollName, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {

            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    //获取用户信息接口
    private void getUserDate(String userId) {
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<LoginInfo>>() {
            @Override
            public void _onSuccess(Result<LoginInfo> loginInfoResult) {
                Log.e(TAG, "获取结果=" + loginInfoResult.getMsg());
                if (loginInfoResult.getMsg().equals("success")) {
                    coinTv.setText("  " + loginInfoResult.getData().getAppUser().getBALANCE());
                    UserUtils.UserBalance = loginInfoResult.getData().getAppUser().getBALANCE();
                }
            }

            @Override
            public void _onError(Throwable e) {

            }
        });
    }

    private void playBGMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.catchroom_bgm);
        // 设置音频流的类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Utils.showLogE(TAG, "房间背景音乐播放成功"+mediaPlayer.isPlaying());
        //MyToast.getToast(getApplicationContext(),"播放成功"+mediaPlayer.isPlaying()).show();
    }

    private void playBtnMusic(int file) {
        btn_mediaPlayer = MediaPlayer.create(this, file);
        btn_mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        btn_mediaPlayer.start();
    }

    private void setCatchResultDialog(boolean result) {
        CatchDollResultDialog catchDollResultDialog = new CatchDollResultDialog(this, R.style.activitystyle);
        catchDollResultDialog.setCancelable(false);
        catchDollResultDialog.show();
        if (result) {
            catchDollResultDialog.setTitle("恭喜您！");
            catchDollResultDialog.setContent("本次抓娃娃成功啦。");
        } else {
            catchDollResultDialog.setTitle("太可惜了！");
            catchDollResultDialog.setContent("本次抓娃娃失败啦。");
        }
        catchDollResultDialog.setDialogResultListener(new CatchDollResultDialog.DialogResultListener() {
            @Override
            public void getResult(int resultCode) {
                if (resultCode == 1) {
                    MyToast.getToast(getApplicationContext(), "二次抢抓功能还在完善中！").show();
                }
            }
        });


    }


}
