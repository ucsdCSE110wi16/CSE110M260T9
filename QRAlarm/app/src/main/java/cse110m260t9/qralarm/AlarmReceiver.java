package cse110m260t9.qralarm;

/**
 * Created by Nid on 1/25/16.
 */
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    private static PowerManager.WakeLock wakeLock;

    @Override
    public void onReceive(final Context context, Intent intent) {
        wakeLockAcquire(context);

        //turn on QRScannerActivity
        Intent snoozeChoice = new Intent(context, SnoozeChoice.class);
        snoozeChoice.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(snoozeChoice);

        wakeLockRelease();
    }

    public static void wakeLockAcquire(Context ctx) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "wakeLock");
        wakeLock.acquire();
    }

    public static void wakeLockRelease() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }

}
