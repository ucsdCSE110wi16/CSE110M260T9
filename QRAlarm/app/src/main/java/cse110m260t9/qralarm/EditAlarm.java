package cse110m260t9.qralarm;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

public class EditAlarm extends AppCompatActivity {
    private static final int RINGTONE_ACTIVITY_ID = 0;

    private Calendar calendar;
    private TimePicker timePicker;
    private QRAlarmManager qrAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm);

        this.calendar = Calendar.getInstance();
        this.toggleButtonForToday();
        this.timePicker = (TimePicker)this.findViewById(R.id.alarmTimePicker);
        qrAlarmManager = new QRAlarmManager();
    }

    private void toggleButtonForToday() {
        ToggleButton button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmSunday);
        switch (this.calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmSunday);
                break;
            case Calendar.MONDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmMonday);
                break;
            case Calendar.TUESDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmTuesday);
                break;
            case Calendar.WEDNESDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmWednesday);
                break;
            case Calendar.THURSDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmThursday);
                break;
            case Calendar.FRIDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmFriday);
                break;
            case Calendar.SATURDAY:
                button = (ToggleButton)this.findViewById(R.id.toggleButtonAlarmSaturday);
                break;
        }
        button.setChecked(true);
    }

    public void cancelAlarm(View v) {
        this.setResult(Activity.RESULT_CANCELED);
        this.finish();
    }

    public void saveAlarm(View v) {
        Alarm alarm = createAlarmFromUserInput(v);
        System.out.println(alarm);
        QRAlarmManager.registerAlarm(this, alarm);

        // Return to the MainActivity
        Intent returnIntent = new Intent(this, MainActivity.class);
        this.setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    public void listAlarmTones(View v) {
        Intent rtIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        rtIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                Settings.System.DEFAULT_ALARM_ALERT_URI);
        rtIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
                Settings.System.DEFAULT_ALARM_ALERT_URI);
        rtIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        rtIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        rtIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        this.startActivityForResult(rtIntent, EditAlarm.RINGTONE_ACTIVITY_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case EditAlarm.RINGTONE_ACTIVITY_ID:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                }
                break;
            default:
                break;
        }
    }

    private Alarm createAlarmFromUserInput(View v) {
        // Get the alarm's name
        EditText nameField = (EditText)findViewById(R.id.alarmTextLabel);
        String name = nameField.getText().toString();
        // Does the alarm repeat?
        CheckBox isRepeatingCheckBox = (CheckBox) findViewById(R.id.alarmCheckRepeatWeekly);
        boolean isRepeating = isRepeatingCheckBox.isChecked();
        // Create the calendar to represent time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, this.timePicker.getCurrentMinute());
        ArrayList<Integer> days = new ArrayList<>();
        whichDays(days);
        return new Alarm(name, calendar, days, isRepeating);
    }

    private void whichDays(ArrayList<Integer> days) {
        LinearLayout llh = (LinearLayout)this.findViewById(R.id.llhDotw);
        for (int i = 0; i < llh.getChildCount(); ++i) {
            View child = llh.getChildAt(i);
            switch (child.getId()) {
                case R.id.toggleButtonAlarmSunday:
                    days.add(Calendar.SUNDAY);
                    break;
                case R.id.toggleButtonAlarmMonday:
                    days.add(Calendar.MONDAY);
                    break;
                case R.id.toggleButtonAlarmTuesday:
                    days.add(Calendar.TUESDAY);
                    break;
                case R.id.toggleButtonAlarmWednesday:
                    days.add(Calendar.WEDNESDAY);
                    break;
                case R.id.toggleButtonAlarmThursday:
                    days.add(Calendar.THURSDAY);
                    break;
                case R.id.toggleButtonAlarmFriday:
                    days.add(Calendar.FRIDAY);
                    break;
                case R.id.toggleButtonAlarmSaturday:
                    days.add(Calendar.SATURDAY);
                    break;
                default:
                    break;
            }
        }
    }
}
