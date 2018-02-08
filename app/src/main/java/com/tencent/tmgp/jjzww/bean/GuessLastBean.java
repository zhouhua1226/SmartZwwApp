package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/2/8 13:47
 * 修改人：
 * 修改时间：
 * 类描述：竞猜跑马灯实体类
 */
public class GuessLastBean implements Serializable{

    private String GUESSER_NAME;
    private String GUESS_ID;
    private String UPDATE_DATE;

    public String getGUESSER_NAME() {
        return GUESSER_NAME;
    }

    public void setGUESSER_NAME(String GUESSER_NAME) {
        this.GUESSER_NAME = GUESSER_NAME;
    }

    public String getGUESS_ID() {
        return GUESS_ID;
    }

    public void setGUESS_ID(String GUESS_ID) {
        this.GUESS_ID = GUESS_ID;
    }

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }
}
