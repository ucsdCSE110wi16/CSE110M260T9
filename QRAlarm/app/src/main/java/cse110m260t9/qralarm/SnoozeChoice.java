package cse110m260t9.qralarm;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Nid on 2/28/16.
 */
public class SnoozeChoice extends AppCompatActivity {


    public static Ringtone ringtone;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snoozechoice);
        //initiate ringtone
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();

        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);

        if(MainActivity.IsAtHome()) {

            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent turnItOff = new Intent(SnoozeChoice.this, QRScannerActivity.class);

                    SnoozeChoice.this.startActivity(turnItOff);

                }
            });
        } else {

            b1.setText("Turn off Alarm");

            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ringtone.stop();

                    startActivity(new Intent(SnoozeChoice.this, MainActivity.class));

                }
            });
        }

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snooze bro = new Snooze();
                bro.goSnooze(SnoozeChoice.this);
                Intent goBack = new Intent(SnoozeChoice.this, MainActivity.class);
                ringtone.stop();
                SnoozeChoice.this.startActivity(goBack);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SnoozeChoice Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cse110m260t9.qralarm/http/host/path")
        );
        AppIndex.AppIndexApi.start(mClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SnoozeChoice Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cse110m260t9.qralarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(mClient, viewAction);
        mClient.disconnect();
    }
}
