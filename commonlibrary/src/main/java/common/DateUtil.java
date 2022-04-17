package common;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;



/**
 * Update 1
 * Exposes methods which are very common & generic like generating random
 * number, getting current date & time.
 * 
 * @author Administrator
 * 
 */
public class DateUtil {

	/**
	 * 
	 * Usage: | getCurrentDateTimeStamp| formats|
	 * formats be like dd-MM-yyyy HH-mm-ss,yMMddHHmmss,dd-MM-yyyy-HH-mm-ss more
	 * @return get current date time stamp in format formats
	 */
	public static String getCurrentDateTimeStamp(String formats) {
		
		Date date = new Date();
		String showFormat;
		
		switch (formats) {
		case "dd-MM-yyyy HH-mm-ss":
			showFormat = "dd-MM-yyyy HH-mm-ss";
			break;
		case "yMMdd":
			showFormat = "yMMdd";
			break;
		case "yMMddHHmmss":
			showFormat = "yMMddHHmmss";
			break;
		case "yyyyMMdHms":
			showFormat = "yyyyMMdHms";
			break;	
		case "dd-MM-yyyy-HH-mm-ss":
			showFormat = "dd-MM-yyyy-HH-mm-ss";
			break;
		case "yyyy/MM/dd HH:mm:ss":
			showFormat = "yyyy/MM/dd HH:mm:ss";
			break;
		case "yyyy/MM/dd":
			showFormat = "yyyy/MM/dd";
			break;
	    case "dd-MM-yyyy":
			showFormat = "dd-MM-yyyy";
			break;		
	    case "dd MMM yyyy":		
			showFormat = "dd MMM yyyy";
			break;		
		case "iso":
			showFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
			break;
		case "isoonly":
			showFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
			break;
		case "hh:mm:a":
			showFormat = "hh:mm:a";
			break;
		default:
			showFormat = "Y-m-d";	
		}
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(showFormat);
		return dateFormat.format(date);
	
	}
	
	/**
	 *   Date Calculation
	 *   Usage: | dateCalculation| formats| number|
	 *   @return current date with a format
	 */

   public String dateCalculation(String formats, int number) {
		
	   Calendar cal  = Calendar.getInstance();
	   cal.add(Calendar.DATE, number);

	   SimpleDateFormat s = new SimpleDateFormat(formats);
	   String result = s.format(new Date(cal.getTimeInMillis()));
	
	   return result; 
	
   }
   
	/**
	 * UTC conversion Return UTC time like yyyy-MM-dd'T'HH:mm:ss.SSSXXX
	 * Usage: | utcConversion| dates| format|
	 * @throws ParseException
	 */

	public String utcConversion(String dates, String format) throws ParseException {

		// Input
		Date date = new Date(dates);

		// Conversion
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String result = sdf.format(date);
		return result;

	}
   
	/**
	 * Future Date and TIme Calculation
	 * Usage: | dateCalculation| option| formats| number|
	 * option be like dayOfWeek,dayOfMonth,month,hourofday more
	 * @return Date and time with a format
	 */

	public String dateCalculation(String option, String formats, int number) {

		Calendar cal = Calendar.getInstance();

		switch (option) {
		case "dayOfWeek":
			DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
			String weekdays[] = dfs.getWeekdays();
			
			cal.add(Calendar.DAY_OF_WEEK, number);
			int day = cal.get(Calendar.DAY_OF_WEEK);
			
			return weekdays[day];
		case "dayOfMonth":
			cal.add(Calendar.DAY_OF_MONTH, number);
			break;
		case "date":
			cal.add(Calendar.DATE, number);
			break;
		case "month":
			cal.add(Calendar.MONTH, number);
			break;
		case "year":
			cal.add(Calendar.YEAR, number);
			break;
		case "hour":
			cal.add(Calendar.HOUR, number);
			break;
		case "hourofday":
			cal.add(Calendar.HOUR_OF_DAY, number);
			break;
		case "minute":
			cal.add(Calendar.MINUTE, number);
			break;
		case "second":
			cal.add(Calendar.SECOND, number);
			break;
		case "millisecond":
			cal.add(Calendar.MILLISECOND, number);
			break;
		default:
			cal.add(Calendar.DATE, number);

		}

		SimpleDateFormat s = new SimpleDateFormat(formats);
		String result = s.format(new Date(cal.getTimeInMillis()));

		return result;

	}

