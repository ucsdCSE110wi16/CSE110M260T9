package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditAlarm extends AppCompatActivity {
    public RingtoneManager ringtoneManager;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm);

        if (this.ringtoneManager == null) {
            this.ringtoneManager = new RingtoneManager(this);
            this.ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        }
        this.timePicker = (TimePicker)this.findViewById(R.id.alarmTimePicker);
    }

    public void cancelAlarm(View v) {
        this.finish();
    }

    public void saveAlarm(View v) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, this.timePicker.getCurrentMinute());
        System.out.println(calendar);
        long triggerAtMillis = calendar.getTimeInMillis();
        PendingIntent operation = PendingIntent.getBroadcast(
                this, 0, new Intent(this, AlarmReceiver.class), 0);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);

        System.out.println( triggerAtMillis );


        // Return to the MainActivity
        Intent returnIntent = new Intent(this, MainActivity.class);
        setResult(MyConstants.NEW_ALARM_SUCCESSFULLY_SET, returnIntent);
        this.finish();
    }
}
