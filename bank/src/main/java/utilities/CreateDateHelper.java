package utilities;

import java.util.Calendar;
import java.util.Date;

import pojo.Transaction;

public class CreateDateHelper {
	private static final String WITHDRAW = "withdraw";
	
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
	
	public double checkWithdrawalTransactionsDates(Transaction t, Date fromDate, Date toDate, double accumulatedWithdrawal) {
		if(t.getDate().after(fromDate) && t.getDate().before(toDate)) {
			if(t.getDescription().equals(WITHDRAW)) {
				accumulatedWithdrawal +=t.getAmount();
			}
		} else if(t.getDate().equals(fromDate) || t.getDate().equals(toDate)) {
			if(t.getDescription().equals(WITHDRAW)) {
				accumulatedWithdrawal +=t.getAmount();
			}
		}
		
		return accumulatedWithdrawal;
	}
}
