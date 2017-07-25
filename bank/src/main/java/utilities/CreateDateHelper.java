package utilities;

import java.util.Calendar;
import java.util.Date;

public class CreateDateHelper {
	
	public Date createDate(int month, int date, int year, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		
		return cal.getTime();
	}

}
