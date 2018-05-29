package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yincong on 2018/1/5 10:18
 * 修改人：
 * 修改时间：
 * 类描述：房间实体类
 */
public class RoomBean implements Serializable{

    private List<CamerasBean> cameras;
    private String cameraName01;
    private String cameraName02;
    private String dollConversiongold;
    private String dollGold;
    private String dollId;
    private String dollName;
    private String dollSn;
    private String dollState;
    private String dollUrl;
    private String roomId;
    private String prob;
    private String reward;
    private String deviceType; //1 娃娃机  2 推币器

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public List<CamerasBean> getCameras() {
        return cameras;
    }

    public void setCameras(List<CamerasBean> cameras) {
        this.cameras = cameras;
    }

    public String getCameraName01() {
        return cameraName01;
    }

    public void setCameraName01(String cameraName01) {
        this.cameraName01 = cameraName01;
    }

    public String getCameraName02() {
        return cameraName02;
    }

    public void setCameraName02(String cameraName02) {
        this.cameraName02 = cameraName02;
    }

    public String getDollConversiongold() {
        return dollConversiongold;
    }

    public void setDollConversiongold(String dollConversiongold) {
        this.dollConversiongold = dollConversiongold;
    }

    public String getDollGold() {
        return dollGold;
    }

    public void setDollGold(String dollGold) {
        this.dollGold = dollGold;
    }

    public String getDollId() {
        return dollId;
    }

    public void setDollId(String dollId) {
        this.dollId = dollId;
    }

    public String getDollName() {
        return dollName;
    }

    public void setDollName(String dollName) {
        this.dollName = dollName;
    }

    public String getDollSn() {
        return dollSn;
    }

    public void setDollSn(String dollSn) {
        this.dollSn = dollSn;
    }

    public String getDollState() {
        return dollState;
    }

    public void setDollState(String dollState) {
        this.dollState = dollState;
    }

    public String getDollUrl() {
        return dollUrl;
    }

    public void setDollUrl(String dollUrl) {
        this.dollUrl = dollUrl;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
