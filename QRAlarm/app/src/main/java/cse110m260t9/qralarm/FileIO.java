package cse110m260t9.qralarm;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Michael on 2/21/2016.
 * This class provides a wrapper for the Android FileIO methods
 */
public class FileIO {
    public static void saveString( String fileName, String stringToSave, Context ctx ) {
        try {
            FileOutputStream fileOutStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutStream.write(stringToSave.getBytes());
            fileOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
