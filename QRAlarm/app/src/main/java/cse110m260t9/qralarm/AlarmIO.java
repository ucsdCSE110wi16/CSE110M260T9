package cse110m260t9.qralarm;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Michael on 2/28/2016.
 */
public class AlarmIO extends FileIO {
    public static ArrayList<Integer> getAlarms( Context ctx ) {
        // Read the raw string from file
        String alarmStr = FileIO.retrieveStringFromFile(MyConstants.ALARM_FILE, ctx);
        // Split the raw string by a comma
        String[] alarmArrRaw = alarmStr.split(",");
        ArrayList<Integer> alarmArrMillis = new ArrayList<Integer>();
        // Populate alarmArrMillis from the read file
        for (String w: alarmArrRaw) { alarmArrMillis.add(new Integer(w)); }
        return alarmArrMillis;
    }

    public static void saveAlarm( int millis, Context ctx) {
        ArrayList<Integer> alarmArrMillis = getAlarms(ctx);
        alarmArrMillis.add(new Integer(millis));
        writeAlarm(alarmArrMillis, ctx);
    }

    public static void deleteAlarm( int millis, Context ctx ) {
        ArrayList<Integer> alarmArrMillis = getAlarms(ctx);
        Integer intToDelete = new Integer(millis);
        for(Integer i : alarmArrMillis) {
            if (i.equals(intToDelete))
                alarmArrMillis.remove(i);
        }
        writeAlarm(alarmArrMillis, ctx);
    }

    private static void writeAlarm( ArrayList<Integer> alarmList, Context ctx ) {
        String stringToWrite = "";
        for(int i = 0; i < alarmList.size(); i++ ) {
            stringToWrite += alarmList.get(i);
            if(i != alarmList.size() - 1 )
                stringToWrite += ",";
        }
        FileIO.saveString(MyConstants.ALARM_FILE, stringToWrite, ctx);
    }
}
