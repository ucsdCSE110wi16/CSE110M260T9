package com.cchulo.thecompscientist.locationexample;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String flag = "New Location";

    Location location;

    TextView textDisplay;
    Button buttonCurrLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDisplay = (TextView) findViewById(R.id.textViewLocationDisplay);
        buttonCurrLocation = (Button) findViewById(R.id.buttonShowLocation);

        if(location == null){

            textDisplay.setText("Location not set");

            buttonCurrLocation.setEnabled(false);

        }
    }

    public void buttonCurrentLocation(View v){

    }

    public void buttonSetNewLocation(View v){

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(flag, true);
        startActivity(intent);
    }
}
