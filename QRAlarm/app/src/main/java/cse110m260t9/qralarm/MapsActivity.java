package cse110m260t9.qralarm;

/*=================================================================================================
Created By: Carlos Chulo
Email: thecompscientist.dev@gmail.com

This app is an example of storing a user's location.

Sources of Help:
-Tutorial from http://blog.teamtreehouse.com/beginners-guide-location-android
-Google Android API Manual
 ================================================================================================*/

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//This class will be used to set the location of the user
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    //private variable used to display the map
    private GoogleMap mMap;

    //private reference to access GoogleApi for location services
    private GoogleApiClient mGoogleApiClient;

    //part of the teamtreehouse tutorial, may not be needed...
    public static final String TAG = MapsActivity.class.getSimpleName();

    //Timeout set for retrieving location
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    //reference to location request
    private LocationRequest mLocationRequest;

    //flag to determine if we are setting a location...
    private boolean isSettingLocation;

    //References to design widgets/components
    private TextView textLocation;
    private Button buttonYes;
    private Button buttonNo;

    //private variable to store location
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        //get references to all the components
        textLocation = (TextView) findViewById(R.id.textNewLocation);
        buttonYes = (Button) findViewById(R.id.buttonYes);
        buttonNo = (Button) findViewById(R.id.buttonNo);

        //get reference to the intent that led us to this map...
        Intent intent = getIntent();

        //determine if we are setting a new location
        isSettingLocation = intent.getBooleanExtra(MyConstants.NEW_LOCATION, false);

        //if we are not....
        if(!isSettingLocation){

            //we will hide all unnecessary components
            textLocation.setVisibility(View.GONE);
            buttonYes.setVisibility(View.GONE);
            buttonNo.setVisibility(View.GONE);
            latLng = convertStringToLatLng(intent.getStringExtra("Location"));
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) //we can use lower if needed
                .setInterval(10 * 1000) //set interval to 10 seconds
                .setFastestInterval(1 * 1000); //set fastest interval to 1 second
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        } else {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(location == null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        } else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
            try{
                //Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e){
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    private void handleNewLocation(Location location){
        Log.d(TAG, location.toString());

        double currLat = location.getLatitude();
        double currLongt = location.getLongitude();

        if(isSettingLocation)
            latLng = new LatLng(currLat, currLongt);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng, 15));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onLocationChanged(Location location) {

        handleNewLocation(location);

    }

    public void buttonYes(View v){

        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("Location", String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
        setResult(MyConstants.RESULT_CODE, returnIntent);
        finish();
    }

    public void buttonNo(View v){
        finish();
    }

    //helper method to convert String to LatLng object
    public static LatLng convertStringToLatLng(String string){

        String[] newLocation = string.split(",");

        LatLng latLng = new LatLng(Double.parseDouble(newLocation[0]),
                Double.parseDouble(newLocation[1]));

        return latLng;
    }
}
