package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/12/6 16:54
 * 修改人：
 * 修改时间：
 * 类描述：投注记录实体类
 */
public class BetRecordBean implements Serializable{

    private String PLAYID;
    private String BETMONEY;
    private String DOLLNAME;
    private String BETTIME;
    private String BETRESULT;

    public String getPLAYID() {
        return PLAYID;
    }

    public void setPLAYID(String PLAYID) {
        this.PLAYID = PLAYID;
    }

    public String getBETMONEY() {
        return BETMONEY;
    }

    public void setBETMONEY(String BETMONEY) {
        this.BETMONEY = BETMONEY;
    }

    public String getDOLLNAME() {
        return DOLLNAME;
    }

    public void setDOLLNAME(String DOLLNAME) {
        this.DOLLNAME = DOLLNAME;
    }

    public String getBETTIME() {
        return BETTIME;
    }

    public void setBETTIME(String BETTIME) {
        this.BETTIME = BETTIME;
    }

    public String getBETRESULT() {
        return BETRESULT;
    }

    public void setBETRESULT(String BETRESULT) {
        this.BETRESULT = BETRESULT;
    }
}