	/**
	 * UTC conversion Return UTC time like yyyy-MM-dd'T'HH:mm:ss.SSSXXX
	 * Usage: | utcConversion| dates|
	 * @throws ParseException
	 */

	public String utcConversion(String dates) throws ParseException {

		// Input
		Date date = new Date(dates);

		// Conversion
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String result = sdf.format(date);
		return result;

	}
	
	/**
	 * Parse with given Format for given date
	 * Usage: | parseDate | dates| format|
	 * @param dates
	 * @param format
	 * @return
	 */
	
	public String parseDate(String dates,String format) {
		
        // Input
		Date date = new Date(dates);
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat(format);
		String result = sdf.format(date);
		return result;
		
	}
	
	/**
	 * To get the time difference to the nearest quarter hour
	 * Usage: | getTimeDIfferenceToTheNextQuaterHour |
	 * @return
	 */
	
	public int getTimeDIfferenceToTheNextQuaterHour() {
		Date currentDateTime = new Date();
		Calendar future = Calendar.getInstance();
		future.setTime(currentDateTime);
		Calendar current = Calendar.getInstance();
		current.setTime(currentDateTime);

		int unroundedMinutes = future.get(Calendar.MINUTE);
		if(unroundedMinutes > 15 && unroundedMinutes < 30) {
			return (30 - unroundedMinutes);
		} else if (unroundedMinutes > 30 && unroundedMinutes < 45) {
			return (45 - unroundedMinutes);
		} else if (unroundedMinutes > 45 && unroundedMinutes < 60) {
			return (60 - unroundedMinutes);
		} else {
			return (15 - unroundedMinutes);
		}
	}
	
	/**
	 * To get the N previous days in the current month
	 * Usage: | currentDate | previousDate| defaultReturnDate|
	 * @return nPrevious sate
	 */
	public static Integer getNPreviousDateInCurrentMonth (int currentDate, int previousDate, int defaultReturnDate) {
		return (currentDate + previousDate) < 1 ?  defaultReturnDate : (currentDate + previousDate);
	}
	
	/**
	 * To get the ordinal date suffix
	 * Usage: | getOrdinalDateSuffix | date|
	 * @param date
	 * @return
	 */
	public static String getOrdinalDateSuffix(int date) {
		String ods = (date == 1 || date == 21 || date == 31) ? "st"
				: (date == 2 || date == 22) ? "nd" : (date == 3 || date == 23) ? "rd" : "th";
		return ods;

	}
	
	/**
	 * To get the date and time in non utc format
	 * Usage: | appendDateAndTime | date| time|
	 * @param date
	 * @param time
	 * @return
	 */
	public String appendDateAndTime (String date, String time) {
		return date.concat(time);
	}
	
	/**
	 * To get the date and time in non utc format
	 * Usage: | waitForRoundoffMinute | acceptableRemainingTime|
	 * @param acceptableRemainingTime
	 * @throws InterruptedException
	 */
	public static void waitForRoundoffMinute (int acceptableRemainingTime) throws InterruptedException {
		String now = new DateUtil().dateCalculation("ss", 0);
		int remainingTime = 60 - Integer.parseInt(now);
		int waitFor = remainingTime < acceptableRemainingTime ? remainingTime : 0;
		Thread.sleep(waitFor * 1000);
	}
	
	public static void main(String[] args) throws InterruptedException {
		waitForRoundoffMinute(30);
	}
}
   
