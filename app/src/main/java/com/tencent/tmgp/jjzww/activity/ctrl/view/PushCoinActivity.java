package com.tencent.tmgp.jjzww.activity.ctrl.view;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.global.ConnectResultEvent;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.iot.game.pooh.server.entity.json.CoinControlResponse;
import com.iot.game.pooh.server.entity.json.app.AppInRoomResponse;
import com.iot.game.pooh.server.entity.json.app.AppOutRoomResponse;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.ctrl.presenter.CtrlCompl;
import com.tencent.tmgp.jjzww.activity.home.CoinRecordActivity;
import com.tencent.tmgp.jjzww.activity.wechat.WeChatPayActivity;
import com.tencent.tmgp.jjzww.bean.HttpDataInfo;
import com.tencent.tmgp.jjzww.bean.Result;
import com.tencent.tmgp.jjzww.bean.UserBean;
import com.tencent.tmgp.jjzww.model.http.HttpManager;
import com.tencent.tmgp.jjzww.model.http.RequestSubscriber;
import com.tencent.tmgp.jjzww.utils.UrlUtils;
import com.tencent.tmgp.jjzww.utils.UserUtils;
import com.tencent.tmgp.jjzww.utils.Utils;
import com.tencent.tmgp.jjzww.view.GifView;
import com.tencent.tmgp.jjzww.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushCoinActivity extends Activity implements IctrlView{
    private static final String TAG = "PushCoinActivity";

    @BindView(R.id.player_counter_tv)
    TextView player_counter;//在线人数
    @BindView(R.id.player2_iv)
    ImageView player2_iv;//在线人头像
    @BindView(R.id.player_name_tv)
    TextView player_name;//玩家
    @BindView(R.id.main_player_iv)
    ImageView player_iv;//玩家头像
    @BindView(R.id.ctrl_status_iv)
    ImageView ctrl_status;//链接状态

    @BindView(R.id.coin_recharge)
    TextView coin_recharge;//充值
    @BindView(R.id.ctrl_comerecord_tv)
    TextView comerecord;//投币记录
     @BindView(R.id.ctrl_gif_view)
     GifView ctrlGifView;
     @BindView(R.id.ctrl_fail_iv)
     ImageView ctrlFailIv;

    @BindView(R.id.coin_button1)
    ImageView coinBtn1;
    @BindView(R.id.coin_button5)
    ImageView coinBtn5;
    @BindView(R.id.coin_button10)
    ImageView coinBtn10;
    @BindView(R.id.coin_button20)
    ImageView coinBtn20;
    @BindView(R.id.coin_button50)
    ImageView coinBtn50;
    @BindView(R.id.coin_push_btn)
    TextView coinPushBtn;
    @BindView(R.id.coin_response_text)
    TextView coinResponseText;

    @BindView(R.id.coin_play_video_sv)
    SurfaceView mPlaySv;

    private boolean isStartSend = false;
    private boolean isCurrentConnect = true;
    private String currentUrl;
    private String playUrlMain;
    private String playUrlSecond;
    private CtrlCompl ctrlCompl;
    private int coinNumber = 0;
   // private String dollName;
  //  private String dollId;
    private String showUserId = "";
    private List<String> userInfos = new ArrayList<>();  //房屋内用户电话信息
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_coin);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        RxBus.get().register(this);
        NettyUtils.sendRoomInCmd();
        ctrlGifView.setVisibility(View.VISIBLE);
        ctrlGifView.setEnabled(false);
        ctrlGifView.setMovieResource(R.raw.ctrl_video_loading);
        ctrlFailIv.setVisibility(View.GONE);
        if (Utils.connectStatus.equals(ConnectResultEvent.CONNECT_FAILURE)) {
            ctrl_status.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        } else {
            ctrl_status.setImageResource(R.drawable.point_green);
        }
        Glide.with(this).load(UserUtils.UserImage).asBitmap().
                transform(new GlideCircleTransform(this)).into(player_iv);
        player_name.setText(UserUtils.NickName + "...");
        NettyUtils.pingRequest(); //判断连接
        getUserDate(UserUtils.USER_ID);

    }

    private void initData() {
       // dollName = getIntent().getStringExtra(Utils.TAG_ROOM_NAME);
       // dollId = getIntent().getStringExtra(Utils.TAG_DOLL_Id);
        playUrlMain = getIntent().getStringExtra(Utils.TAG_URL_MASTER);
        playUrlSecond = getIntent().getStringExtra(Utils.TAG_URL_SECOND);
        ctrlCompl = new CtrlCompl(this, this);
        currentUrl = playUrlMain;
        ctrlCompl.startPlayVideo(mPlaySv, currentUrl);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.showLogE(TAG, "onRestart");
        if (ctrlFailIv.getVisibility() == View.VISIBLE) {
            ctrlFailIv.setVisibility(View.GONE);
        }
        ctrlGifView.setVisibility(View.VISIBLE);
        ctrlCompl.startPlayVideo(mPlaySv, currentUrl);
        NettyUtils.pingRequest();
        if (!Utils.isEmpty(UserUtils.USER_ID)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getUserDate(UserUtils.USER_ID);    //2秒后获取用户余额并更新UI
                }
            }, 2000);

        }

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        ctrlCompl.stopRecordView();
        NettyUtils.sendRoomOutCmd();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        ctrlCompl.stopPlayVideo();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void playBGMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.coin_down);
        // 设置音频流的类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @OnClick({R.id.coin_button1, R.id.coin_button10, R.id.coin_button5,
            R.id.coin_button20, R.id.coin_button50,R.id.ctrl_back_imag,
            R.id.coin_recharge, R.id.ctrl_comerecord_tv,R.id.ctrl_change_camera_iv,
            R.id.ctrl_fail_iv})
    public void onViewClicked(View view){
        setCoinNormal();
        switch (view.getId()) {
            case R.id.coin_button1:
                coinBtn1.setImageResource(R.drawable.coin_1_s);
                coinNumber = 1;
                break;
            case R.id.coin_button5:
                coinBtn5.setImageResource(R.drawable.coin_5_s);
                coinNumber = 5;
                break;
            case R.id.coin_button10:
                coinBtn10.setImageResource(R.drawable.coin_10_s);
                coinNumber = 10;
                break;
            case R.id.coin_button20:
                coinBtn20.setImageResource(R.drawable.coin_20_s);
                coinNumber = 20;
                break;
            case R.id.coin_button50:
                coinBtn50.setImageResource(R.drawable.coin_50_s);
                coinNumber = 50;
                break;
            case R.id.ctrl_back_imag:
                finish();
                break;

            case R.id.ctrl_comerecord_tv://投币记录
                startActivity(new Intent(this, CoinRecordActivity.class));
                break;

            case R.id.coin_recharge://充值
                startActivity(new Intent(this, WeChatPayActivity.class));
                break;
            case R.id.ctrl_change_camera_iv:
                currentUrl = currentUrl.equals(playUrlMain) ? playUrlSecond : playUrlMain;
                ctrlCompl.startPlaySwitchUrlVideo(currentUrl);
                break;
            case R.id.ctrl_fail_iv:
                ctrlFailIv.setVisibility(View.GONE);
                ctrlGifView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        if (!isStartSend) {
            Utils.showLogE(TAG, "isStartSendisStartSend");
            NettyUtils.sendPushCoinCmd(NettyUtils.USER_PUSH_COIN_START, 0);
            coinResponseText.setText(String.valueOf(0));
            isStartSend = true;
        }
    }

    private void setCoinNormal() {
        coinBtn1.setImageResource(R.drawable.coin_1_n);
        coinBtn5.setImageResource(R.drawable.coin_5_n);
        coinBtn10.setImageResource(R.drawable.coin_10_n);
        coinBtn20.setImageResource(R.drawable.coin_20_n);
        coinBtn50.setImageResource(R.drawable.coin_50_n);
        coinPushBtn.setText("投 币");
    }

    private void setBtnEnabled(boolean isEnabled) {
        coinBtn1.setEnabled(isEnabled);
        coinBtn5.setEnabled(isEnabled);
        coinBtn10.setEnabled(isEnabled);
        coinBtn20.setEnabled(isEnabled);
        coinBtn50.setEnabled(isEnabled);
        coinPushBtn.setEnabled(isEnabled);
        if(isEnabled){
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }else{
            playBGMusic();
        }
    }
    @OnClick({R.id.coin_push_btn})
    public void onPushClick(View v) {
        switch (v.getId()) {
            case R.id.coin_push_btn:
                if (coinNumber != 0&&isCurrentConnect) {
                    NettyUtils.sendPushCoinCmd(NettyUtils.USER_PUSH_COIN_PLAY, coinNumber);
                    coinPushBtn.setText("投币中");
                    setBtnEnabled(false);
                    getUserDate(UserUtils.USER_ID);
                }
                break;
            default:
                break;
        }
    }
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_ROOM_IN),
            @Tag(Utils.TAG_ROOM_OUT),
            @Tag(Utils.TAG_COIN_RESPONSE)})
    public void getCoinDeviceResponse(Object response) {
        if (response instanceof CoinControlResponse) {
            CoinControlResponse coinControlResponse = (CoinControlResponse) response;
            if ((coinControlResponse.getBet() != null) && (coinControlResponse.getBingo() != null)) {
                int bingo = coinControlResponse.getBingo();
                coinResponseText.setText(String.valueOf(bingo));
                getUserDate(UserUtils.USER_ID);    //再次获取用户余额并更新UI
            }

            setCoinNormal();
            setBtnEnabled(true);
            isStartSend = false;
        }else if(response instanceof AppInRoomResponse)//进入房间
        {
            AppInRoomResponse appInRoomResponse = (AppInRoomResponse) response;
            Utils.showLogE(TAG, "=====" + appInRoomResponse.toString());
            String allUsers = appInRoomResponse.getAllUserInRoom(); //返回的UserId
            Boolean free = appInRoomResponse.getFree();
            long seq = appInRoomResponse.getSeq();
            if ((seq != -2) && (!Utils.isEmpty(allUsers))) {
                //TODO  我本人进来了
                ctrlCompl.sendGetUserInfos(allUsers, true);
                //是否能点击开始
                if (free) {
                    setCoinNormal();
                    coinPushBtn.setText("投 币");
                    setBtnEnabled(true);
                    isStartSend=false;
                } else {
                    coinPushBtn.setText("投币中");
                    setBtnEnabled(false);
                    isStartSend=true;
                }
            } else {
                boolean is = false;
                if (userInfos.size() == 1) {
                    is = true;
                }
                userInfos.add(appInRoomResponse.getUserId());
                getUserInfos(userInfos, is);
            }
        }else if(response instanceof AppOutRoomResponse)//退出房间
        {
            AppOutRoomResponse appOutRoomResponse = (AppOutRoomResponse) response;
            Utils.showLogE(TAG, appOutRoomResponse.toString());
            long seq = appOutRoomResponse.getSeq();
            if (seq == -2) {
                userInfos.remove(appOutRoomResponse.getUserId());
                if (appOutRoomResponse.getUserId().equals(showUserId)) {
                    getUserInfos(userInfos, true);
                } else {
                    getUserInfos(userInfos, false);
                }
            }

        }
    }

    //开始游戏
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_COIN_DEVICE_STATE)})
    public void getCoinDeviceState(String state) {
        if (state.equals("cbusy")) { //游戏中
            setBtnEnabled(false);
        } else if (state.equals("cfree")) {//休闲中
            setBtnEnabled(true);
            setCoinNormal();
            isStartSend = false;
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
            ctrl_status.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        } else if (state.equals(Utils.TAG_CONNECT_SUCESS)) {
            Utils.showLogE(TAG, "TAG_CONNECT_SUCESS");
            ctrl_status.setImageResource(R.drawable.point_green);
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
            ctrl_status.setImageResource(R.drawable.point_red);
            isCurrentConnect = false;
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_GATEWAY_SINGLE_CONNECT)})
    public void getSingleGatwayConnect(String id) {
        Utils.showLogE(TAG, "getSingleGatwayConnect id" + id);
        if (id.equals(AppGlobal.getInstance().getUserInfo().getRoomid())) {
            ctrl_status.setImageResource(R.drawable.point_green);
            isCurrentConnect = true;
        }
    }


    @Override
    public void getTime(int time) {

    }

    @Override
    public void getTimeFinish() {

    }

    @Override
    public void getRecordErrCode(int code) {

    }

    @Override
    public void getRecordSuecss() {

    }

    @Override
    public void getRecordAttributetoNet(String time, String name) {

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
    public void getUserInfos(List<String> list, boolean is) {
        //当前房屋的人数
        userInfos = list;
        int counter = userInfos.size();
        Utils.showLogE(TAG, "当前房屋的人数::::" + counter);
        if (counter > 0) {
            String s = counter + "人在线";
            player_counter.setText(s);
            if (counter == 1) {
                //显示自己
                Glide.with(this).load(UserUtils.UserImage).asBitmap().
                        transform(new GlideCircleTransform(this)).into(player2_iv);
            } else {
                if (is) {
                    //显示另外一个人
                    for (int i = 0; i < counter; i++) {
                        if (!userInfos.get(i).equals(UserUtils.USER_ID)) {
                            showUserId = userInfos.get(i);
                            Utils.showLogE(TAG, "显示观察者的userId::::" + showUserId);
                            getCtrlUserImage(showUserId);
                            break;
                        }
                    }
                }
            }
        }
    }
    public void getCtrlUserImage(String userId) {
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> httpDataInfoResult) {
                if (httpDataInfoResult.getCode().equals("0")) {
                    UserBean bean = httpDataInfoResult.getData().getAppUser();
                    if ((bean != null) && (!Utils.isEmpty(bean.getIMAGE_URL()))) {
                        String showImage = UrlUtils.USERFACEIMAGEURL + bean.getIMAGE_URL();
                        Glide.with(getApplicationContext()).load(showImage)
                                .asBitmap().transform(new GlideCircleTransform(PushCoinActivity.this)).into(player2_iv);
                    } else {
                        Glide.with(getApplicationContext()).load(R.drawable.ctrl_default_user_bg)
                                .asBitmap().transform(new GlideCircleTransform(PushCoinActivity.this)).into(player2_iv);
                    }
                } else {
                    Glide.with(getApplicationContext()).load(R.drawable.ctrl_default_user_bg)
                            .asBitmap().transform(new GlideCircleTransform(PushCoinActivity.this)).into(player2_iv);
                }
            }

            @Override
            public void _onError(Throwable e) {
                Glide.with(getApplicationContext()).load(R.drawable.ctrl_default_user_bg)
                        .asBitmap().transform(new GlideCircleTransform(PushCoinActivity.this)).into(player2_iv);
            }
        });
    }
    //获取用户信息接口
    private void getUserDate(String userId) {
        HttpManager.getInstance().getUserDate(userId, new RequestSubscriber<Result<HttpDataInfo>>() {
            @Override
            public void _onSuccess(Result<HttpDataInfo> loginInfoResult) {
                Log.e(TAG, "获取结果=" + loginInfoResult.getMsg());
                if (loginInfoResult.getMsg().equals("success")) {
                    UserBean bean = loginInfoResult.getData().getAppUser();
                    if (bean != null) {
                        String balance = bean.getBALANCE();
                        //UserUtils.UserBetNum = bean.getBET_NUM();
                        if (!TextUtils.isEmpty(balance)) {
                            UserUtils.UserBalance = balance;
                            coin_recharge.setText("  " + UserUtils.UserBalance + " 充值");

                        }
//                        String showImage = UrlUtils.USERFACEIMAGEURL + bean.getIMAGE_URL();
//                        Glide.with(getApplicationContext()).load(showImage)
//                                .asBitmap().transform(new GlideCircleTransform(CtrlActivity.this)).into(playerSecondIv);
                    }
                }
            }

            @Override
            public void _onError(Throwable e) {
            }
        });
    }

}
