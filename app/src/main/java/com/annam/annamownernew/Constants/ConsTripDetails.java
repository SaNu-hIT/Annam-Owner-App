package com.annam.annamownernew.Constants;

import static com.annam.annamownernew.Constants.ConsBooking.AnnamOWNER_BaseUrl;

/**
 * Created by SFT on 14/10/2016.
 */
public class ConsTripDetails {
    //public static final String URL = "http://192.168.1.13/debug/AnnamAgroTech/owner/tripDetails.php?format=json&booking_id=";
    //public static final String AcceptURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/acceptOrRejectTrip.php?format=json&booking_id=";
    //public static final String StartURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/startJob.php?format=json&booking_id=";
    //public static final String EndURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/endJob.php?format=json&booking_id=";
//    public static final String URL = AnnamOWNER_BaseUrl+"tripDetails.php?format=json&booking_id=";
    public static final String URL = AnnamOWNER_BaseUrl+"tripDetails.php?owner_id=";
    public static final String AcceptURL = AnnamOWNER_BaseUrl+"acceptOrRejectTrip.php?format=json&booking_id=";
    public static final String StartURL = AnnamOWNER_BaseUrl+"startJob.php?format=json&booking_id=";
    public static final String EndURL = AnnamOWNER_BaseUrl+"endJob.php?format=json&booking_id=";
}
