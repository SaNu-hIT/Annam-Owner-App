package com.annam.annamownernew.Constants;

import static com.annam.annamownernew.Constants.ConsBooking.AnnamOWNER_BaseUrl;

/**
 * Created by SFT on 7/10/2016.
 */
public class ConsLogin {
    public static final String LOGIN_URL = AnnamOWNER_BaseUrl+"logincheck.php";
    //public static final String LOGIN_URL = "http://192.168.1.13/debug/AnnamAgroTech/owner/logincheck.php";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    public static final String ERROR_CODE = "errorCode";
    public static final String OWNER_ID = "owner_id";
    public static final String OWNER_NAME = "owner_name";
    public static final String ONETIME = "onetime";
    public static final String DISABLE_FLAG = "disable_flag";

    public static final String JSON_ARRAY_MACHINE_TYPE = "machinetype";
    public static final String JSON_ARRAY_MACHINE_DETAILS = "machine_details";

    public static final String MT_MACHINE_TYPE_ID = "machine_type_id";
    public static final String MT_NAME = "name";
    public static final String MT_PICKUP_REQ = "pickup_req";

    public static final String MD_MACHINE_ID = "machine_id";
    public static final String MD_MACHINE_NO = "machine_no";
    public static final String MD_MACHINE_NAME = "machine_name";
    public static final String MD_MACHINE_DESC = "machine_desc";
    public static final String MD_MAVAILABLE = "mavailable";
    public static final String MD_IMAGE = "image";

    public static final String PROFILE_URL = AnnamOWNER_BaseUrl+"viewprofile.php?format=json&owner_id=";
    public static final String PROFILE_SETURL = AnnamOWNER_BaseUrl+"profilesettings.php?format=json&owner_id=";
    //public static final String PROFILE_URL = "http://192.168.1.13/debug/AnnamAgroTech/owner/viewprofile.php?format=json&owner_id=";
    //public static final String PROFILE_SETURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/profilesettings.php?format=json&owner_id=";
    public static final String JSON_ARRAY_PROFILE = "profileData";
    public static final String PROFILE_NAME = "name";
    public static final String PROFILE_DRIVERID = "driverid";
    public static final String PROFILE_ADDRESS = "address";
    public static final String PROFILE_PHONE = "phone";
    public static final String PROFILE_EMAIL = "email";
    public static final String PROFILE_LOCATION = "location";
}
