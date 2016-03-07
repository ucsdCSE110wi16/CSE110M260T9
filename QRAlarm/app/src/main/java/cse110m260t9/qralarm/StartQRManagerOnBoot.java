package cse110m260t9.qralarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Michael on 3/6/2016.
 */
public class StartQRManagerOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            QRAlarmManager.reloadAlarms(context);

            MapsActivity.attemptToRestartProximityAlert(context);
        }
    }

}
