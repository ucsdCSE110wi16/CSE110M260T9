package cse110m260t9.qralarm;

/**
 * Created by Nid on 1/25/16.
 */
public abstract class MyConstants {
    public static final String HOUR = "time_hour";
    public static final String MINUTE = "time_minute";
    public static final String TIME_PICKER = "time_picker";

    public static final String LOCATION_FILE = "QRAlarm_Saved_Location_Info";
    public static final String QR_HASH_FILE = "QRAlarm_QR_Hash";
    public static final String ALARM_FILE = "Alarm_File";


    public final static String NEW_LOCATION = "New Location";
    public final static String CURR_LOCATION = "Location";
    public final static String LOCATION_NOT_SET = "Location not set";
    public final static String LOCATION_SET = "Location set";
    public final static String ALARM_SAVED_STR = "Alarm saved";
    public final static String PROX_ALERT_FLAG = "Proximity Alert flag";

    public final static int NEW_ALARM_ACTIVITY = 0xF1;
    public final static int LOCATION_SUCCESSFULLY_SET = 0x20;
    public final static int NEW_ALARM_SUCCESSFULLY_SET = 0x21;


}