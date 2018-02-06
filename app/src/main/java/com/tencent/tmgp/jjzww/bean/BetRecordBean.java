package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yincong on 2017/12/6 16:54
 * 修改人：
 * 修改时间：
 * 类描述：投注记录实体类
 */
public class BetRecordBean implements Serializable{


    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * SETTLEMENT_FLAG : N
         * CREATE_DATE : 2017-12-28 17:11:50
         * GUESS_GOLD : 19
         * SETTLEMENT_GOLD : 0
         * GUESS_KEY : 1
         * APP_USER_ID : 1e70de1afef441ab97f873c8764f90f9
         * GUESS_ID : 201712280240
         * GUESS_TYPE : 1
         */

        private String SETTLEMENT_FLAG;
        private String CREATE_DATE;
        private String GUESS_GOLD;
        private int SETTLEMENT_GOLD;
        private String GUESS_KEY;
        private String APP_USER_ID;
        private String GUESS_ID;
        private String GUESS_TYPE;
        private String GUSESS_Y_PEOPLE;

        public String getGUSESS_Y_PEOPLE() {
            return GUSESS_Y_PEOPLE;
        }

        public void setGUSESS_Y_PEOPLE(String GUSESS_Y_PEOPLE) {
            this.GUSESS_Y_PEOPLE = GUSESS_Y_PEOPLE;
        }

        public String getSETTLEMENT_FLAG() {
            return SETTLEMENT_FLAG;
        }

        public void setSETTLEMENT_FLAG(String SETTLEMENT_FLAG) {
            this.SETTLEMENT_FLAG = SETTLEMENT_FLAG;
        }

        public String getCREATE_DATE() {
            return CREATE_DATE;
        }

        public void setCREATE_DATE(String CREATE_DATE) {
            this.CREATE_DATE = CREATE_DATE;
        }

        public String getGUESS_GOLD() {
            return GUESS_GOLD;
        }

        public void setGUESS_GOLD(String GUESS_GOLD) {
            this.GUESS_GOLD = GUESS_GOLD;
        }

        public int getSETTLEMENT_GOLD() {
            return SETTLEMENT_GOLD;
        }

        public void setSETTLEMENT_GOLD(int SETTLEMENT_GOLD) {
            this.SETTLEMENT_GOLD = SETTLEMENT_GOLD;
        }

        public String getGUESS_KEY() {
            return GUESS_KEY;
        }

        public void setGUESS_KEY(String GUESS_KEY) {
            this.GUESS_KEY = GUESS_KEY;
        }

        public String getAPP_USER_ID() {
            return APP_USER_ID;
        }

        public void setAPP_USER_ID(String APP_USER_ID) {
            this.APP_USER_ID = APP_USER_ID;
        }

        public String getGUESS_ID() {
            return GUESS_ID;
        }

        public void setGUESS_ID(String GUESS_ID) {
            this.GUESS_ID = GUESS_ID;
        }

        public String getGUESS_TYPE() {
            return GUESS_TYPE;
        }

        public void setGUESS_TYPE(String GUESS_TYPE) {
            this.GUESS_TYPE = GUESS_TYPE;
        }
    }
}
