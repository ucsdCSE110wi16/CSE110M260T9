package cse110m260t9.qralarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by cchul on 2/29/2016.
 */
public class ProximityIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);
        if (entering) {

            MainActivity.setIsAtHome(true);
            Toast.makeText(context,
                "Entering Home", Toast.LENGTH_SHORT).show();
        }else {

            MainActivity.setIsAtHome(false);
            Toast.makeText(context,
                "Exiting Home", Toast.LENGTH_SHORT).show();
        }


    }
}
