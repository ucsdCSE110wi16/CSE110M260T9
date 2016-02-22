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

    // These are methods to save/load the user's QR Hash to permanent storage


    public static void writeQRHashToFile(String qrHash, Context ctx) {
        saveString(MyConstants.QR_HASH_FILE, qrHash, ctx);
    }

    public static String getQRHashFromFile(Context ctx) {
        return retrieveStringFromFile(MyConstants.QR_HASH_FILE, ctx);
    }

    // These are methods to save/load the user's home location to permanent storage


    /**
     * Function Name: writeLocationToFile()
     * Description: Saves location to local file storage
     * --- NOTE --- ctx will most likely by 'this' from a class that calls this function
     * @param location
     * @param ctx
     */
    public static void writeLocationToFile(String location, Context ctx) {
        saveString(MyConstants.LOCATION_FILE, location, ctx);
    }

    /**
     * Function Name: getLocationFromFile()
     * Description: Loads location from local file storage and returns it as a string
     * --- NOTE --- ctx will most likely by 'this' from a class that calls this function
     * @param ctx
     * @return
     */
    public static String getLocationFromFile(Context ctx) {
        return retrieveStringFromFile(MyConstants.LOCATION_FILE, ctx);
    }


    // These are the generic methods to save/load any string to any file

    /**
     * Function Name: saveString()
     * Description: Saves stringToSave to fileName
     * --- NOTE --- ctx will most likely by 'this' from a class that calls this function
     * @param fileName
     * @param stringToSave
     * @param ctx
     */
    public static void saveString( String fileName, String stringToSave, Context ctx ) {
        try {
            // Create the FileOutputStream
            // The context mode private ensures this file should only be used by our app
            FileOutputStream fileOutStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            // Dump the string to a file
            fileOutStream.write(stringToSave.getBytes());
            // Close the stream
            fileOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function Name: retrieveStringFromFile()
     * Description: This function returns the contents of fileName as a string
     * --- NOTE --- ctx will most likely by 'this' from a class that calls this function
     * @param fileName
     * @param ctx
     * @return
     */
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
