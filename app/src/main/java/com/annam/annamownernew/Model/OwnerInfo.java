package com.annam.annamownernew.Model;

/**
 * Created by SFT on 9/10/2016.
 */
public class  OwnerInfo {
    public static String OWNER_ID;
    public static String OWNER_NAME;
    public static String ONETIME;
    public static String DISABLE_FLAG;
    public static String USERNAME;

    public static String getOwnerId() {
        return OWNER_ID;
    }

    public static void setOwnerId(String ownerId) {
        OWNER_ID = ownerId;
    }

    public static String getOwnerName() {
        return OWNER_NAME;
    }

    public static void setOwnerName(String ownerName) {
        OWNER_NAME = ownerName;
    }

    public static String getONETIME() {
        return ONETIME;
    }

    public static void setONETIME(String ONETIME) {
        OwnerInfo.ONETIME = ONETIME;
    }

    public static String getDisableFlag() {
        return DISABLE_FLAG;
    }

    public static void setDisableFlag(String disableFlag) {
        DISABLE_FLAG = disableFlag;
    }

    public static String getUSERNAME() { return USERNAME; }

    public static void setUSERNAME(String USERNAME) { OwnerInfo.USERNAME = USERNAME; }
}
