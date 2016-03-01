package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michael on 2/28/2016.
 */
public class Alarm implements Serializable{
    // Fields
    public ArrayList<Integer> daysAlarmShouldFire;
    public ArrayList<Long> broadcastTimes;
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
        broadcastTimes = new ArrayList<>();
    }

    public Alarm( Alarm alm ) {
        this(alm.name, alm.alarmTime, alm.daysAlarmShouldFire, alm.isRepeating);
    }

    public static Alarm fromSerializedString( byte[] serializedAlarm ) {
        try {
            byte b[] = serializedAlarm;
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            Alarm alm  = (Alarm) si.readObject();
            return alm;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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

    private void purgeOldAlarms() {
        if(isRepeating)
            return;

    }

    public ArrayList<Calendar> getAlarmAsCalendarList() {
        Calendar instance = Calendar.getInstance();
        System.out.println("Processing Alarm: " + this);
        int today = instance.get(instance.DAY_OF_WEEK);
        ArrayList<Calendar> rv = new ArrayList<>();
        for(Integer day : daysAlarmShouldFire ) {
            System.out.println("Day is: " + day);
            Calendar alarmClone = Calendar.getInstance();
            alarmClone.set(alarmClone.HOUR_OF_DAY, alarmTime.get(alarmTime.HOUR_OF_DAY));
            alarmClone.set(alarmClone.MINUTE, alarmTime.get(alarmTime.MINUTE));
            alarmClone.set(alarmClone.SECOND, alarmTime.get(alarmTime.SECOND));
            // If the alarm is set for today
            if (day == alarmClone.get(alarmClone.DAY_OF_WEEK)) {
                // If the alarm should occur later in the week
                if (instance.getTimeInMillis() > alarmClone.getTimeInMillis() + 2000)
                    alarmClone.add(Calendar.DAY_OF_WEEK, 7);
                // If this condition doesn't hold, then we can assume the alarm will happen later
                // in the day. The alarm clone should already be set to this value
            }
            // If the alarm is set to happen later during the week
            else
                alarmClone.add(Calendar.DAY_OF_WEEK, calculateDayDifference(today, day));
            rv.add(alarmClone);
        }
        System.out.println("Finished processing Alarm: " + this);
        return rv;
    }

    private static int calculateDayDifference( int currentDay, int futureDay ) {
        int delta = currentDay - futureDay;
        return delta < 0 ? Math.abs(delta) : 7-delta;
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
        rv += "\n Condensed: " + getDateAndTimeSet();
        return rv;
    }

    public String getDateAndTimeSet() {
        String hours = String.format("%02d", alarmTime.get(alarmTime.HOUR_OF_DAY));
        String minutes = String.format("%02d",alarmTime.get(alarmTime.MINUTE) );
        String year = String.format("%04d",alarmTime.get(alarmTime.YEAR));
        String month = String.format("%02d",alarmTime.get(alarmTime.MONTH));
        String day = String.format("%02d",alarmTime.get(alarmTime.DAY_OF_MONTH));
        String seconds = String.format("%02d", alarmTime.get(alarmTime.SECOND));
        return year + "-" + month + "-" + day + "|" + hours + ":" + minutes + ":" + seconds;
    }
    public byte[] getSerializedString() {
        byte[] rv = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(this);
            so.flush();
            rv = bo.toByteArray();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Serialized String: " + rv);
        return rv;
    }
}
