package cse110m260t9.qralarm;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;





public class MainActivity extends FragmentActivity{

    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    private TextView timeText;
    private static TextView alarmRingText;
    public static TextView getAlarmRingText() {
        return alarmRingText;
    }
    private AlarmManager alarmManager;
    private PendingIntent pendingAlarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = (TextView)findViewById(R.id.alarmTimeLabel);
        String timeLabelText = cleanTime(timeHour,timeMinute);
        timeText.setText(timeLabelText);
        alarmRingText = (TextView)findViewById(R.id.alarmMessageLabel);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingAlarmIntent = PendingIntent.getBroadcast(MainActivity.this, AlertDialog.THEME_HOLO_DARK, myIntent, 0);

        OnClickListener listener1 = new OnClickListener() {
            public void onClick(View view) {
                saveAlarm();
            }
        };

        Button start = (Button)findViewById(R.id.button1);
        start.setOnClickListener(listener1);
        OnClickListener listener2 = new OnClickListener() {
            public void onClick(View view) {
                alarmRingText.setText("");
                cancelAlarm();
            }
        };
        Button stop = (Button)findViewById(R.id.button2);
        stop.setOnClickListener(listener2);
    }
    class SetAlarmHandler extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            timeHour = bundle.getInt(MyConstants.HOUR);
            timeMinute = bundle.getInt(MyConstants.MINUTE);
            timeText.setText(timeHour + ":" + timeMinute);
            setAlarm();
        }
    }
    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);

        //set the alarm to go off at the set time. runs pendingAlarmIntent
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingAlarmIntent);
    }

    private void cancelAlarm() {
        if (alarmManager!= null) {
            //TODO: make the music stop. currently the text goes away, but not the music
            alarmManager.cancel(pendingAlarmIntent);
        }
    }

    // Logic on start button
    public void saveAlarm() {
        alarmRingText.setText("");
        Bundle bundle = new Bundle();
        bundle.putInt(MyConstants.HOUR, timeHour);
        bundle.putInt(MyConstants.MINUTE, timeMinute);
        MyDialogFragment fragment = new MyDialogFragment(new SetAlarmHandler());
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, MyConstants.TIME_PICKER);
        transaction.commit();
    }

    private String cleanTime(int hour, int min) {
        int resHour = hour % 12;
        resHour = resHour == 0 ? 12 : resHour;
        String minute = min / 10 == 0 ? "0" + min : "" +min;
        return resHour + ":" + minute;
    }
}