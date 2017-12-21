package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2017/10/31.
 */
public class ZwwRoomBean extends BaseRoom implements Serializable{
    private int DOLL_GOLD;
    private String ROOM_ID;
    private String DOLL_STATE;
    private String DOLL_SN;
    private String DOLL_ID;
    private String CAMERA_NAME_01;
    private String CAMERA_NAME_02;

    public void setDOLL_GOLD(int DOLL_GOLD) {
        this.DOLL_GOLD = DOLL_GOLD;
    }

    public void setROOM_ID(String ROOM_ID) {
        this.ROOM_ID = ROOM_ID;
    }

    public void setDOLL_ID(String DOLL_ID) {
        this.DOLL_ID = DOLL_ID;
    }

    public void setDOLL_SN(String DOLL_SN) {
        this.DOLL_SN = DOLL_SN;
    }

    public void setDOLL_STATE(String DOLL_STATE) {
        this.DOLL_STATE = DOLL_STATE;
    }

    public int getDOLL_GOLD() {
        return DOLL_GOLD;
    }

    public String getDOLL_ID() {
        return DOLL_ID;
    }

    public String getDOLL_SN() {
        return DOLL_SN;
    }

    public String getDOLL_STATE() {
        return DOLL_STATE;
    }

    public String getROOM_ID() {
        return ROOM_ID;
    }

    public void setCAMERA_NAME_01(String CAMERA_NAME_01) {
        this.CAMERA_NAME_01 = CAMERA_NAME_01;
    }

    public String getCAMERA_NAME_01() {
        return CAMERA_NAME_01;
    }

    public void setCAMERA_NAME_02(String CAMERA_NAME_02) {
        this.CAMERA_NAME_02 = CAMERA_NAME_02;
    }

    public String getCAMERA_NAME_02() {
        return CAMERA_NAME_02;
    }

}
