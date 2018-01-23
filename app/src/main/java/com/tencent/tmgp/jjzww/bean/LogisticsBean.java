package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/1/18 13:58
 * 修改人：
 * 修改时间：
 * 类描述：物流订单实体类
 */
public class LogisticsBean implements Serializable{

    private String MODE_DESPATCH;   //发货模式 0:包邮 1：金币抵扣 2：货到付款
    private String USER_ID;
    private String CNEE_PHONE;      //收货人手机号
    private String FMS_NAME;        //物流名称
    private String CNEE_NAME;       //收货人姓名
    private String FMS_ORDER_NO;    //物流编号
    private String SENDBOOLEAN;     //物流状态   //0：待发货   1：已发货
    private String FMS_TIME;        //发货时间
    private String GOODS_NUM;       //数量
    private String CREATE_TIME;     //订单创建日期
    private String ID;               //订单ID
    private String CNEE_ADDRESS;    //地址
    private String POST_REMARK;     //发货明细
    private String REMARK;          //备注
    private String SEND_NUM_ID;     //订单号

    public String getSEND_NUM_ID() {
        return SEND_NUM_ID;
    }

    public void setSEND_NUM_ID(String SEND_NUM_ID) {
        this.SEND_NUM_ID = SEND_NUM_ID;
    }

    public String getMODE_DESPATCH() {
        return MODE_DESPATCH;
    }

    public void setMODE_DESPATCH(String MODE_DESPATCH) {
        this.MODE_DESPATCH = MODE_DESPATCH;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCNEE_PHONE() {
        return CNEE_PHONE;
    }

    public void setCNEE_PHONE(String CNEE_PHONE) {
        this.CNEE_PHONE = CNEE_PHONE;
    }

    public String getFMS_NAME() {
        return FMS_NAME;
    }

    public void setFMS_NAME(String FMS_NAME) {
        this.FMS_NAME = FMS_NAME;
    }

    public String getCNEE_NAME() {
        return CNEE_NAME;
    }

    public void setCNEE_NAME(String CNEE_NAME) {
        this.CNEE_NAME = CNEE_NAME;
    }

    public String getFMS_ORDER_NO() {
        return FMS_ORDER_NO;
    }

    public void setFMS_ORDER_NO(String FMS_ORDER_NO) {
        this.FMS_ORDER_NO = FMS_ORDER_NO;
    }

    public String getSENDBOOLEAN() {
        return SENDBOOLEAN;
    }

    public void setSENDBOOLEAN(String SENDBOOLEAN) {
        this.SENDBOOLEAN = SENDBOOLEAN;
    }

    public String getFMS_TIME() {
        return FMS_TIME;
    }

    public void setFMS_TIME(String FMS_TIME) {
        this.FMS_TIME = FMS_TIME;
    }

    public String getGOODS_NUM() {
        return GOODS_NUM;
    }

    public void setGOODS_NUM(String GOODS_NUM) {
        this.GOODS_NUM = GOODS_NUM;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCNEE_ADDRESS() {
        return CNEE_ADDRESS;
    }

    public void setCNEE_ADDRESS(String CNEE_ADDRESS) {
        this.CNEE_ADDRESS = CNEE_ADDRESS;
    }

    public String getPOST_REMARK() {
        return POST_REMARK;
    }

    public void setPOST_REMARK(String POST_REMARK) {
        this.POST_REMARK = POST_REMARK;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
