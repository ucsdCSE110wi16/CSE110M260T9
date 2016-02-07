package cse110m260t9.qralarm;
/*=================================================================================================
Created By: Carlos Chulo
Email: thecompscientist.dev@gmail.com

This app is an example of storing a user's location.

Sources of Help:
-Tutorial from http://blog.teamtreehouse.com/beginners-guide-location-android
-Google Android API Manual
 ================================================================================================*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;

/**
 * This class will be used to drive the entire app.
 * It will be the first activity to run.
 */
public class LocationPromptActivity extends AppCompatActivity {

    //flags used for intents...
    public final static String NEW_LOCATION = "New Location";
    public final static String CURR_LOCATION = "Location";

    //private variable to store location
    private LatLng location;

    //private variable used to store string version of LatLng location (this is temporary... don't
    //freak out)
    private String stringLocation;

    //references to design components...
    private TextView textDisplay;
    private Button buttonCurrLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //store references of design widgets/components
        textDisplay = (TextView) findViewById(R.id.textViewLocationDisplay);
        buttonCurrLocation = (Button) findViewById(R.id.buttonShowLocation);

        //if my current location is null (this is our first time using this app...
        if(location == null){

            //we let the user know
            //textDisplay.setText("Location not set");

            //disable use of current location
            //buttonCurrLocation.setEnabled(false);

        }
    }

    //method called when buttonShowLocation is pressed
    public void buttonCurrentLocation(View v){

        //we make a new intent of going into Maps
        Intent intent = new Intent(this, MapsActivity.class);

        //raise a flag that we are not setting a new location
        intent.putExtra(NEW_LOCATION, false);

        //raise a flag that we are sending a string location
        intent.putExtra("Location", stringLocation);

        //... and off we go into maps!
        startActivity(intent);
    }

    //method called when buttonSetNewLocation is pressed
    public void buttonSetNewLocation(View v){

        //intent to go into maps...
        Intent intent = new Intent(this, MapsActivity.class);

        //raise a flag that we are setting a new location...
        intent.putExtra(NEW_LOCATION, true);

        //...and off we go into maps! But expecting a result back....
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //when we return from maps when we set a new location, we check to see if it returned
        //a code of 1...
        if(resultCode == 1){

            //if so it means a new location was set!
            textDisplay.setText("Location Set");

            //store the string of this new location
            stringLocation = data.getStringExtra("Location");

            //set the location
            location = convertStringToLatLng(stringLocation);

            //enable the current location button
            buttonCurrLocation.setEnabled(true);
        }
    }

    //helper method to convert String to LatLng object
    public static LatLng convertStringToLatLng(String string){

        String[] newLocation = string.split(",");

        LatLng latLng = new LatLng(Double.parseDouble(newLocation[0]),
                Double.parseDouble(newLocation[1]));

        return latLng;
    }
}

