package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/3/16 11:18
 * 修改人：
 * 修改时间：
 * 类描述：加盟实体类
 */
public class PromomoteBean implements Serializable{
    private String PRO_TYPE;
    private String CONVER_GOLD;
    private String PRO_MANAGE_NAME;
    private String PAY_GOLD;
    private int PRO_MANAGE_ID;
    private String RETURN_RATIO;
    private int PRO_ID;

    public int getPRO_ID() {
        return PRO_ID;
    }

    public void setPRO_ID(int PRO_ID) {
        this.PRO_ID = PRO_ID;
    }

    public String getPRO_TYPE() {
        return PRO_TYPE;
    }

    public void setPRO_TYPE(String PRO_TYPE) {
        this.PRO_TYPE = PRO_TYPE;
    }

    public String getCONVER_GOLD() {
        return CONVER_GOLD;
    }

    public void setCONVER_GOLD(String CONVER_GOLD) {
        this.CONVER_GOLD = CONVER_GOLD;
    }

    public String getPRO_MANAGE_NAME() {
        return PRO_MANAGE_NAME;
    }

    public void setPRO_MANAGE_NAME(String PRO_MANAGE_NAME) {
        this.PRO_MANAGE_NAME = PRO_MANAGE_NAME;
    }

    public String getPAY_GOLD() {
        return PAY_GOLD;
    }

    public void setPAY_GOLD(String PAY_GOLD) {
        this.PAY_GOLD = PAY_GOLD;
    }

    public int getPRO_MANAGE_ID() {
        return PRO_MANAGE_ID;
    }

    public void setPRO_MANAGE_ID(int PRO_MANAGE_ID) {
        this.PRO_MANAGE_ID = PRO_MANAGE_ID;
    }

    public String getRETURN_RATIO() {
        return RETURN_RATIO;
    }

    public void setRETURN_RATIO(String RETURN_RATIO) {
        this.RETURN_RATIO = RETURN_RATIO;
    }


}
