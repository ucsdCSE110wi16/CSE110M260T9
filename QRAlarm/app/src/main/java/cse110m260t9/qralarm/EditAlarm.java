package cse110m260t9.qralarm;

import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

public class EditAlarm extends AppCompatActivity {
    private TimePicker timePicker;
    private QRAlarmManager qrAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_alarm);

        this.timePicker = (TimePicker)this.findViewById(R.id.alarmTimePicker);
        toggleTodaysButton();
        qrAlarmManager = new QRAlarmManager();
    }

    public void cancelAlarm(View v) {
        this.finish();
    }

    public void saveAlarm(View v) {

        Alarm alarm = createAlarmFromUserInput(v);
        System.out.println(alarm);
        QRAlarmManager.registerAlarm(this,alarm);


        // Return to the MainActivity
        Intent returnIntent = new Intent(this, MainActivity.class);
        setResult(MyConstants.NEW_ALARM_SUCCESSFULLY_SET, returnIntent);
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
        this.startActivity(rtIntent);
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
        return new Alarm(name, calendar, whichDays(), isRepeating);
    }

    public void toggleTodaysButton() {
        Calendar calendar = Calendar.getInstance();
        ToggleButton button = (ToggleButton) findViewById(R.id.toggleButtonAlarmSunday);
        //System.out.println("Today is: " + calendar.get(calendar.DAY_OF_WEEK));
        //System.out.println("Monday is: " + calendar.MONDAY);
        //System.out.println(calendar);
        switch (calendar.get(calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmSunday);
                break;
            case Calendar.MONDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmMonday);
                break;
            case Calendar.TUESDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmTuesday);
                break;
            case Calendar.WEDNESDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmWednesday);
                break;
            case Calendar.THURSDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmThursday);
                break;
            case Calendar.FRIDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmFriday);
                break;
            case Calendar.SATURDAY:
                button = (ToggleButton) findViewById(R.id.toggleButtonAlarmSaturday);
                break;
        }
        button.setChecked(true);
    }


    public ArrayList<Integer> whichDays() {
        ArrayList<Integer> result = new ArrayList<>();
        ToggleButton sunday    = (ToggleButton) findViewById(R.id.toggleButtonAlarmSunday);
        ToggleButton monday    = (ToggleButton) findViewById(R.id.toggleButtonAlarmMonday);
        ToggleButton tuesday   = (ToggleButton) findViewById(R.id.toggleButtonAlarmTuesday);
        ToggleButton wednesday = (ToggleButton) findViewById(R.id.toggleButtonAlarmWednesday);
        ToggleButton thursday  = (ToggleButton) findViewById(R.id.toggleButtonAlarmThursday);
        ToggleButton friday    = (ToggleButton) findViewById(R.id.toggleButtonAlarmFriday);
        ToggleButton saturday  = (ToggleButton) findViewById(R.id.toggleButtonAlarmSaturday);
        if(sunday.isChecked())
            result.add(new Integer(Calendar.SUNDAY));
        if(monday.isChecked())
            result.add(new Integer(Calendar.MONDAY));
        if(tuesday.isChecked())
            result.add(new Integer(Calendar.TUESDAY));
        if(wednesday.isChecked())
            result.add(new Integer(Calendar.WEDNESDAY));
        if(thursday.isChecked())
            result.add(new Integer(Calendar.THURSDAY));
        if(friday.isChecked())
            result.add(new Integer(Calendar.FRIDAY));
        if(saturday.isChecked())
            result.add(new Integer(Calendar.SATURDAY));
        return result;
    }

}
