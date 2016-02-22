package cse110m260t9.qralarm;

import android.content.Context;

import java.io.FileInputStream;
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
            // Create the FileOutputStream
            // The contex mode private ensures this file should only be used by our app
            FileOutputStream fileOutStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            // Dump the string to a file
            fileOutStream.write(stringToSave.getBytes());
            System.out.println("Trying to save: " + stringToSave + " to file: " + fileName);
            // Close the stream
            fileOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String retrieveStringFromFile( String fileName, Context ctx ) {
        String retVal = "";
        try {
            // Create the FileInputStream
            FileInputStream fileInStream = ctx.openFileInput(fileName);
            // Grab a byte from the file stream and append it to the end of retVal
            // -1 Signifies the EOF so we stop reading there
            for( int i = fileInStream.read(); i != -1; i = fileInStream.read() ) {
                retVal += (char) i;
            }
            // Close the stream
            fileInStream.close();
            // Error handling
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return retVal;
    }
}
