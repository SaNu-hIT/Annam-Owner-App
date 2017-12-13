package com.annam.annamownernew.Constants;

/**
 * Created by SFT on 16/10/2016.
 */
public class ConsBooking {

    public static final String AnnamOWNER_BaseUrl = "http://annamagrotech.com/webservice/owner/";
    public static final String AnnamFARMAR_BaseUrl = "http://annamagrotech.com/webservice/farmer/";
    //public static final String getBookingURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/bookings.php?owner_id=";
    //public static final String sendSMSURL = "http://192.168.1.13/debug/AnnamAgroTech/owner/sendcancelsms.php?owner=";
    public static final String getBookingURL = AnnamOWNER_BaseUrl+"viewbooking.php?owner_id=";
//public static final String getBookingURL = "http://softforest.in/demo/AnnamAgroTech/owner/viewbookings.php?owner_id=";
    public static final String sendSMSURL = AnnamOWNER_BaseUrl+"sendcancelsms.php?owner=";
    public static final String VIEWMACHANE = AnnamOWNER_BaseUrl+"viewmach.php?format=json&owner_id=";
}
