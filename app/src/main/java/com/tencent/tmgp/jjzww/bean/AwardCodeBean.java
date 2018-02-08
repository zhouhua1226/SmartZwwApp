package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by yincong on 2018/2/7 20:53
 * 修改人：
 * 修改时间：
 * 类描述：邀请码实体类
 */
public class AwardCodeBean implements Serializable{

    private int AWARDCOUNT;
    private int AWARDSUM;

    public int getAWARDCOUNT() {
        return AWARDCOUNT;
    }

    public void setAWARDCOUNT(int AWARDCOUNT) {
        this.AWARDCOUNT = AWARDCOUNT;
    }

    public int getAWARDSUM() {
        return AWARDSUM;
    }

    public void setAWARDSUM(int AWARDSUM) {
        this.AWARDSUM = AWARDSUM;
    }
}
