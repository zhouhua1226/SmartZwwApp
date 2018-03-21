package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/3/16 17:30
 * 修改人：
 * 修改时间：
 * 类描述：代理收益
 */
public class PromoteEarnBean implements Serializable{

    private String TRANS_AMT;        //订单提交处理金额（单位：分）
    private String ORG_TRANS_AMT;   //下属用户充值金额（单位：分）
    private String LAST_TXN_TIME;   //交易时间
    private String TRANS_TYPE;
    private String LAST_TXN_DATE;  //交易日期
    private String ACC_AMT;        //账户处理金额（单位：分）
    private String LOG_TYPE;       //1：加款，2：减款
    private long LOG_ID;
    private String ACC_TOTAL_AMT;  //账务处理后，账户余额
    private String RES_COLUMN1;    //下属用户昵称

    private String ORDER_ST;
    private String LOG_STR;
    private String CREATE_DATE;

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getORDER_ST() {
        return ORDER_ST;
    }

    public void setORDER_ST(String ORDER_ST) {
        this.ORDER_ST = ORDER_ST;
    }

    public String getLOG_STR() {
        return LOG_STR;
    }

    public void setLOG_STR(String LOG_STR) {
        this.LOG_STR = LOG_STR;
    }

    public String getTRANS_AMT() {
        return TRANS_AMT;
    }

    public void setTRANS_AMT(String TRANS_AMT) {
        this.TRANS_AMT = TRANS_AMT;
    }

    public String getORG_TRANS_AMT() {
        return ORG_TRANS_AMT;
    }

    public void setORG_TRANS_AMT(String ORG_TRANS_AMT) {
        this.ORG_TRANS_AMT = ORG_TRANS_AMT;
    }

    public String getLAST_TXN_TIME() {
        return LAST_TXN_TIME;
    }

    public void setLAST_TXN_TIME(String LAST_TXN_TIME) {
        this.LAST_TXN_TIME = LAST_TXN_TIME;
    }

    public String getTRANS_TYPE() {
        return TRANS_TYPE;
    }

    public void setTRANS_TYPE(String TRANS_TYPE) {
        this.TRANS_TYPE = TRANS_TYPE;
    }

    public String getLAST_TXN_DATE() {
        return LAST_TXN_DATE;
    }

    public void setLAST_TXN_DATE(String LAST_TXN_DATE) {
        this.LAST_TXN_DATE = LAST_TXN_DATE;
    }

    public String getACC_AMT() {
        return ACC_AMT;
    }

    public void setACC_AMT(String ACC_AMT) {
        this.ACC_AMT = ACC_AMT;
    }

    public String getLOG_TYPE() {
        return LOG_TYPE;
    }

    public void setLOG_TYPE(String LOG_TYPE) {
        this.LOG_TYPE = LOG_TYPE;
    }

    public long getLOG_ID() {
        return LOG_ID;
    }

    public void setLOG_ID(long LOG_ID) {
        this.LOG_ID = LOG_ID;
    }

    public String getACC_TOTAL_AMT() {
        return ACC_TOTAL_AMT;
    }

    public void setACC_TOTAL_AMT(String ACC_TOTAL_AMT) {
        this.ACC_TOTAL_AMT = ACC_TOTAL_AMT;
    }

    public String getRES_COLUMN1() {
        return RES_COLUMN1;
    }

    public void setRES_COLUMN1(String RES_COLUMN1) {
        this.RES_COLUMN1 = RES_COLUMN1;
    }
}
