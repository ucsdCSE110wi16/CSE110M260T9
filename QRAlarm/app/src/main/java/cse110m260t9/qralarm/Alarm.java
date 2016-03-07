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
import java.util.ListIterator;

/**
 * Class: Alarm.java
 * Description: Represents an instance of our QR Alarm
 *              Saves the days alarm should fire, the alarm's name, whether an alarm should repeat
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
        this.name = name;
        daysAlarmShouldFire = days;
        isRepeating = shouldRepeat;
        alarmTime = calendar;
        hour = alarmTime.get(alarmTime.HOUR_OF_DAY);
        minute = alarmTime.get(alarmTime.MINUTE);
        broadcastTimes = new ArrayList<>();


    }

    /**
     * Copy Constructor
     * @param alm
     */
    public Alarm( Alarm alm ) {
        this(alm.name, alm.alarmTime, alm.daysAlarmShouldFire, alm.isRepeating);
    }

    /**
     * Function Name: fromSerializedBytes()
     * Description: This function will reconstruct an alarm from a serialized byte array
     * Side Effects: The reconstructed alarm will be purged of all old alarms
     * @param serializedAlarm
     * @return
     */
    public static Alarm fromSerializedBytes(byte[] serializedAlarm) {
        try {
            byte b[] = serializedAlarm;
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            Alarm alm  = (Alarm) si.readObject();
            alm.purgeOldAlarms();
            return alm;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Function Name: getSerializedBytes()
     * Description: This function will translate this alarm to a serialized byte array.
     * @return
     */
    public byte[] getSerializedBytes() {
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
        return rv;
    }

    /**
     * Function Name: isAM()
     * @return true if the alarm is in the morning, else false
     */
    public boolean isAM() {
        return hour >= 12 ? false : true;
    }

    /**
     * Function Name: getHour()
     * @return The hour value at which this alarm will fire in military time
     */
    public int getHour() { return hour; }

    /**
     * Function Name: getMinute()
     * @return The minute value at which this alarm will fire
     */
    public int getMinute() { return minute; }

    /**
     * Function Name: purgeOldAlarms()
     * Description: This function removes all old non-repeating alarms from this alarm
     */
    public void purgeOldAlarms() {
        if(isRepeating)
            return;
        System.out.println("Entering purge function");
        Calendar today = Calendar.getInstance();
        System.out.println("Purge on day: " + today);
        for(Calendar cal : getAlarmAsCalendarList() ) {
            System.out.println("Testing to see if should purge: " + cal);
            if( cal.getTimeInMillis() < today.getTimeInMillis() )
            {
                System.out.println("I should purge here");
                ListIterator<Integer> listIterator = daysAlarmShouldFire.listIterator();
                while(listIterator.hasNext()) {
                    Integer i = listIterator.next();
                    if( i == today.get(Calendar.DAY_OF_WEEK))
                        listIterator.remove();
                }
            }
        }
    }

    /**
     * Function Name: getAlarmAsCalendarList()
     * Description: This function returns an ArrayList of Calendars that represent the
     *              different days this alarm may fire. For example, if an Alarm should trigger
     *              on Monday, Wednesday, and Friday, then this function will return an ArrayList
     *              of size 3. Each element in that list will correspond to the next time that alarm
     *              should fire.
     * @return
     */
    public ArrayList<Calendar> getAlarmAsCalendarList(Calendar instance) {

        System.out.println("Processing Alarm: " + this);
        int today = instance.get(instance.DAY_OF_WEEK);
        ArrayList<Calendar> rv = new ArrayList<>();
        for(Integer day : daysAlarmShouldFire ) {
            System.out.println("Day is: " + day);
            Calendar alarmClone = isRepeating ? (Calendar)instance.clone():(Calendar)alarmTime.clone();
            alarmClone.set(alarmClone.HOUR_OF_DAY, alarmTime.get(alarmTime.HOUR_OF_DAY));
            alarmClone.set(alarmClone.MINUTE, alarmTime.get(alarmTime.MINUTE));
            alarmClone.set(alarmClone.SECOND, alarmTime.get(alarmTime.SECOND));
            alarmClone.set(alarmClone.MILLISECOND, alarmTime.get(alarmTime.MILLISECOND));
            // If the alarm isn't set for today
            if (day != alarmClone.get(alarmClone.DAY_OF_WEEK))
                alarmClone.add(Calendar.DAY_OF_WEEK, calculateDayDifference(today, day));
            rv.add(alarmClone);
        }
        System.out.println("Finished processing Alarm: " + this);
        return rv;
    }


    public ArrayList<Calendar> getAlarmAsCalendarList() {
        return getAlarmAsCalendarList(Calendar.getInstance());
    }

    /**
     * Function Name: calculateDayDifference()
     * Description: This function will return the number of days it takes to get from currentDay
     *              to future day. For example, the number of days it takes to get from Monday(2) to
     *              Tuesday(3) is 1. Likewise, the number of days it takes to get from Saturday(7)
     *              to Sunday(1) is 1.
     * @param currentDay <-- The calendar integer representation of a day
     * @param futureDay <-- The calendar integer representation of a day
     * @return
     */
    public static int calculateDayDifference( int currentDay, int futureDay ) {
        int delta = currentDay - futureDay;
        return delta < 0 ? Math.abs(delta) : 7-delta;
    }


    /**
     * Function Name: getDateAndTimeSet()
     * Description: This function returns the date and time which this alarm in a single lined
     *              string
     * @return
     */
    public String getDateAndTimeSet() {
        String hours = String.format("%02d", alarmTime.get(alarmTime.HOUR_OF_DAY));
        String minutes = String.format("%02d",alarmTime.get(alarmTime.MINUTE) );
        String year = String.format("%04d",alarmTime.get(alarmTime.YEAR));
        String month = String.format("%02d",alarmTime.get(alarmTime.MONTH));
        String day = String.format("%02d",alarmTime.get(alarmTime.DAY_OF_MONTH));
        String seconds = String.format("%02d", alarmTime.get(alarmTime.SECOND));
        return year + "-" + month + "-" + day + "|" + hours + ":" + minutes + ":" + seconds;
    }



    @Override public String toString() {
        String rv = "Alarm name: " + this.name;
        rv += "\nDays: [ ";
        for(Integer day : daysAlarmShouldFire ) {
            switch (day) {
                case 1: rv += "S ";
                    break;
                case 2: rv += "M ";
                    break;
                case 3: rv += "T ";
                    break;
                case 4: rv += "W ";
                    break;
                case 5: rv += "Th ";
                    break;
                case 6: rv += "F ";
                    break;
                case 7: rv += "S ";
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
        rv += "        Repeats: " + isRepeating;
        rv += "\nDate Alarm was set: " + year + "/" + month + "/" + day;
        //rv += "\n Condensed: " + getDateAndTimeSet();
        return rv;
    }
}
