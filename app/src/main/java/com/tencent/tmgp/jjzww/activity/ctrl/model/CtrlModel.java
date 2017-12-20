package com.tencent.tmgp.jjzww.activity.ctrl.model;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.SurfaceView;

import com.daniulive.smartplayer.SmartPlayerJni;
import com.eventhandle.SmartEventCallback;
import com.gatz.netty.utils.NettyUtils;
import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.tencent.tmgp.jjzww.utils.EZUtils;
import com.tencent.tmgp.jjzww.utils.Utils;


/**
 * Created by zhouh on 2017/9/7.
 */
public class CtrlModel implements SmartEventCallback {
    private static final String TAG = "CtrlModel-";

    private Context ctx;
    private DeviceCallBack callBack;
    private final long countTime = 20 * 1000;
    private final long seconds = 1000;
    private boolean isRecoding = false;
    private SmartPlayerJni smartPlayer;
    private long playerHandle = 0;
    private boolean isChangerUrl = false;

    static {
        System.loadLibrary("SmartPlayer");
    }

    //定时器区
    private CountDownTimer countDownTimer = new CountDownTimer(countTime, seconds) {
        @Override
        public void onTick(long l) {
            callBack.getClickTime((int) (l / seconds));
        }

        @Override
        public void onFinish() {
            callBack.getClickFinish();
        }
    };

    public CtrlModel(Context context, DeviceCallBack deviceCallBack) {
        this.ctx = context;
        this.callBack = deviceCallBack;
        smartPlayer = new SmartPlayerJni();
    }

    public void sendCmd(MoveType moveType) {
        NettyUtils.sendCtrlCmd(moveType);
    }

    public void sendCmdOutRoom() {
        NettyUtils.sendRoomOutCmd();
    }

    public void sendTimeStart() {
        countDownTimer.start();
    }

    public void sendTimeCancle() {
        countDownTimer.cancel();
    }

    /**
     * 保存视频到本地
     */
    public void sendStartSecordVideo() {

    }

    /**
     * 关闭视频录制
     */
    public void stopRecordVideo() {

    }

    public void stopPlayer() {
        if (playerHandle == 0) {
            callBack.getVideoPlayErr(EZUtils.PLAYER_PLAYHANDLER_ZERO);
            return;
        }
        smartPlayer.SmartPlayerClose(playerHandle);
        playerHandle = 0;
    }

    /**
     * 直播
     * @param surfaceView
     * @param url
     */
    public void startPlayer(SurfaceView surfaceView, String url) {
        playerHandle = smartPlayer.SmartPlayerInit(ctx);
        if (playerHandle == 0) {
            callBack.getVideoPlayErr(EZUtils.PLAYER_PLAYHANDLER_ZERO);
            return;
        }
        smartPlayer.SetSmartPlayerEventCallback(playerHandle, this);
        smartPlayer.SmartPlayerSetSurface(playerHandle, surfaceView);
        smartPlayer.SmartPlayerSetFastStartup(playerHandle, 1);
        smartPlayer.SmartPlayerSetLowLatencyMode(playerHandle, 1);
        smartPlayer.SmartPlayerSetBuffer(playerHandle, 0);
        //int hwChecking = smartPlayer.SetSmartPlayerVideoHWDecoder(playerHandle, 0);  //娃娃机一定需要软解码
        int iPlaybackRet = smartPlayer.SmartPlayerStartPlayback(playerHandle, url);
        Utils.showLogE(TAG, "当前播放url:::::::" + iPlaybackRet + "===========" + url);
        if (iPlaybackRet != 0) {
            callBack.getVideoPlayErr(EZUtils.PLAYER_PLAYBACKRET_ZERO);
        }
    }

    public void startPlaySwitchUrl(String url) {
        if (playerHandle != 0) {
            isChangerUrl = true;
            smartPlayer.SmartPlayerSwitchPlaybackUrl(playerHandle, url);
        } else {
            callBack.getVideoPlayErr(EZUtils.PLAYER_ERC_SWITCH_URL_ERR);
        }
    }

    @Override
    public void onCallback(int i, long l, long l1, String s, String s1, Object o) {
        Utils.showLogE(TAG, "播放回调code::::::" + i + "=====" + isChangerUrl);
        switch (i) {
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_STARTED: //开始直播
                callBack.getVideoPlayStart();
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_CONNECTING: //连接中
                callBack.getVideoPlayConnect();
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_CONNECTION_FAILED: //直播失败
                callBack.getVideoPlayErr(EZUtils.PLAYER_ERC_PLAYERR);
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_CONNECTED: //直播成功
                callBack.getVideoPlaySucess();
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_DISCONNECTED: //连接断开
                if (!isChangerUrl) {
                    callBack.getVideoPlayErr(EZUtils.PLAYER_ERC_DISCONNECTED);
                }
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_STOP: //停止
                callBack.getVideoStop();
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_NO_MEDIADATA_RECEIVED:  //收不到数据 但是不影响播放
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_SWITCH_URL: //切换URL
                break;
            case EVENTID.EVENT_DANIULIVE_ERC_PLAYER_RESOLUTION_INFO: //收到分辨率
                callBack.getVideoPlaySucess();
                break;
        }
        isChangerUrl = false;
    }
}
