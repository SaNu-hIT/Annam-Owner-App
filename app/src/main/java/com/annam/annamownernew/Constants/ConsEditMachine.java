package com.annam.annamownernew.Constants;

import static com.annam.annamownernew.Constants.ConsBooking.AnnamOWNER_BaseUrl;

/**
 * Created by SFT on 25/10/2016.
 */
public class ConsEditMachine {
    //public static final String getURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/selectMacForEdit.php?owner_id=";
    //public static final String setURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/ownermachinerysettings?owner_id=";
    public static final String getURL = AnnamOWNER_BaseUrl+"selectMacForEdit.php?owner_id=";
    public static final String setURL = AnnamOWNER_BaseUrl+"ownermachinerysettings.php?owner_id=";
    public static final String DELETE_URL = AnnamOWNER_BaseUrl+"deletemachinery.php?owner_id=";

    public static final String ERROR_CODE = "errorCode";
    public static final String SUCCESS = "Success";
    public static final String JSON_OBJECT_MACHINE_DETAILS = "machine_details";

    public static final String MACHINE_NAME = "machine_name";
    public static final String MACHINE_NO = "machine_no";
    public static final String OWN_MACHID = "owner_machid";
    public static final String SPECIFICATION1 = "specification1";
    public static final String SPECIFICATION2 = "specification2";
    public static final String SPECIFICATION3 = "specification3";
    public static final String RADIOUS = "radious";
    public static final String PICKUP = "pickup";
    public static final String TRANSPORTRATE = "transportrate";
    public static final String MINRATE = "minrate";
    public static final String RATE_PER_DAY = "rate_per_day";
    public static final String RATE_PER_HOUR = "rate_per_hour";
}
