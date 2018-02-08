package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/2/8 11:47
 * 修改人：
 * 修改时间：
 * 类描述：邀请码奖励规则实体类
 */
public class AwardCodePdBean implements Serializable{

    private String inviteTotalNum;  //邀请人数上限
    private String exchangeAmount;  //被邀请人奖励金币数
    private String inviteAmount;    //邀请人奖励金币数

    public String getInviteTotalNum() {
        return inviteTotalNum;
    }

    public void setInviteTotalNum(String inviteTotalNum) {
        this.inviteTotalNum = inviteTotalNum;
    }

    public String getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(String exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public String getInviteAmount() {
        return inviteAmount;
    }

    public void setInviteAmount(String inviteAmount) {
        this.inviteAmount = inviteAmount;
    }
}
