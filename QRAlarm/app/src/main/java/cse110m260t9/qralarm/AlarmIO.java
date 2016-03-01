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
    public static final String ALARMSDIR = "Alarms";
    public static final String EXT = ".alm";


    public static File[] getAllAlarmFiles( Context ctx ) {
        File alarmDir = getAlarmsDir(ctx);
        return alarmDir.listFiles();
    }

    public static ArrayList<Alarm> getAllAlarms(Context ctx ) {
        File[] files = getAllAlarmFiles(ctx);
        ArrayList<Alarm> alarms = new ArrayList<>();
        for(File f : files )
            alarms.add( loadAlarm(ctx, f) );
        return alarms;
    }

    public static void deleteAllAlarms( Context ctx ) {
        System.out.println("Deleting files");
        for(File f : getAllAlarmFiles(ctx)) {
            System.out.println("Filename: " + f.getName());
            f.delete();
        }
    }
    public static Alarm loadAlarm( Context ctx, File inFile) {
        byte[] bytes = FileIO.retrieveByteArrayFromFile( inFile, ctx);
        return Alarm.fromSerializedBytes(bytes);
    }

    public static void saveAlarm( Context ctx, Alarm alm ) {
        File alarmDir = getAlarmsDir(ctx);
        File outFile = new File(alarmDir, alm.getDateAndTimeSet() + EXT );
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outFile);
            fos.write(alm.getSerializedBytes());
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
