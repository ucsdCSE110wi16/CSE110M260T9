package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.AlarmManager.*;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Timer;

public class Snooze extends AppCompatActivity {

    public void goSnooze(Context ctx) {

        PendingIntent operation = PendingIntent.getBroadcast(
                ctx, 0, new Intent(ctx, AlarmReceiver.class), 0);
        Calendar calendar = Calendar.getInstance();
        long triggerAtMillis = calendar.getTimeInMillis() + MyConstants.SNOOZE_TIME;
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis , operation);

    }
}
