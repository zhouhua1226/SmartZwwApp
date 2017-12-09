package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by hongxiu on 2017/12/3.
 */
public class PondResponseBean implements Serializable {

    /**
     * pond : {"CREATE_DATE":"2017-12-03 15:03:30","GUESS_GOLD":0,"UPDATE_DATE":"","PLAYBACK_ID":444,"GUESS_Y":0,"POND_ID":444,"GUESS_N":0}
     */

    private PondBean pond;

    public PondBean getPond() {
        return pond;
    }

    public void setPond(PondBean pond) {
        this.pond = pond;
    }

    public static class PondBean {
        /**
         * CREATE_DATE : 2017-12-03 15:03:30
         * GUESS_GOLD : 0
         * UPDATE_DATE :
         * PLAYBACK_ID : 444
         * GUESS_Y : 0
         * POND_ID : 444
         * GUESS_N : 0
         */

        private String CREATE_DATE;
        private int GUESS_GOLD;
        private String UPDATE_DATE;
        private int PLAYBACK_ID;
        private int GUESS_Y;
        private int POND_ID;
        private int GUESS_N;

        public String getCREATE_DATE() {
            return CREATE_DATE;
        }

        public void setCREATE_DATE(String CREATE_DATE) {
            this.CREATE_DATE = CREATE_DATE;
        }

        public int getGUESS_GOLD() {
            return GUESS_GOLD;
        }

        public void setGUESS_GOLD(int GUESS_GOLD) {
            this.GUESS_GOLD = GUESS_GOLD;
        }

        public String getUPDATE_DATE() {
            return UPDATE_DATE;
        }

        public void setUPDATE_DATE(String UPDATE_DATE) {
            this.UPDATE_DATE = UPDATE_DATE;
        }

        public int getPLAYBACK_ID() {
            return PLAYBACK_ID;
        }

        public void setPLAYBACK_ID(int PLAYBACK_ID) {
            this.PLAYBACK_ID = PLAYBACK_ID;
        }

        public int getGUESS_Y() {
            return GUESS_Y;
        }

        public void setGUESS_Y(int GUESS_Y) {
            this.GUESS_Y = GUESS_Y;
        }

        public int getPOND_ID() {
            return POND_ID;
        }

        public void setPOND_ID(int POND_ID) {
            this.POND_ID = POND_ID;
        }

        public int getGUESS_N() {
            return GUESS_N;
        }

        public void setGUESS_N(int GUESS_N) {
            this.GUESS_N = GUESS_N;
        }
    }
}
