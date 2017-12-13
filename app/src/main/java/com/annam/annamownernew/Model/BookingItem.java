package com.annam.annamownernew.Model;

/**
 * Created by SFT on 15/10/2016.
 */
public class BookingItem {

    private String BOOKING_ID;
    private String LOCATION;
    private String MACHINE_NAME;
    private String MACHINE_TYPE;
    private String CUSTOMER_NAME;
    private String CUSTOMER_PHNO;
    private String DATE;
    private String EST_AMOUNT;
    private String EXP_TIME;
    private String MACHINE_NO;
    private String BSTATUS;

    public String getBOOKING_ID() {
        return BOOKING_ID;
    }

    public void setBOOKING_ID(String BOOKING_ID) {
        this.BOOKING_ID = BOOKING_ID;
    }
    public String getMACHINE_NO() {
        return MACHINE_NO;
    }

    public void setMACHINE_NO(String MACHINE_NO) {
        this.MACHINE_NO = MACHINE_NO;
    }

    public String getEXP_TIME() {
        return EXP_TIME;
    }

    public void setEXP_TIME(String EXP_TIME) {
        this.EXP_TIME = EXP_TIME;
    }

    public String getEST_AMOUNT() {
        return EST_AMOUNT;
    }

    public void setEST_AMOUNT(String EST_AMOUNT) {
        this.EST_AMOUNT = EST_AMOUNT;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getBSTATUS() {
        return BSTATUS;
    }

    public void setBSTATUS(String BSTATUS) {
        this.BSTATUS = BSTATUS;
    }

    //  private String CUSTOMER_ADDRESS;
    // private String CUSTOMER_EMAIL;

    public String getMACHINE_TYPE() {
        return MACHINE_TYPE;
    }

    public void setMACHINE_TYPE(String MACHINE_TYPE) {
        this.MACHINE_TYPE = MACHINE_TYPE;
    }

    public String getMACHINE_NAME() {
        return MACHINE_NAME;
    }

    public void setMACHINE_NAME(String MACHINE_NAME) {
        this.MACHINE_NAME = MACHINE_NAME;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getCUSTOMER_PHNO() {
        return CUSTOMER_PHNO;
    }

    public void setCUSTOMER_PHNO(String CUSTOMER_PHNO) {
        this.CUSTOMER_PHNO = CUSTOMER_PHNO;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }
}
