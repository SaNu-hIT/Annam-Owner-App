package com.annam.annamownernew.Constants;

import static com.annam.annamownernew.Constants.ConsBooking.AnnamOWNER_BaseUrl;

/**
 * Created by SFT on 12/10/2016.
 */
public class ConsAddMachine {
    public static final String URL = AnnamOWNER_BaseUrl+"getmachinerydetails.php?format=json&machinery_type=";
    public static final String AddURL = AnnamOWNER_BaseUrl+"addmach.php?format=json&owner_id=";
    //public static final String URL = "http://192.168.1.13/debug/AnnamAgroTech/owner/getmachinerydetails.php?format=json&machinery_type=";
    //public static final String AddURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/addmach.php?format=json&owner_id=";
    public static final String ERROR_CODE = "errorCode";
    public static final String MACHINERY_DETAILS = "machinery_details";
    public static final String JSON_ARRAY_MACHINE_DETAILS = "machine_details";
}
