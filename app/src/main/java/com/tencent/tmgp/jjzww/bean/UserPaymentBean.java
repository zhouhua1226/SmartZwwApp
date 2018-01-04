package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/1/4 11:21
 * 修改人：
 * 修改时间：
 * 类描述：用户金币流水实体类
 */
public class UserPaymentBean implements Serializable{

    private String DOLLID;
    private String GOLD;
    private String COST_TYPE;
    private String USERID;
    private String CREATE_TIME;
    private int ID;
    private String UPDATE_TIME;
    private String dollname;
    private String REMARK;

    public String getDOLLID() {
        return DOLLID;
    }

    public void setDOLLID(String DOLLID) {
        this.DOLLID = DOLLID;
    }

    public String getGOLD() {
        return GOLD;
    }

    public void setGOLD(String GOLD) {
        this.GOLD = GOLD;
    }

    public String getCOST_TYPE() {
        return COST_TYPE;
    }

    public void setCOST_TYPE(String COST_TYPE) {
        this.COST_TYPE = COST_TYPE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public String getDollname() {
        return dollname;
    }

    public void setDollname(String dollname) {
        this.dollname = dollname;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
