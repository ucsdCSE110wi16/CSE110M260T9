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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by itstehkman on 3/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorWakeScreenTest {
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UITestUtilities.startMainActivityFromHomeScreen(mDevice);
    }

    @Test
    public void testNewAlarmWakesScreen() {
        UITestUtilities.createAlarmNMinutesFromNow(1);

        try { mDevice.sleep(); }
        catch(Exception e){ e.printStackTrace(); }

        UITestUtilities.turnOffAlarm();
    }

}
