package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/3/19 09:47
 * 修改人：
 * 修改时间：
 * 类描述：银行卡信息实体类
 */
public class BankCardBean implements Serializable{

    private String BANKCARD_ID;
    private String USER_REA_NAME;   //真实姓名
    private String ID_NUMBER;       //身份证
    private String BANK_CARD_NO;    //银行卡号
    private String USER_ID;
    private String BANK_BRANCH;     //开户支行
    private String BANK_ADDRESS;    //开户地点
    private String IS_DEFAULT;
    private String BANK_NAME;     //银行名称
    private String BANK_PHONE;   //银行绑定手机号

    public String getBANKCARD_ID() {
        return BANKCARD_ID;
    }

    public void setBANKCARD_ID(String BANKCARD_ID) {
        this.BANKCARD_ID = BANKCARD_ID;
    }

    public String getUSER_REA_NAME() {
        return USER_REA_NAME;
    }

    public void setUSER_REA_NAME(String USER_REA_NAME) {
        this.USER_REA_NAME = USER_REA_NAME;
    }

    public String getID_NUMBER() {
        return ID_NUMBER;
    }

    public void setID_NUMBER(String ID_NUMBER) {
        this.ID_NUMBER = ID_NUMBER;
    }

    public String getBANK_CARD_NO() {
        return BANK_CARD_NO;
    }

    public void setBANK_CARD_NO(String BANK_CARD_NO) {
        this.BANK_CARD_NO = BANK_CARD_NO;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getBANK_BRANCH() {
        return BANK_BRANCH;
    }

    public void setBANK_BRANCH(String BANK_BRANCH) {
        this.BANK_BRANCH = BANK_BRANCH;
    }

    public String getBANK_ADDRESS() {
        return BANK_ADDRESS;
    }

    public void setBANK_ADDRESS(String BANK_ADDRESS) {
        this.BANK_ADDRESS = BANK_ADDRESS;
    }

    public String getIS_DEFAULT() {
        return IS_DEFAULT;
    }

    public void setIS_DEFAULT(String IS_DEFAULT) {
        this.IS_DEFAULT = IS_DEFAULT;
    }

    public String getBANK_NAME() {
        return BANK_NAME;
    }

    public void setBANK_NAME(String BANK_NAME) {
        this.BANK_NAME = BANK_NAME;
    }

    public String getBANK_PHONE() {
        return BANK_PHONE;
    }

    public void setBANK_PHONE(String BANK_PHONE) {
        this.BANK_PHONE = BANK_PHONE;
    }
}
