package cse110m260t9.qralarm;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Michael on 2/28/2016.
 */
public class AlarmIO {
    public static String ALARMSDIR = "Alarms";

    public static void createAlarmsDir(Context ctx) {
        File alarmDir = getAlarmsDir(ctx);
        alarmDir.mkdirs();
    }

    public static Alarm loadTestAlarm( Context ctx, Alarm alm ) {
        return null;
    }

    public static void saveTestAlarm( Context ctx, Alarm alm ) {
        createAlarmsDir(ctx);
        File alarmDir = getAlarmsDir(ctx);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(alarmDir, "Test.alarm"));
            fos.write(alm.getSerializedString());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static File getAlarmsDir( Context ctx ) {
        return new File(ctx.getFilesDir(), ALARMSDIR );
    }
}
