package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/12/17 12:16
 * 修改人：
 * 修改时间：
 * 类描述：YSDK订单类
 */
public class OrderBean implements Serializable{

    private String STATUS;
    private String REC_ID;
    private String ORDER_ID;   //订单号
    private String REGAMOUNT;  //金额
    private String USER_ID;    //用户id(uid)
    private String CREATETIME;   //时间

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getREC_ID() {
        return REC_ID;
    }

    public void setREC_ID(String REC_ID) {
        this.REC_ID = REC_ID;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getREGAMOUNT() {
        return REGAMOUNT;
    }

    public void setREGAMOUNT(String REGAMOUNT) {
        this.REGAMOUNT = REGAMOUNT;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }
}
