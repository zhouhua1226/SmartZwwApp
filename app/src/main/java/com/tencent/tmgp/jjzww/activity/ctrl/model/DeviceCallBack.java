package com.tencent.tmgp.jjzww.activity.ctrl.model;


/**
 * Created by dell on 2017/9/8.
 */
public interface DeviceCallBack {

    void getClickTime(int time);
    void getClickFinish();
    //视频回放区
    void getVideoRecordErr(int errCode);
    void getVideoSucess();
    void getVideoAttributetoNet(String time, String fileName);
    //视频播放回调区
    void getVideoPlayErr(int code);
    void getVideoPlayConnect();
    void getVideoPlayStart();
    void getVideoPlaySucess();
    void getVideoStop();
}
