package cse110m260t9.qralarm;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;
/**
 * Created by Michael on 2/29/2016.
 */


public class AlarmTester {
    Calendar today;
    ArrayList<Integer> sunday;
    ArrayList<Integer> monday;
    ArrayList<Integer> tuesday;
    ArrayList<Integer> wednesday;
    ArrayList<Integer> thursday;
    ArrayList<Integer> friday;
    ArrayList<Integer> saturday;
    ArrayList<Integer> everyday;

    @Before
    public void setup() {
        today = Calendar.getInstance();
        sunday = new ArrayList<>();
        sunday.add(Calendar.SUNDAY);
        monday = new ArrayList<>();
        monday.add(Calendar.MONDAY);
        tuesday = new ArrayList<>();
        tuesday.add(Calendar.TUESDAY);
        wednesday = new ArrayList<>();
        wednesday.add(Calendar.WEDNESDAY);
        thursday = new ArrayList<>();
        thursday.add(Calendar.THURSDAY);
        friday = new ArrayList<>();
        friday.add(Calendar.FRIDAY);
        saturday = new ArrayList<>();
        saturday.add(Calendar.SATURDAY);
        everyday = new ArrayList<>();
        for(int i = 1; i <= 7; i++)
            everyday.add(i);
    }

    @Test
    public void DifferenceBetweenSundayAndMonday() {
        assertEquals(Alarm.calculateDayDifference(Calendar.SUNDAY, Calendar.MONDAY), 1);
    }

    @Test
    public void DifferenceBetweenSundayAndAnyday() {
        for(int i = 2; i < 8; i++ )
            assertEquals(Alarm.calculateDayDifference(Calendar.SUNDAY, i), i-1);
    }

    @Test
    public void DifferenceBetweenMondayAndTuesday() {
        assertEquals(Alarm.calculateDayDifference(Calendar.MONDAY, Calendar.TUESDAY), 1);
    }
    @Test
    public void DifferenceBetweenMondayAndAnyday() {
        for(int i = 3; i < 8; i++ )
            assertEquals(Alarm.calculateDayDifference(Calendar.MONDAY, i), i-2);
        assertEquals(Alarm.calculateDayDifference(Calendar.MONDAY, Calendar.SUNDAY), 6);
    }

    @Test
    public void DifferenceBetweenSaturdayAndAnyday() {
        for(int i = 1; i < 7; i++ )
            assertEquals(i, Alarm.calculateDayDifference(Calendar.SATURDAY, i));
    }

    @Test
    public void TestPurgeAlarms() {
        today.add(Calendar.DAY_OF_WEEK, -27);
        Alarm alm = new Alarm("Test", today, sunday, false);
        alm.purgeOldAlarms();
        assertTrue( alm.broadcastTimes.size() == 0 );
    }

    @Test
    public void TestPurgeMultipleAlarms() {
        today.add(Calendar.DAY_OF_WEEK, -7);
        Alarm alm = new Alarm("Foo", today, everyday, false);
        alm.purgeOldAlarms();
        assertTrue(alm.broadcastTimes.size() == 0);
    }



    @Test
    public void TestAlarmSetOnMonday() {
        Calendar mon = GetCalendarForDay(Calendar.MONDAY);
        Alarm alm = new Alarm("Monday", today, monday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(mon.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestAlarmSetOnTuesday() {
        Calendar tue = GetCalendarForDay(Calendar.TUESDAY);
        Alarm alm = new Alarm("Tuesday", today, tuesday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(tue.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestAlarmSetOnWednesday() {
        Calendar wed = GetCalendarForDay(Calendar.WEDNESDAY);
        Alarm alm = new Alarm("Wednesday", today, wednesday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(wed.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestAlarmSetOnThursday() {
        Calendar thurs = GetCalendarForDay(Calendar.THURSDAY);
        Alarm alm = new Alarm("Thursday", today, thursday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(thurs.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestAlarmSetOnFriday() {
        Calendar fri = GetCalendarForDay(Calendar.FRIDAY);
        Alarm alm = new Alarm("Friday", today, friday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(fri.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestAlarmSetOnSaturday() {
        Calendar tue = GetCalendarForDay(Calendar.SATURDAY);
        Alarm alm = new Alarm("Saturday", today, saturday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(tue.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestAlarmSetOnSunday() {
        Calendar tue = GetCalendarForDay(Calendar.SUNDAY);
        Alarm alm = new Alarm("Sunday", today, sunday, false);
        ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList();
        assertEquals(tue.getTimeInMillis(), calendars.get(0).getTimeInMillis());
    }

    @Test
    public void TestPurgeOnReserialization() {
        ArrayList<Integer> todayL = new ArrayList<>();
        todayL.add(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Alarm alm = new Alarm("Moo", today, todayL, false);
        assertEquals(1,alm.getAlarmAsCalendarList().size());
        byte[] bytes = alm.getSerializedBytes();
        Alarm newAlm = Alarm.fromSerializedBytes(bytes);
        assertEquals(0,newAlm.getAlarmAsCalendarList().size());
    }


    @Test
    public void TestRepeatingAlarm() {
        int day = today.get(Calendar.DAY_OF_WEEK);
        Calendar mon = (Calendar)today.clone();
        if(day != Calendar.MONDAY)
            mon.add(Calendar.DAY_OF_WEEK, Alarm.calculateDayDifference(day, Calendar.MONDAY));
        Alarm alm = new Alarm("Monday", today, monday, true);
        // Test for all the weeks in the year
        for( int week = 1; week < 52; week++) {
            mon.add(Calendar.DAY_OF_WEEK, 7);
            Calendar futureDay = Calendar.getInstance();
            futureDay.add(Calendar.DAY_OF_WEEK, 7 * week );
            ArrayList<Calendar> calendars = alm.getAlarmAsCalendarList(futureDay);
            System.out.println(mon);
            System.out.println(calendars.get(0));
            assertEquals(mon.getTimeInMillis(), calendars.get(0).getTimeInMillis());
        }

    }

    private Calendar GetCalendarForDay( int dayOfWeek ) {
        int day = today.get(Calendar.DAY_OF_WEEK);
        Calendar rv = (Calendar)today.clone();
        if(day != dayOfWeek)
            rv.add(Calendar.DAY_OF_WEEK, Alarm.calculateDayDifference(day, dayOfWeek));
        return rv;
    }

}
