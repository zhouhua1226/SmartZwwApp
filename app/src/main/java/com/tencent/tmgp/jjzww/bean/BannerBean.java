package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/1/11 11:37
 * 修改人：
 * 修改时间：
 * 类描述：轮播实体类
 */
public class BannerBean implements Serializable {

    private String RUN_NAME;
    private String TIME;
    private String CONTENT;
    private String RUNIMAGE_ID;
    private String IMAGE_URL;
    private String HREF_ST;

    private String STATE;
    private String LIVESTREAM;
    private String RTMP_URL;
    private String SERVER_NAME;
    private String DEVICE_STATE;

    public String getLIVESTREAM() {
        return LIVESTREAM;
    }

    public void setLIVESTREAM(String LIVESTREAM) {
        this.LIVESTREAM = LIVESTREAM;
    }

    public String getRTMP_URL() {
        return RTMP_URL;
    }

    public void setRTMP_URL(String RTMP_URL) {
        this.RTMP_URL = RTMP_URL;
    }

    public String getSERVER_NAME() {
        return SERVER_NAME;
    }

    public void setSERVER_NAME(String SERVER_NAME) {
        this.SERVER_NAME = SERVER_NAME;
    }

    public String getDEVICE_STATE() {
        return DEVICE_STATE;
    }

    public void setDEVICE_STATE(String DEVICE_STATE) {
        this.DEVICE_STATE = DEVICE_STATE;
    }


    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getHREF_ST() {
        return HREF_ST;
    }

    public void setHREF_ST(String HREF_ST) {
        this.HREF_ST = HREF_ST;
    }

    public String getRUN_NAME() {
        return RUN_NAME;
    }

    public void setRUN_NAME(String RUN_NAME) {
        this.RUN_NAME = RUN_NAME;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getRUNIMAGE_ID() {
        return RUNIMAGE_ID;
    }

    public void setRUNIMAGE_ID(String RUNIMAGE_ID) {
        this.RUNIMAGE_ID = RUNIMAGE_ID;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }
}
