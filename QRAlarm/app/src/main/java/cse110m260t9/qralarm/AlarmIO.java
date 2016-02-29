package cse110m260t9.qralarm;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Michael on 2/28/2016.
 */
public class AlarmIO {
    public static ArrayList<Long> getAlarms( Context ctx ) {
        // Read the raw string from file
        String alarmStr = FileIO.retrieveStringFromFile(MyConstants.ALARM_FILE, ctx);
        // Split the raw string by a comma
        String[] alarmArrRaw = alarmStr.split(",");
        ArrayList<Long> alarmArrMillis = new ArrayList<Long>();
        // Populate alarmArrMillis from the read file
        if(!alarmStr.equals(""))
            for (String w: alarmArrRaw) { alarmArrMillis.add(new Long(w)); }
        return alarmArrMillis;
    }

    public static void saveAlarm( long millis, Context ctx) {
        ArrayList<Long> alarmArrMillis = getAlarms(ctx);
        alarmArrMillis.add(new Long(millis));
        writeAlarm(alarmArrMillis, ctx);
    }

    public static void deleteAlarm( long millis, Context ctx ) {
        ArrayList<Long> alarmArrMillis = getAlarms(ctx);
        Long intToDelete = new Long(millis);
        for(Long i : alarmArrMillis) {
            if (i.equals(intToDelete))
                alarmArrMillis.remove(i);
        }
        writeAlarm(alarmArrMillis, ctx);
    }

    private static void writeAlarm( ArrayList<Long> alarmList, Context ctx ) {
        String stringToWrite = "";
        for(int i = 0; i < alarmList.size(); i++ ) {
            stringToWrite += alarmList.get(i);
            if(i != alarmList.size() - 1 )
                stringToWrite += ",";
        }
        FileIO.saveString(MyConstants.ALARM_FILE, stringToWrite, ctx);
    }
}
