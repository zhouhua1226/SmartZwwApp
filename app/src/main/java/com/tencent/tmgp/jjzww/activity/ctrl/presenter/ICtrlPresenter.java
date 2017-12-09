package com.tencent.tmgp.jjzww.activity.ctrl.presenter;

import android.view.SurfaceView;

import com.iot.game.pooh.server.entity.json.enums.MoveType;

/**
 * Created by zhouh on 2017/9/7.
 */
public interface ICtrlPresenter {
    void sendCmdCtrl(MoveType moveType);
    void startTimeCounter();
    void stopTimeCounter();
    void sendCmdOutRoom();
    void sendGetUserInfos(String o);

    void startRecordVideo();
    void stopRecordView();

    void startPlayVideo(SurfaceView view, String url);
    void stopPlayVideo();
    void startPlaySwitchUrlVideo(String url);
}
