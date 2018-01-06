package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/1/5 23:05
 * 修改人：
 * 修改时间：
 * 类描述：签到实体类
 */
public class SignBean implements Serializable{

    private String CSDATE;
    private String SIGNTIME;
    private String SIGN_TAG;

    public String getSIGN_TAG() {
        return SIGN_TAG;
    }

    public void setSIGN_TAG(String SIGN_TAG) {
        this.SIGN_TAG = SIGN_TAG;
    }

    public String getCSDATE() {
        return CSDATE;
    }

    public void setCSDATE(String CSDATE) {
        this.CSDATE = CSDATE;
    }

    public String getSIGNTIME() {
        return SIGNTIME;
    }

    public void setSIGNTIME(String SIGNTIME) {
        this.SIGNTIME = SIGNTIME;
    }
}
