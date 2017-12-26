package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2017/12/26 13:41
 * 修改人：
 * 修改时间：
 * 类描述：充值卡实体类
 */
public class PayCardBean implements Serializable {

    private String GOLD;        //金币数
    private String AMOUNT;      //金额
    private int ID;             //充值卡id
    private String IMAGEURL;    //充值卡url
    private String DISCOUNT;    //充值卡折扣


    public String getGOLD() {
        return GOLD;
    }

    public void setGOLD(String GOLD) {
        this.GOLD = GOLD;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIMAGEURL() {
        return IMAGEURL;
    }

    public void setIMAGEURL(String IMAGEURL) {
        this.IMAGEURL = IMAGEURL;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }
}
