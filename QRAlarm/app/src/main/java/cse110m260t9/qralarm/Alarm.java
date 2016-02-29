package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michael on 2/28/2016.
 */
public class Alarm {
    private ArrayList<Integer> daysAlarmShouldFire;
    private boolean isRepeating;
    private Calendar alarmTime;
    public Alarm( Calendar calendar, ArrayList<Integer> days, boolean shouldRepeat ) {
        daysAlarmShouldFire = days;
        isRepeating = shouldRepeat;
        alarmTime = calendar;
    }

    /**
     * Function Name: registerAlarm()
     * Description: This function iterates over the list of all alarms and registers them with
     *              the alarm manager through the overloaded registerAlarm function.
     * @param ctx
     */
    public void registerAlarm(Context ctx) {
        int today = Calendar.getInstance().DAY_OF_WEEK;
        for(Integer day : daysAlarmShouldFire ) {
            Calendar alarmClone = (Calendar)alarmTime.clone();
            // If the alarm is set for today
            if( day.intValue() == alarmTime.DAY_OF_WEEK) {
                // If the alarm should occur later in the week
                if(Calendar.getInstance().getTimeInMillis() >= alarmTime.getTimeInMillis() )
                    alarmClone.add(Calendar.DAY_OF_WEEK, 7);
                // If this condition doesn't hold, then we can assume the alarm will happen later
                // in the day. The alarm clone should already be set to this value
            }
            // If the alarm is set to happen later during the week
            else
                alarmClone.add(Calendar.DAY_OF_WEEK, calculateDayDifference(today, day) );
            registerAlarm(ctx, alarmClone);
        }

    }

    public void registerAlarm(Context ctx, Calendar time ) {
        long triggerAtMillis = time.getTimeInMillis();
        PendingIntent operation = PendingIntent.getBroadcast(
                ctx, 0, new Intent(ctx, AlarmReceiver.class), 0);
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
    }


    private int calculateDayDifference( int currentDay, int futureDay ) {
        int delta = currentDay - futureDay;
        return delta < 0 ? Math.abs(delta) : 7-delta;
    }
}
