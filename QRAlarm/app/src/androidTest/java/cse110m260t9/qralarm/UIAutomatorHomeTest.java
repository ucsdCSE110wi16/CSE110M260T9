package cse110m260t9.qralarm;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by itstehkman on 3/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorHomeTest {
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UITestUtilities.startMainActivityFromHomeScreen(mDevice);
    }

    @Test
    public void testThatBeingInHomeTriggersQRCodeAlarm() {
        UITestUtilities.setHomeLocation();
//        UITestUtilities.createAlarmNMinutesFromNow(1);
//        UITestUtilities.turnOffAlarm();
    }

}