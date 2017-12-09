package com.tencent.tmgp.jjzww.bean;

import java.io.Serializable;

/**
 * Created by hongxiu on 2017/11/30.
 */
public class BetsBackBean implements Serializable {
            private int DOLLTOTAL;
            private String POSTSTATE;
            private String DOLLNAME;
            private String STATE;
            private String USERNAME;
            private String COUNT;
            private int ID;
            private int CONVERSIONGOLD;
            private String CREATETIME;
            private String DOLL_URL;

            public int getDOLLTOTAL() {
                return DOLLTOTAL;
            }

            public void setDOLLTOTAL(int DOLLTOTAL) {
                this.DOLLTOTAL = DOLLTOTAL;
            }

            public String getPOSTSTATE() {
                return POSTSTATE;
            }

            public void setPOSTSTATE(String POSTSTATE) {
                this.POSTSTATE = POSTSTATE;
            }

            public String getDOLLNAME() {
                return DOLLNAME;
            }

            public void setDOLLNAME(String DOLLNAME) {
                this.DOLLNAME = DOLLNAME;
            }

            public String getSTATE() {
                return STATE;
            }

            public void setSTATE(String STATE) {
                this.STATE = STATE;
            }

            public String getUSERNAME() {
                return USERNAME;
            }

            public void setUSERNAME(String USERNAME) {
                this.USERNAME = USERNAME;
            }

            public String getCOUNT() {
                return COUNT;
            }

            public void setCOUNT(String COUNT) {
                this.COUNT = COUNT;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getCONVERSIONGOLD() {
                return CONVERSIONGOLD;
            }

            public void setCONVERSIONGOLD(int CONVERSIONGOLD) {
                this.CONVERSIONGOLD = CONVERSIONGOLD;
            }

            public String getCREATETIME() {
                return CREATETIME;
            }

            public void setCREATETIME(String CREATETIME) {
                this.CREATETIME = CREATETIME;
            }

            public String getDOLL_URL() {
                return DOLL_URL;
            }

            public void setDOLL_URL(String DOLL_URL) {
                this.DOLL_URL = DOLL_URL;
            }
        }

