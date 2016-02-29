package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michael on 2/29/2016.
 */
public class QRAlarmManager extends IntentService{
    private ArrayList<Alarm> alarmList;
    private ArrayList<Integer> broadCastIDs;
    private final int ALARM_BUFFER = 2000;
    public static final String ALARM_KEY = "QR_ALARM_MANAGER_ALARM_KEY";
    public QRAlarmManager() {
        super("QRAlarmManager");
    }

    @Override public void onCreate() {
        super.onCreate();
        alarmList = new ArrayList<>();
        broadCastIDs = new ArrayList<>();
    }

    @Override public void onHandleIntent(Intent intent) {
        Alarm alarm = (Alarm) intent.getSerializableExtra(ALARM_KEY);
        registerAlarm(this, alarm);
    }

    /**
     * Function Name: registerAlarm()
     * Description: This function iterates over the list of all alarms and registers them with
     *              the alarm manager through the overloaded registerAlarm function.
     * @param ctx
     */
    public void registerAlarm(Context ctx, Alarm alarm) {
        Calendar instance = Calendar.getInstance();
        int today = instance.get(instance.DAY_OF_WEEK);
        for(Integer day : alarm.daysAlarmShouldFire ) {
            System.out.println("Day is: " + day);
            Calendar alarmClone = (Calendar)alarm.alarmTime.clone();
            // If the alarm is set for today
            if( day == alarmClone.get(alarmClone.DAY_OF_WEEK)) {
                // If the alarm should occur later in the week
                if(instance.getTimeInMillis() > alarmClone.getTimeInMillis() + ALARM_BUFFER )
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

    private void registerAlarm(Context ctx, Calendar time ) {
        System.out.println("Registering alarm: ");
        System.out.println(time);
        long triggerAtMillis = time.getTimeInMillis();
        int broadCastID = (int) triggerAtMillis % Integer.MAX_VALUE;
        PendingIntent operation = PendingIntent.getBroadcast(
                ctx, broadCastID, new Intent(ctx, AlarmReceiver.class), 0);
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        broadCastIDs.add(broadCastID);
    }

    public void deleteAllAlarms(Context ctx) {
        for(Integer id : broadCastIDs ) {
            PendingIntent operation = PendingIntent.getBroadcast(
                    ctx, id, new Intent(ctx, AlarmReceiver.class), 0);
            AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(operation);
            Toast.makeText(ctx,
                    "Cleared Alarms", Toast.LENGTH_SHORT).show();
        }
        broadCastIDs.clear();
    }


    private int calculateDayDifference( int currentDay, int futureDay ) {
        int delta = currentDay - futureDay;
        return delta < 0 ? Math.abs(delta) : 7-delta;
    }
}
