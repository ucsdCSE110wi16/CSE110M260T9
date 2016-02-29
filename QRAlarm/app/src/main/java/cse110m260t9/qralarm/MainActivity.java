package cse110m260t9.qralarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //ListView for our Navigation Drawer
    private ListView mDrawerList;

    //Array Adapter to store our options for Nav Drawer
    private ArrayAdapter<String> mAdapter;

    //Toggle to pull up Nav Drawer
    private ActionBarDrawerToggle mDrawerToggle;

    //set the layout for our Nav Drawer
    private DrawerLayout mDrawerLayout;

    //private string variable to store our app's name
    private String mActivityTitle;

    //private variable used to store string version of LatLng location (this is temporary... don't
    //freak out)
    private String stringLocation;



    public enum EOptions {
        E_OPTIONS_SET_HOME("Set Home Location", 0),
        E_OPTIONS_SHOW_HOME("Show Home Location", 1);

        private final String text;
        private final int id;

        private EOptions(final String text, int id) {
            this.text = text;
            this.id = id;
        }

        @Override
        public String toString() {
            return text;
        }

        public int getId(){
            return id;
        }

        private static final int size = EOptions.values().length;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        initNavDrawer();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newAlarm(View v) {
        this.startActivityForResult(new Intent(this, EditAlarm.class), MyConstants.NEW_ALARM_ACTIVITY);
    }

    public void clearAlarms(View v) {
        //Alarm.deleteAllAlarms(this);
    }

    public void dumpAlarms(View v) {
        /**EditText txt = (EditText)findViewById(R.id.testDisplay);
        String acc = "";
        for (Long i : AlarmIO.getAlarms(this)) {
            acc += "| " + i.toString();
        }
        txt.setText(acc);*/
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cse110m260t9.qralarm/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cse110m260t9.qralarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //when we return from maps when we set a new location, we check to see if it returned
        //a code of 1...
        switch (resultCode) {
            case MyConstants.LOCATION_SUCCESSFULLY_SET:
                //store the string of this new location
                stringLocation = data.getStringExtra(MyConstants.CURR_LOCATION);
                FileIO.writeLocationToFile(stringLocation, this);
                break;
            case MyConstants.NEW_ALARM_SUCCESSFULLY_SET:
                Toast.makeText(MainActivity.this,
                        MyConstants.ALARM_SAVED_STR, Toast.LENGTH_SHORT).show();
                break;
        }
        //System.out.println("Inside onActivityResult -- Result Code: " + resultCode);
    }

    public void initNavDrawer(){
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();

        stringLocation = FileIO.getLocationFromFile(this);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (id == EOptions.E_OPTIONS_SHOW_HOME.getId()) {
                    if(!stringLocation.isEmpty()){

                        //we make a new intent of going into Maps
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                        //raise a flag that we are not setting a new location
                        intent.putExtra(MyConstants.NEW_LOCATION, false);

                        //raise a flag that we are sending a string location
                        intent.putExtra(MyConstants.CURR_LOCATION, stringLocation);

                        //... and off we go into maps!
                        startActivity(intent);

                    } else{
                        Toast.makeText(MainActivity.this,
                                "Location not set", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(id == EOptions.E_OPTIONS_SET_HOME.getId()) {

                    //intent to go into maps...
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                    //raise a flag that we are setting a new location...
                    intent.putExtra(MyConstants.NEW_LOCATION, true);

                    //...and off we go into maps! But expecting a result back....
                    startActivityForResult(intent, MyConstants.LOCATION_SUCCESSFULLY_SET);

                }
            }
        });

        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    //Helper function to help us set up our NavDrawer
    private void addDrawerItems() {
        String[] listArray = new String[EOptions.size];
        for(int i = 0; i < listArray.length; i++){
            listArray[i] = EOptions.values()[i].toString();
        }
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Options");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {

                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

}
