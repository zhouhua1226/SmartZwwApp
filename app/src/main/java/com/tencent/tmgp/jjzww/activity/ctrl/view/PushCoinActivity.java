package com.tencent.tmgp.jjzww.activity.ctrl.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.iot.game.pooh.server.entity.json.CoinControlResponse;
import com.tencent.tmgp.jjzww.R;
import com.tencent.tmgp.jjzww.activity.ctrl.presenter.CtrlCompl;
import com.tencent.tmgp.jjzww.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushCoinActivity extends Activity implements IctrlView{
    private static final String TAG = "PushCoinActivity";

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
    ImageView coinPushBtn;
    @BindView(R.id.coin_response_text)
    TextView coinResponseText;

    @BindView(R.id.coin_play_video_sv)
    SurfaceView mPlaySv;

    private boolean isStartSend = false;
    private String currentUrl;
    private String playUrlMain;
    private String playUrlSecond;
    private CtrlCompl ctrlCompl;
    private int coinNumber = 0;

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
        NettyUtils.pingRequest(); //判断连接
    }

    private void initData() {
        playUrlMain = getIntent().getStringExtra(Utils.TAG_URL_MASTER);
        playUrlSecond = getIntent().getStringExtra(Utils.TAG_URL_SECOND);
        ctrlCompl = new CtrlCompl(this, this);
        currentUrl = playUrlMain;
        ctrlCompl.startPlayVideo(mPlaySv, currentUrl);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NettyUtils.pingRequest(); //判断连接
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        ctrlCompl.stopRecordView();
        NettyUtils.sendRoomOutCmd();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.coin_push_btn})
    public void onPushClick(View v) {
        switch (v.getId()) {
            case R.id.coin_push_btn:
                if (coinNumber != 0) {
                    NettyUtils.sendPushCoinCmd(NettyUtils.USER_PUSH_COIN_PLAY, coinNumber);
                    coinPushBtn.setImageResource(R.drawable.coin_push_ing);
                    setBtnEnabled(false);
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.coin_button1, R.id.coin_button10, R.id.coin_button5,
            R.id.coin_button20, R.id.coin_button50})
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
        coinPushBtn.setImageResource(R.drawable.coin_push);
    }

    private void setBtnEnabled(boolean isEnabled) {
        coinBtn1.setEnabled(isEnabled);
        coinBtn5.setEnabled(isEnabled);
        coinBtn10.setEnabled(isEnabled);
        coinBtn20.setEnabled(isEnabled);
        coinBtn50.setEnabled(isEnabled);
        coinPushBtn.setEnabled(isEnabled);
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
            }
            setCoinNormal();
            setBtnEnabled(true);
            isStartSend = false;
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_COIN_DEVICE_STATE)})
    public void getCoinDeviceState(String state) {
        if (state.equals("cbusy")) {
            setBtnEnabled(false);
        } else if (state.equals("cfree")) {
            setBtnEnabled(true);
            setCoinNormal();
            isStartSend = false;
        }
    }

    @Override
    public void getTime(int time) {

    }

    @Override
    public void getTimeFinish() {

    }

    @Override
    public void getUserInfos(List<String> list, boolean is) {

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

    }

    @Override
    public void getPlayerSucess() {

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
}
