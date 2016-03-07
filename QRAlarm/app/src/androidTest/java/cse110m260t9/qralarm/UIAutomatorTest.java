package cse110m260t9.qralarm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;


import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Michael on 3/6/2016.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest {
    private static final String CSE110M260T9_QRALARM
            = "cse110m260t9.qralarm";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

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

    @Test
    public void testClickNewAlarm() {
        clickNewAlarm();
        clickNextMinutes();
        clickSave();
    }

    private void clickNewAlarm() {
        UiObject okButton = mDevice.findObject(new UiSelector()
                .text("New Alarm"));
        // Simulate a user-click on the OK button, if found.
       clickButton(okButton);
    }

    private void clickNextMinutes() {
        Integer nextMinutes = Calendar.getInstance().get(Calendar.MINUTE) + 1;
        UiObject nextMinButton = mDevice.findObject(new UiSelector()
                .packageName(CSE110M260T9_QRALARM)
                .className("android.widget.Button")
                .text(nextMinutes.toString()));
        clickButton(nextMinButton);
    }

    private void clickSave() {
        UiObject saveButton = mDevice.findObject(new UiSelector()
                .text("Save"));
        clickButton(saveButton);
    }

    private void clickButton(UiObject button) {
        try {
            if(button.exists() && button.isEnabled()){
                button.click();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
