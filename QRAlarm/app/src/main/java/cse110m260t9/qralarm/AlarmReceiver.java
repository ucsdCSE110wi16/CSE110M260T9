package cse110m260t9.qralarm;

/**
 * Created by Nid on 1/25/16.
 */
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        MainActivity.getAlarmRingText().setText("Enough Rest. Do Work Now!");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();

        //TODO: review this section. this is a sketch way to start an activity
        //turn on QRScannerActivity
        Intent scannerIntent = new Intent(context, QRScannerActivity.class);
        scannerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scannerIntent);
    }
}
