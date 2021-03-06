package cse110m260t9.qralarm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;


import java.util.Calendar;

import dalvik.annotation.TestTarget;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Michael on 3/6/2016.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorCreateAlarmTest {

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UITestUtilities.startMainActivityFromHomeScreen(mDevice);
    }

    @Test
    public void testClickNewAlarm() {
        UITestUtilities.createAlarmNMinutesFromNow(1);
        UITestUtilities.turnOffAlarm();
    }

}
