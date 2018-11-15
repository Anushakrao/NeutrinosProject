/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.gss;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.kie.api.time.SessionPseudoClock;
import org.jbpm.process.core.timer.BusinessCalendarImpl;
import org.junit.Test;
import org.junit.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BusinessCalendarTest {


    Logger logger = LoggerFactory.getLogger(this.getClass());
    Properties config;
    
    @Before
    public void setupBusinessCalender(){
        config = new Properties();
        config.setProperty(BusinessCalendarImpl.DAYS_PER_WEEK, "5");
        config.setProperty(BusinessCalendarImpl.HOURS_PER_DAY, "8");
        config.setProperty(BusinessCalendarImpl.START_HOUR, "9");
        config.setProperty(BusinessCalendarImpl.END_HOUR, "18");
        config.setProperty(BusinessCalendarImpl.WEEKEND_DAYS, "1,7"); // sun,sat
//        config.setProperty(BusinessCalendarImpl.HOLIDAYS, "2018-04-30,2018-05-03,2018-05-04,2018-05-05");
        config.setProperty(BusinessCalendarImpl.HOLIDAYS, "2018-04-30,2018-05-03:2018-05-05");
        config.setProperty(BusinessCalendarImpl.HOLIDAY_DATE_FORMAT, "yyyy-MM-dd");
    }


    @Test
    public void testCalculateMinutesPassingAfterWorkingHours() {
	String currentDate = "2018-05-02 19:51:33";
	String duration = "10m";
        String expectedDate = "2018-05-07 09:10:00";
        
        SessionPseudoClock clock = new StaticPseudoClock(parseToDateWithTime(currentDate).getTime());
        BusinessCalendarImpl businessCal = new BusinessCalendarImpl(config, clock);

        Date result = businessCal.calculateBusinessTimeAsDate(duration);
        
        System.out.println(result);
        assertEquals(expectedDate, formatDate("yyyy-MM-dd HH:mm", result));
    }

    @Test
    public void testCalculateMinutesPassingBeforeWorkingHours() {
	String currentDate = "2018-05-02 06:51:33";
	String duration = "10m";
        String expectedDate = "2018-05-02 09:10:00";
        
        SessionPseudoClock clock = new StaticPseudoClock(parseToDateWithTime(currentDate).getTime());
        BusinessCalendarImpl businessCal = new BusinessCalendarImpl(config, clock);

        Date result = businessCal.calculateBusinessTimeAsDate(duration);
        
        System.out.println(result);
        assertEquals(expectedDate, formatDate("yyyy-MM-dd HH:mm", result));
    }

    @Test
    public void testCalculateMinutesPassingWeekend() {
        String currentDate = "2018-05-06 13:51:33";  //Weekend(Sunday)
        String duration = "10m";
        String expectedDate = "2018-05-07 09:10:00";

        SessionPseudoClock clock = new StaticPseudoClock(parseToDateWithTime(currentDate).getTime());
        BusinessCalendarImpl businessCal = new BusinessCalendarImpl(config, clock);

        Date result = businessCal.calculateBusinessTimeAsDate(duration);

        System.out.println(result);
        assertEquals(expectedDate, formatDate("yyyy-MM-dd HH:mm", result));
    }

   @Test
    public void testCalculateMinutesPassingHoliday() {
        String currentDate = "2018-05-03 13:51:33";   // Holiday
        String duration = "10m";
        String expectedDate = "2018-05-07 09:10:00";

        SessionPseudoClock clock = new StaticPseudoClock(parseToDateWithTime(currentDate).getTime());
        BusinessCalendarImpl businessCal = new BusinessCalendarImpl(config, clock);

        Date result = businessCal.calculateBusinessTimeAsDate(duration);

        System.out.println(result);
        assertEquals(expectedDate, formatDate("yyyy-MM-dd HH:mm", result));
    }
    
    private Date parseToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date testTime;
        try {
            testTime = sdf.parse(dateString);
            
            return testTime;
        } catch (ParseException e) {
            return null;
        }
    }
    
    private Date parseToDateWithTime(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        Date testTime;
        try {
            testTime = sdf.parse(dateString);
            
            return testTime;
        } catch (ParseException e) {
            return null;
        }        
    }
    
    private Date parseToDateWithTimeAndMillis(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        Date testTime;
        try {
            testTime = sdf.parse(dateString);
            
            return testTime;
        } catch (ParseException e) {
            return null;
        }        
    }
    
    
    private String formatDate(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        
        String testTime = sdf.format(date);
        
        return testTime;
                
    }
    
    private class StaticPseudoClock implements SessionPseudoClock {

    	private long currentTime;
    	
    	private StaticPseudoClock(long currenttime) {
    		this.currentTime = currenttime;
    	}
    	
		public long getCurrentTime() {
			return this.currentTime;
		}

		public long advanceTime(long amount, TimeUnit unit) {
			throw new UnsupportedOperationException("It is static clock and does not allow advance time operation");
		}
    	
    }
}
