package com.tencent.tmgp.jjzww.activity.ctrl.presenter;

import android.content.Context;
import android.view.SurfaceView;

import com.iot.game.pooh.server.entity.json.enums.MoveType;
import com.tencent.tmgp.jjzww.activity.ctrl.model.CtrlModel;
import com.tencent.tmgp.jjzww.activity.ctrl.model.DeviceCallBack;
import com.tencent.tmgp.jjzww.activity.ctrl.view.IctrlView;
import com.tencent.tmgp.jjzww.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouh on 2017/9/7.
 */
public class CtrlCompl implements BasePresenter, ICtrlPresenter {

    private IctrlView ictrlView;
    private Context ctx;
    private CtrlModel ctrlModel;
    private DeviceCallBack callBack = new DeviceCallBack() {

        @Override
        public void getClickTime(int time) {
            ictrlView.getTime(time);
        }

        @Override
        public void getClickFinish() {
            ictrlView.getTimeFinish();
        }

        @Override
        public void getVideoRecordErr(int errCode) {
            ictrlView.getRecordErrCode(errCode);
        }

        @Override
        public void getVideoSucess() {
            ictrlView.getRecordSuecss();
        }

        @Override
        public void getVideoAttributetoNet(String time, String fileName) {
            ictrlView.getRecordAttributetoNet(time, fileName);
        }

        @Override
        public void getVideoPlayErr(int code) {
            ictrlView.getPlayerErcErrCode(code);
        }

        @Override
        public void getVideoPlayConnect() {
            ictrlView.getVideoPlayConnect();
        }

        @Override
        public void getVideoPlayStart() {
            ictrlView.getVideoPlayStart();
        }

        @Override
        public void getVideoPlaySucess() {
            ictrlView.getPlayerSucess();
        }

        @Override
        public void getVideoStop() {
            ictrlView.getVideoStop();
        }
    };

    @Override
    public void startLoginPresent() {

    }

    @Override
    public void destoryLoginPresent() {

    }

    public CtrlCompl(Context context) {
        ctx = context;
        ctrlModel = new CtrlModel(context, callBack);
    }

    public CtrlCompl(IctrlView ctrlView, Context context) {
        ictrlView = ctrlView;
        ctx = context;
        ctrlModel = new CtrlModel(context, callBack);
    }

    @Override
    public void sendCmdCtrl(MoveType moveType) {
        ctrlModel.sendCmd(moveType);
    }

    @Override
    public void startTimeCounter() {
        ctrlModel.sendTimeStart();
    }

    @Override
    public void stopTimeCounter() {
        ctrlModel.sendTimeCancle();
    }

    @Override
    public void sendCmdOutRoom() {
        ctrlModel.sendCmdOutRoom();
    }

    @Override
    public void sendGetUserInfos(String o, boolean isMe) {
        List<String> list = new ArrayList<>();
        String[] os = o.split(";");
        for(int i = 0; i < os.length; i++) {
            list.add(os[i]);
        }
        ictrlView.getUserInfos(list, isMe);
    }

    @Override
    public void startRecordVideo() {
        ctrlModel.sendStartSecordVideo();
    }

    @Override
    public void stopRecordView() {
        ctrlModel.stopRecordVideo();
    }

    @Override
    public void startPlayVideo(SurfaceView view, String url) {
        ctrlModel.startPlayer(view, url);
    }

    @Override
    public void stopPlayVideo() {
        ctrlModel.stopPlayer();
    }

    @Override
    public void startPlaySwitchUrlVideo(String url) {
        ctrlModel.startPlaySwitchUrl(url);
    }
}
