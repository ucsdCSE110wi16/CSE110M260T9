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


    public static File[] getAllAlarmFiles( Context ctx ) {
        File alarmDir = getAlarmsDir(ctx);
        return alarmDir.listFiles();

    }

    public static void deleteAllAlarms( Context ctx ) {
        System.out.println("Deleting files");
        for(File f : getAllAlarmFiles(ctx)) {
            System.out.println("Filename: " + f.getName());
            f.delete();
        }
    }
    public static Alarm loadTestAlarm( Context ctx ) {
        File alarmDir = getAlarmsDir(ctx);
        File inFile = new File(alarmDir, "Test.alarm");
        byte[] bytes = FileIO.retrieveByteArrayFromFile( inFile, ctx);
        return Alarm.fromSerializedString(bytes);
    }

    public static void saveTestAlarm( Context ctx, Alarm alm ) {
        File alarmDir = getAlarmsDir(ctx);
        File outFile = new File(alarmDir, "Test2.alarm");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFile);
            fos.write(alm.getSerializedString());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static File getAlarmsDir( Context ctx ) {
        File alarmDir = new File(ctx.getFilesDir(), ALARMSDIR );
        alarmDir.mkdirs();
        return alarmDir;
    }
}
