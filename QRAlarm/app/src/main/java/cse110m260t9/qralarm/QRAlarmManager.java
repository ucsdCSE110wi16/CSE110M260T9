package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Michael on 2/29/2016.
 */
public class QRAlarmManager extends IntentService{
    private ArrayList<Alarm> alarmList;
    private ArrayList<Integer> broadCastIDs;

    public static final String ALARM_KEY = "QR_ALARM_MANAGER_ALARM_KEY";
    public static final String DELETE_ALARMS = "QR_ALARM_MANAGER_DELETE_ALL";

    /**
     * Default Constructor
     * -- DO NOT CALL DIRECTLY --
     */
    public QRAlarmManager() {
        super("QRAlarmManager");
    }

    // ------------- Overloaded Functions -------------

    @Override public void onCreate() {
        super.onCreate();
        alarmList = new ArrayList<>();
        broadCastIDs = new ArrayList<>();
    }
    @Override public void onHandleIntent(Intent intent) {
        if( intent.hasExtra(ALARM_KEY)) {
            Alarm alarm = (Alarm) intent.getSerializableExtra(ALARM_KEY);
            _registerAlarm(this, alarm);
        }
        if( intent.hasExtra(DELETE_ALARMS))
            _clearAllAlarms(this);
    }
    @Override public int onStartCommand(Intent intent, int flag, int startID) {
        onHandleIntent(intent);
        return 0;
    }

    // ------------- Public Static Functions -------------

    /**
     * Function Name: clearAllAlarms()
     * Description: This function clears all the Pending Alarm Intnets from the built-in
     *              Android Alarm Manager.
     * @param ctx
     */
    public static void clearAllAlarms(Context ctx) {
        Intent alarmManager = new Intent(ctx, QRAlarmManager.class);
        alarmManager.putExtra(QRAlarmManager.DELETE_ALARMS, true);
        ctx.startService(alarmManager);
    }

    /**
     * Function Name: registerAlarm()
     * Description: This function registers an alarm with the QRAlarmManager service
     * @param ctx
     * @param alarm
     */
    public static void registerAlarm(Context ctx, Alarm alarm ) {
        Intent alarmManager = new Intent(ctx, QRAlarmManager.class);
        alarmManager.putExtra(QRAlarmManager.ALARM_KEY, alarm);
        ctx.startService(alarmManager);
    }

    /**
     * Function Name: reloadAlarms()
     * Description: This function loads all saved alarms and registers them with the QRAlarmManager
     *              service
     * @param ctx
     */
    public static void reloadAlarms(Context ctx ) {
        ArrayList<Alarm> alarms = AlarmIO.getAllAlarms(ctx);
        AlarmIO.deleteAllAlarms(ctx);
        for(Alarm alm : alarms )
            if(alm.getAlarmAsCalendarList().size() > 0)
                registerAlarm(ctx, alm);

    }

    /**
     * Function Name: stopService()
     * Description: This function stops the QRAlarmManager service from running
     * WARNING: This function should only be called when the application is about to close
     *          Calling this function prematurely will result in loosing references to cached alarms
     * @param ctx
     */
    public static void stopService(Context ctx) {
        ctx.stopService(new Intent(ctx, QRAlarmManager.class));
    }



    // ------------- Private Functions -------------


    /**
     * Function Name: _registerAlarm()
     * Description: This function iterates over the list of all alarms and registers them with
     *              the alarm manager through the overloaded registerAlarm function.
     * @param ctx
     */
    private void _registerAlarm(Context ctx, Alarm alarm) {

        if(alarm == null)
            return;
        alarmList.add(alarm);
        for(Calendar cal : alarm.getAlarmAsCalendarList()) {
            _registerAlarm(ctx, cal);
            alarm.broadcastTimes.add(cal.getTimeInMillis());
        }
        AlarmIO.saveAlarm(this, alarm);

    }

    /**
     * Function Name: _registerAlarm()
     * Description: This function registers an Alarm to fire with the built-in Android Alarm
     *              Manager. The time and ID by which this alarm will fire is determined by
     *              the Calendar time.
     * @param ctx
     * @param time
     */
    private void _registerAlarm(Context ctx, Calendar time ) {
        System.out.println("Registering alarm: ");
        System.out.println(time);
        long triggerAtMillis = time.getTimeInMillis();
        int broadCastID = (int) (triggerAtMillis % Integer.MAX_VALUE);
        PendingIntent operation = PendingIntent.getBroadcast(
                ctx, broadCastID, new Intent(ctx, AlarmReceiver.class), 0);
        AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        broadCastIDs.add(broadCastID);
    }

    /**
     * Function Name: _clearAllAlarms()
     * Description: This function clears all the Pending Alarm Intnets from the built-in
     *              Android Alarm Manager
     * @param ctx
     */
    private void _clearAllAlarms(Context ctx) {
        System.out.println("Before clearing Broadcast IDs: " + broadCastIDs);
        for(Integer id : broadCastIDs ) {
            PendingIntent operation = PendingIntent.getBroadcast(
                    ctx, id, new Intent(ctx, AlarmReceiver.class), 0);
            AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(operation);
            Toast.makeText(ctx,
                    "Cleared Alarms", Toast.LENGTH_SHORT).show();
        }
        broadCastIDs.clear();
        alarmList.clear();
    }




}
