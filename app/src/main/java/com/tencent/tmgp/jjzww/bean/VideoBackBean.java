package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/11/25 12:35
 * 修改人：
 * 修改时间：
 * 类描述：
 */
public class VideoBackBean implements Serializable{

    private String DOLLNAME;
    private String STATE;
    private String USERNAME;
    private int ID;
    private String CREATETIME;
    private String DOLL_URL;
    private int DOLLTOTAL;
    private int CONVERSIONGOLD;
    private String POSTSTATE;
    private String COUNT;
    private String SENDGOODS;
    private String IMAGE_URL;

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public String getSENDGOODS() {
        return SENDGOODS;
    }

    public void setSENDGOODS(String SENDGOODS) {
        this.SENDGOODS = SENDGOODS;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }

    public int getDOLLTOTAL() {
        return DOLLTOTAL;
    }

    public void setDOLLTOTAL(int DOLLTOTAL) {
        this.DOLLTOTAL = DOLLTOTAL;
    }

    public int getCONVERSIONGOLD() {
        return CONVERSIONGOLD;
    }

    public void setCONVERSIONGOLD(int CONVERSIONGOLD) {
        this.CONVERSIONGOLD = CONVERSIONGOLD;
    }

    public String getPOSTSTATE() {
        return POSTSTATE;
    }

    public void setPOSTSTATE(String POSTSTATE) {
        this.POSTSTATE = POSTSTATE;
    }

    public String getDOLLNAME() {
        return DOLLNAME;
    }

    public void setDOLLNAME(String DOLLNAME) {
        this.DOLLNAME = DOLLNAME;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getDOLL_URL() {
        return DOLL_URL;
    }

    public void setDOLL_URL(String DOLL_URL) {
        this.DOLL_URL = DOLL_URL;
    }
}
