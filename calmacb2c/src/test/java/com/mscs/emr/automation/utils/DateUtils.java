package com.mscs.emr.automation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	public static final String FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
	public static final String TIME_HH_MM_12_FORMAT = "hh:mm a";
	public static final String TIME_H_12_FORMAT = "h";
	public static final String TIME_HH_12_FORMAT = "hh";
	public static final String TIME_HH_24_FORMAT = "HH";
	public static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(FORMAT_MM_DD_YYYY);
	public static SimpleDateFormat DATE_FORMAT_M_DD = new SimpleDateFormat("M/dd");
	public static SimpleDateFormat DATE_FORMAT_YEAR_TO_YY = new SimpleDateFormat("MM/dd/yy");
	public static SimpleDateFormat DATE_FORMAT_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat DATE_FORMAT_HH_MM_SS_12_FORMAT = new SimpleDateFormat("hh:mm:ss a");
	public static SimpleDateFormat DATE_FORMAT_HH_MM_12_FORMAT = new SimpleDateFormat(TIME_HH_MM_12_FORMAT);
	public static SimpleDateFormat DATE_FORMAT_H_12_FORMAT = new SimpleDateFormat(TIME_H_12_FORMAT);
	public static SimpleDateFormat DATE_FORMAT_HH_12_FORMAT = new SimpleDateFormat(TIME_HH_12_FORMAT);
	public static SimpleDateFormat DATE_FORMAT_HH_24_FORMAT = new SimpleDateFormat(TIME_HH_24_FORMAT);
	public static SimpleDateFormat DATE_FORMAT_MMddyyyy = new SimpleDateFormat("MMddyyyy");
	public static SimpleDateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat DATE_FORMAT_MM_DD_YYYY_HH_MM_A = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	public static SimpleDateFormat DATE_FORMAT_MMMM_DD_YYYY = new SimpleDateFormat("MMMM dd, yyyy");

	public static SimpleDateFormat DATE_FORMAT_DAY_OF_WEEK = new SimpleDateFormat("EEEE");

	public static void main(String[] args) {
		System.out.println(getMonthNumber("September"));
		
	}

	public enum DateParts {
		DATE(Calendar.DATE), MONTH(Calendar.MONTH), YEAR(Calendar.YEAR);

		private int internalCalendarRefId;

		public int toCalendarRefId() {
			return this.internalCalendarRefId;
		}

		private DateParts(int calendarConstRef) {
			this.internalCalendarRefId = calendarConstRef;
		}
	}

	/**
	 * @param monthName ex December
	 * @return monthNumber e.g. 12
	 */
	public static int getMonthNumber(String monthName) {
		return Month.valueOf(monthName.toUpperCase()).getValue();
	}

	/**
	 * @param
	 * @return monthNumber e.g. 12
	 */
	public static int getMonthNumberFromTodaysDate() {
		Date today = new Date();
		try {
			today = DEFAULT_DATE_FORMAT.parse(getTodaysDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int month = cal.get(Calendar.MONTH);
		return month;
	}

	/**
	 * @param
	 * @return monthNumber e.g. 12
	 */
	public static int getMonthNumberFromDate(String InputDate) {
		Date date = new Date();
		try {
			date = DEFAULT_DATE_FORMAT.parse(InputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * @param
	 * @return DayNumber e.g. 12
	 */
	public static int getDayNumberFromDate(String InputDate) {
		Date date = new Date();
		try {
			date = DEFAULT_DATE_FORMAT.parse(InputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		return day;
	}

	/**
	 * @param
	 * @return Year e.g. 2018
	 */
	public static int getYearFromDate(String InputDate) {
		Date date = new Date();
		try {
			date = DEFAULT_DATE_FORMAT.parse(InputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * @param
	 * @return Year e.g. 2018
	 */
	public static int getYearFromTodaysDate() {
		Date today = new Date();
		try {
			today = DEFAULT_DATE_FORMAT.parse(getTodaysDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * @param monthNumber ex 12
	 * @return monthName ex december
	 */
	public static String getMonthName(int monthNumber) {
		return Month.of(monthNumber).name();
	}

	/**
	 * @return date
	 */
	public static String getTomorrowsDate() {
		return constructDate(DEFAULT_DATE_FORMAT, 1, DateParts.DATE);
	}

	/**
	 * @return date
	 */
	public static String getTodaysDate() {
		return constructDate(DEFAULT_DATE_FORMAT, 0, DateParts.DATE);
	}

	/**
	 * @return current time
	 */
	public static String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		String time = DATE_FORMAT_HH_MM_SS.format(cal.getTime());
		return time;
	}

	/**
	 * @return priorDate
	 */
	public static String getYesterdaysDate() {
		return constructDate(DEFAULT_DATE_FORMAT, -1, DateParts.DATE);
	}

	/**
	 * @param futureDateDifference ex mm/DD/yyyy
	 * @return futureDate
	 */
	public static String getFutureDate(int futureDateDifference) {
		return constructDate(DEFAULT_DATE_FORMAT, Math.abs(futureDateDifference), DateParts.DATE);
	}

	/**
	 * @param priorDateDifference ex mm/DD/yyyy
	 * @return priorDate
	 */
	public static String getPriorDate(int priorDateDifference) {
		return constructDate(DEFAULT_DATE_FORMAT, -Math.abs(priorDateDifference), DateParts.DATE);
	}

	/**
	 * @param futureMonthDifference ex mm/DD/yyyy
	 * @return date with added future months
	 */
	public static String getFutureDateByAddingMonths(int futureMonthDifference) {
		return constructDate(DEFAULT_DATE_FORMAT, Math.abs(futureMonthDifference), DateParts.MONTH);
	}

	/**
	 * @param futureYearDifference ex mm/DD/yyyy
	 * @return date with added future years
	 */
	public static String getFutureDateByAddingyears(int futureYearDifference) {
		return constructDate(DEFAULT_DATE_FORMAT, Math.abs(futureYearDifference), DateParts.YEAR);
	}

	/**
	 * @param previousMonthsDifference ex 1,2,3
	 * @return date (mm/dd/yyyy) of any previous month from todays's Date
	 */
	public static String getPreviousMonthsDate(int previousMonthsDifference) {
		return constructDate(DEFAULT_DATE_FORMAT, -Math.abs(previousMonthsDifference), DateParts.MONTH);
	}

	public static long differenceBetweenDates(String fromDate, String toDate, TemporalUnit unit) {
		LocalDate localFromDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern(FORMAT_MM_DD_YYYY));
		LocalDate localToDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern(FORMAT_MM_DD_YYYY));
		Period period = Period.between(localFromDate, localToDate);
		return period.get(unit);
	}

	public static long differenceBetweenTime(String fromTime, String toTime, TemporalUnit unit) {
		if (StringUtils.isEmpty(toTime))
			return 0;
		LocalTime localFromTime = LocalTime.parse(fromTime, DateTimeFormatter.ofPattern(TIME_HH_MM_12_FORMAT));
		LocalTime localToTime = LocalTime.parse(toTime, DateTimeFormatter.ofPattern(TIME_HH_MM_12_FORMAT));
		return localFromTime.until(localToTime, unit);
	}

	/**
	 * @param previousYearsDifference ex 1,2,3
	 * @return date (mm/dd/yyyy) of any previous year from todays's Date
	 */
	public static String getPreviousYearDate(Integer previousYearsDifference) {
		return constructDate(DEFAULT_DATE_FORMAT, -(previousYearsDifference), DateParts.YEAR);
	}

	public static String constructDate(SimpleDateFormat simpleDateFormat, int delta, DateParts cal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(cal.toCalendarRefId(), delta);
		return simpleDateFormat.format(calendar.getTime());
	}

	public static String constructDateWithDayMonthYear(SimpleDateFormat simpleDateFormat, int date, int month,
			int year) {
		// created this function to handle previous day,month and year in one API
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, date);
		calender.add(Calendar.MONTH, month);
		calender.add(Calendar.YEAR, year);
		return simpleDateFormat.format(calender.getTime());
	}

	public static String getPreviousDayMonthYear(int date, int month, int year) {
		// created this function to handle previous day,month and year in one API
		// example to get lastYear , lastMonth and sameDay
		// getPreviousDayMonthYear(0,1,1)
		return constructDateWithDayMonthYear(DEFAULT_DATE_FORMAT, -Math.abs(date), -Math.abs(month), -Math.abs(year));
	}

	/**
	 * @param vDate e.g. Date in the format of 2/2/1977, 02/02/1977, 02-02-1977,
	 *              02.02.1977 etc.
	 * @return Date Value in the Format of: July 07, 1977
	 */
	public static String formatDateToMonthNameDayYear(String vDate) {
		Date date = convertDashSlashDateToDate(vDate);
		return date == null ? "" : new SimpleDateFormat("MMMM DD, YYYY").format(date);
	}

	public static String getDay(String date) throws ParseException {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
		return simpleDateformat.format(DEFAULT_DATE_FORMAT.parse(date));
	}

	public static String parseDateToYYFormat(String sDate) {
		String parsedDate = null;
		try {
			Date date = DEFAULT_DATE_FORMAT.parse(sDate);
			parsedDate = DATE_FORMAT_YEAR_TO_YY.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}

	public static String parseDateToMMDDYYFormat(String sDate) {
		String parsedDate = null;
		try {
			Date date = DEFAULT_DATE_FORMAT.parse(sDate);
			parsedDate = DATE_FORMAT_MMddyyyy.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}

	public static String parseDateToYYYYMMDDFormat(String sDate) {
		String parsedDate = null;
		try {
			Date date = DEFAULT_DATE_FORMAT.parse(sDate);
			parsedDate = DATE_FORMAT_YYYY_MM_DD.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}

	public static String parseToMDDFormat(String sDate) {
		Date date = parseToDate(sDate);
		return DATE_FORMAT_M_DD.format(date);
	}

	public static Date parseToDate(String sDate) {
		Date date = null;
		try {
			date = DEFAULT_DATE_FORMAT.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getCurrentTimeTwelveHrFormat() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT_HH_MM_SS_12_FORMAT.format(cal.getTime());
	}

	public static String getCurrentHoursFor24HrFormat() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT_HH_24_FORMAT.format(cal.getTime());
	}

	public static String getCurrentTimeTwelveHrFormatWithoutSec() {
		return LocalTime.now(ZoneId.of("America/Los_Angeles"))
				.format(DateTimeFormatter.ofPattern(TIME_HH_MM_12_FORMAT));
	}

	public static Date getCurrentDateTime() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static Date parseStringToTwelveHrTimeFormat(String timeHHMM12) {
		try {
			return DATE_FORMAT_HH_MM_12_FORMAT.parse(timeHHMM12);
		} catch (ParseException e) {
			throw new RuntimeException(
					"Incorrect time format: " + timeHHMM12 + ". It should be in '" + TIME_HH_MM_12_FORMAT + "' format");
		}
	}

	public static String getHour(Date date) {
		int hour = dateToCalendar(date).get(Calendar.HOUR);

		return hour == 0 ? "12" : String.format("%02d", hour);
	}

	public static String getMinute(Date date) {
		return String.format("%02d", dateToCalendar(date).get(Calendar.MINUTE));
	}

	public static String getAmPm(Date date) {
		Calendar calendar = dateToCalendar(date);
		if (calendar.get(Calendar.AM_PM) == 0) {
			return "G2TestValues.MAR_START_STOP_TIME_AM";
		} else if (calendar.get(Calendar.AM_PM) == 1) {
			return "G2TestValues.MAR_START_STOP_TIME_PM";
		}
		return "";
	}

	public static Calendar dateToCalendar(Date date) {
		return new Calendar.Builder().setInstant(date).build();
	}

	/**
	 * @param vDate e.g. Date in the format of 2/2/1977, 02/02/1977, 02-02-1977,
	 *              02.02.1977 etc.
	 * @return Date Value in the Format of: 11-JUN-77
	 */
	public static String formatDateToDayDashMonthNameDashYear(String vDate) {
		Date date = convertDashSlashDateToDate(vDate);
		return date == null ? "" : new SimpleDateFormat("DD-MMM-YY").format(date).toUpperCase();
	}

	/**
	 * @param vDate e.g. Date in the format of M/D/YYYY, MM/DD/YYYY, MM-DD-YYYY,
	 *              DD.MM.YYYY etc.
	 * @return Date Value
	 */
	private static Date convertDashSlashDateToDate(String vDate) {
		final String[] knownDateFormats = { "M/D/YYYY", "MM/DD/YYYY", "MM-DD-YYYY", "YYYY-MM-DD", "DD.MM.YYYY" };

		if (vDate == null || vDate.trim().length() < 6) {
			return null;
		}

		Date result = null;

		for (int i = 0; result == null && i < knownDateFormats.length; ++i) {
			try {
				result = new SimpleDateFormat(knownDateFormats[i]).parse(vDate);
			} catch (ParseException pex) {
			}
		}

		return result;
	}

	/**
	 * @param vDate e.g. Date in the format of 2/2/1977, 02/02/1977, 02-02-1977,
	 *              02.02.1977 etc.
	 * @return Date Value in the Format of: 11-JUN-2019 Purpose: Required for e-Sign
	 *         Date practice Configuration
	 */
	public static String formatDateToDayDashMonthNameDashFullYearValue(String vDate) {
		Date date = convertDashSlashDateToDate(vDate);
		return date == null ? "" : new SimpleDateFormat("DD-MMM-YYYY").format(date).toUpperCase();
	}

	/**
	 * Get a date by adding or subtracting days from a given date
	 * 
	 * @param deltaDays If positive, return date is later than the given date. If
	 *                  negative, return date is earlier than the given date.
	 * @param sDate     A date string in "MM/dd/yyyy" format
	 * @return a new date
	 */
	public static String getNewDateFromDate(int deltaDays, String sDate) {
		Date aDate = parseToDate(sDate);
		Calendar calender = Calendar.getInstance();
		calender.setTime(aDate);
		calender.add(Calendar.DATE, deltaDays);
		return DEFAULT_DATE_FORMAT.format(calender.getTime());
	}

	public static String parseDateToFormatMmDdYyyy(String mmDDyy) {
		String parsedDate = null;
		try {
			Date date = DATE_FORMAT_YEAR_TO_YY.parse(mmDDyy);
			parsedDate = DEFAULT_DATE_FORMAT.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}

	public static String parseDateToMMMMDDYYYYFormat(String date) {
		return DATE_FORMAT_MMMM_DD_YYYY.format(parseToDate(date));
	}

	/**
	 * @param vDate e.g. Date in the format of 2/2/1977, 02/02/1977, 02-02-1977,
	 *              02.02.1977 etc.
	 * @return Date Value in the Format of: Thursday, July 07, 1977 (100 days away)
	 * @throws ParseException
	 */
	/*
	 * public static String formatDateWithDaysAwayFromToday(String vDate) throws
	 * ParseException { String formattedString =
	 * SharedUtils.NO_RECORDED_VALUE_SINGLE_DASH; Date dos =
	 * DEFAULT_DATE_FORMAT.parse(vDate); if (dos == null) { return formattedString;
	 * } int daysAway = CalendarUtil.getDaysBetween(new Date(), dos); return
	 * SharedUtils.format("EEEE, MMMM d, yyyy", dos) + " (" + daysAway + " day" +
	 * (daysAway != 1 ? "s" : "") + " away)"; }
	 */

	public static long getTime() {
		DateUtils.getCurrentTime();
		return System.currentTimeMillis();
	}

	public static Long getMillisFromDateFormatMMDDYYYY(String date) throws ParseException {
		return DEFAULT_DATE_FORMAT.parse(date).getTime();
	}

	/*
	 * We transform the format "hh:mm a" into milliseconds and add the time in
	 * milliseconds at the beginning of current day.
	 */
	public static Long getMillisFromDateFormatHHMMA(String date) throws ParseException {
		final long milliSecInDay = 86400000L;
		long currentTimeMillis = getCurrentDateTime().getTime();
		long millisAtTodayMidnight = (currentTimeMillis / milliSecInDay) * milliSecInDay;
		return millisAtTodayMidnight + DATE_FORMAT_HH_MM_12_FORMAT.parse(date).getTime();
	}

	public static String getDayOfWeek(String date) {
		Date aDate = parseToDate(date);
		return DATE_FORMAT_DAY_OF_WEEK.format(aDate);
	}

	public static String getDateByDayOfWeek(String weekDay, String weekDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseToDate(weekDate));
		switch (weekDay) {
		case "G2TestValues.DAYS_SUNDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		case "G2TestValues.DAYS_MONDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 2);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		case "G2TestValues.DAYS_TUESDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 3);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		case "G2TestValues.DAYS_WEDNESDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 4);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		case "G2TestValues.DAYS_THURSDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 5);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		case "G2TestValues.DAYS_FRIDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 6);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		case "G2TestValues.DAYS_SATURDAY":
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			return DEFAULT_DATE_FORMAT.format(calendar.getTime());
		}
		return "";
	}

	public static String getFutureDate(int monthDifference, int dayOfMonth) {
		return DateUtils.getFutureDateByAddingMonths(monthDifference).replaceFirst("/\\d{2}",
				"/".concat(String.valueOf(dayOfMonth)));
	}

	public static String getPriorDate(int monthDifference, int dayOfMonth) {
		return DateUtils.getPreviousMonthsDate(monthDifference).replaceFirst("/\\d{2}",
				"/".concat(String.valueOf(dayOfMonth)));
	}

}
