package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2018/1/23.
 */
public class ToyTypeBean implements Serializable{
    private int ID;
    private String TOY_TYPE;
    private int RANKING;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRANKING(int RANKING) {
        this.RANKING = RANKING;
    }

    public void setTOY_TYPE(String TOY_TYPE) {
        this.TOY_TYPE = TOY_TYPE;
    }

    public int getID() {
        return ID;
    }

    public int getRANKING() {
        return RANKING;
    }

    public String getTOY_TYPE() {
        return TOY_TYPE;
    }
}
