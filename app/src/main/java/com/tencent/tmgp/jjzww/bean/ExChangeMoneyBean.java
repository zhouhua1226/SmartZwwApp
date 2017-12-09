package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/12/5 17:42
 * 修改人：
 * 修改时间：
 * 类描述：兑换记录实体类
 */
public class ExChangeMoneyBean implements Serializable{

    private int ID;
    private String NUMBER;
    private String USERID;
    private String DOLLNAME;
    private String CREATETIME;
    private String PLAYID;
    private String CONMONEY;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getDOLLNAME() {
        return DOLLNAME;
    }

    public void setDOLLNAME(String DOLLNAME) {
        this.DOLLNAME = DOLLNAME;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getPLAYID() {
        return PLAYID;
    }

    public void setPLAYID(String PLAYID) {
        this.PLAYID = PLAYID;
    }

    public String getCONMONEY() {
        return CONMONEY;
    }

    public void setCONMONEY(String CONMONEY) {
        this.CONMONEY = CONMONEY;
    }
}
