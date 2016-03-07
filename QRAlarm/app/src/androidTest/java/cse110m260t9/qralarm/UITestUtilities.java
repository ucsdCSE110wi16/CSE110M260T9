package cse110m260t9.qralarm;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by itstehkman on 3/6/16.
 */
public class UITestUtilities {
    private static final String CSE110M260T9_QRALARM
            = "cse110m260t9.qralarm";
    private static final int LAUNCH_TIMEOUT = 2000;
    private static UiDevice mDevice;
    private static final String turnAlarmOffID = "cse110m260t9.qralarm:id/turnAlarmOff";
    private static final String newAlarmButtonID = "cse110m260t9.qralarm:id/newAlarmButton";
    private static final String saveAlarmButtonID = "cse110m260t9.qralarm:id/saveButtonAlarm";
    private static final String mainPageNavList = "cse110m260t9.qralarm:id/navList";
    private static final String mapsYes = "cse110m260t9.qralarm:id/buttonYes";


    public static void startMainActivityFromHomeScreen(UiDevice device) {

        mDevice = device;
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(CSE110M260T9_QRALARM);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(CSE110M260T9_QRALARM).depth(0)),
                LAUNCH_TIMEOUT);
    }

    public static void turnOffAlarm() {
        waitForID(turnAlarmOffID);
        clickTurnOffAlarm();
        waitForID(newAlarmButtonID);
    }

    public static void createAlarmNMinutesFromNow( int n ) {
        waitForID(newAlarmButtonID);
        clickNewAlarm();
        waitForID(saveAlarmButtonID);
        for( int i = 1; i <= n; i++ )
            clickNextMinutes(i);
        clickSave();
    }


    public static void waitForID( String id ) {
        mDevice.wait(Until.findObject(By.res(id)), 60 * 2000);
    }
    public static void clickNewAlarm() {
        UiObject okButton = mDevice.findObject(new UiSelector()
                .resourceId(newAlarmButtonID));
        // Simulate a user-click on the OK button, if found.
        clickButton(okButton);
    }

    public static void clickNextMinutes(int i) {
        Integer nextMinutes = Calendar.getInstance().get(Calendar.MINUTE) + i;
        UiObject nextMinButton = mDevice.findObject(new UiSelector()
                .packageName(CSE110M260T9_QRALARM)
                .className("android.widget.Button")
                .text(nextMinutes.toString()));
        clickButton(nextMinButton);
    }

    public static void clickSave() {
        UiObject saveButton = mDevice.findObject(new UiSelector()
                .text("Save"));
        clickButton(saveButton);
    }

    public static void clickTurnOffAlarm() {
        UiObject turnOffAlarm = mDevice.findObject(new UiSelector()
                .resourceId(turnAlarmOffID));
        clickButton(turnOffAlarm);
    }

    public static void clickButton(UiObject button) {
        try {
            if(button.exists() && button.isEnabled()){
                button.click();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setHomeLocation(){
        mDevice.click(5,5);
        Log.i("yes", "hello");
        UiObject yes = mDevice.findObject(new UiSelector()
                .resourceId(mapsYes));
        clickButton(yes);
        Log.i("yes", "hello1");
    }
}
