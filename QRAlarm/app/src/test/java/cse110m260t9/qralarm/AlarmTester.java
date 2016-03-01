package cse110m260t9.qralarm;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

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

}
