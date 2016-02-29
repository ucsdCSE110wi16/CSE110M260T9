package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michael on 2/28/2016.
 */
public class Alarm implements Serializable{
    // Fields
    public ArrayList<Integer> daysAlarmShouldFire;
    private ArrayList<Integer> broadcastIDs;
    public boolean isRepeating;
    public String name;
    public Calendar alarmTime;

    private int hour;
    private int minute;



    /**
     * Primary Constructor
     * @param name <- The name of the alarm
     * @param calendar <- The calendar holding the time at which the alarm should fire
     * @param days <- An array list of Calendar Day Integers for when the alarm should fire
     * @param shouldRepeat <-- Boolean value to determine whether the alarm repeats
     */
    public Alarm( String name, Calendar calendar, ArrayList<Integer> days, boolean shouldRepeat ) {
        this.name = scrubName(name);
        daysAlarmShouldFire = days;
        isRepeating = shouldRepeat;
        alarmTime = calendar;
        hour = alarmTime.get(alarmTime.HOUR_OF_DAY);
        minute = alarmTime.get(alarmTime.MINUTE);
    }

    public boolean isAM() {
        return hour >= 12 ? false : true;
    }

    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    private String scrubName( String name ) {
        String rv = new String(name);
        return rv.replace(";", "").replace(",", "");
    }

    @Override public String toString() {
        String rv = "Alarm name: " + this.name;
        rv += "\nDays: [ ";
        for(Integer day : daysAlarmShouldFire ) {
            switch (day) {
                case 1: rv += "Sunday ";
                    break;
                case 2: rv += "Monday ";
                    break;
                case 3: rv += "Tuesday ";
                    break;
                case 4: rv += "Wednesday ";
                    break;
                case 5: rv += "Thursday ";
                    break;
                case 6: rv += "Friday ";
                    break;
                case 7: rv += "Saturday ";
                    break;
            }
        }
        rv += " ]\n";
        String hours = String.format("%02d", alarmTime.get(alarmTime.HOUR_OF_DAY));
        String minutes = String.format("%02d",alarmTime.get(alarmTime.MINUTE) );
        String year = String.format("%04d",alarmTime.get(alarmTime.YEAR));
        String month = String.format("%02d",alarmTime.get(alarmTime.MONTH));
        String day = String.format("%02d",alarmTime.get(alarmTime.DAY_OF_MONTH));
        rv += "Time: " + hours + ":" + minutes;
        rv += "\nRepeats: " + isRepeating;
        rv += "\nDate Alarm was Set: " + year + "/" + month + "/" + day;
        return rv;
    }


}