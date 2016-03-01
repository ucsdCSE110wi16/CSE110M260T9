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
    private final int ALARM_BUFFER = 2000;
    public static final String ALARM_KEY = "QR_ALARM_MANAGER_ALARM_KEY";
    public static final String DELETE_ALARMS = "QR_ALARM_MANAGER_DELETE_ALL";
    private byte[] lastAlarmSerial = null;
    public QRAlarmManager() {
        super("QRAlarmManager");
    }

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
            _deleteAllAlarms(this);
        if( intent.hasExtra("Testing Serial")) {
            AlarmIO.deleteAllAlarms(this);
        }


    }

    @Override public int onStartCommand(Intent intent, int flag, int startID) {
        onHandleIntent(intent);
        return 0;
    }

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

    private void _registerAlarm(Context ctx, Calendar time ) {
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

    private void _deleteAllAlarms(Context ctx) {
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

    public static void deleteAllAlarms(Context ctx ) {
        Intent alarmManager = new Intent(ctx, QRAlarmManager.class);
        alarmManager.putExtra(QRAlarmManager.DELETE_ALARMS, true);
        ctx.startService(alarmManager);
    }

    public static void registerAlarm(Context ctx, Alarm alarm ) {
        Intent alarmManager = new Intent(ctx, QRAlarmManager.class);
        alarmManager.putExtra(QRAlarmManager.ALARM_KEY, alarm);
        ctx.startService(alarmManager);
    }

    public static void reloadAlarms(Context ctx ) {
        ArrayList<Alarm> alarms = AlarmIO.getAllAlarms(ctx);
        AlarmIO.deleteAllAlarms(ctx);
        for(Alarm alm : alarms )
            registerAlarm(ctx, alm);

    }

    public static void registerSavedAlarm(Context ctx ) {
        Intent alarmManager = new Intent(ctx, QRAlarmManager.class);
        alarmManager.putExtra("Testing Serial", true);
        ctx.startService(alarmManager);
    }


    public static void stopSerivce(Context ctx) {
        ctx.stopService(new Intent(ctx, QRAlarmManager.class));
    }


}
