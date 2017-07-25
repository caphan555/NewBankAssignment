package utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pojo.Account;
import pojo.Transaction;
import pojo.User;
import service.IBankService;

public class CreateStandardTestUserAccount {

	private static final String OPENING_NEW_ACCOUNT = "Opening a new account.";
	private static final String DEBIT = "debit";
	private static final String CREDIT = "credit";
	private static final String TEST_USERNAME = "John";
	private static final String DEPOSIT = "Deposit";
	private static final String WITHDRAW = "Withdraw";
	CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

	public String createStandardTest(IBankService bs) {
		try {
			Account newAccount = this.setUpNormalAccount(bs);

			return bs.createAccount(newAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failure";
	}

	public List<Account> setUpTransferAccounts(IBankService bs) {
		try {
			this.createStandardTest(bs);
			CreateUniqueNumber uniqueNumberSetCreator = new CreateUniqueNumber();

			// 2nd user & account
			bs.createUser("Ramal");

			User secondUser = bs.getUserRepo().getUsers().get(1);

			Account newAccount2 = new Account(0.00, new ArrayList<>(), 2, secondUser);

			// set date
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.MONTH, 9);
			cal2.set(Calendar.DATE, 28);
			cal2.set(Calendar.YEAR, 2017);
			int uniqueTId2 = uniqueNumberSetCreator.generateUniqueTransactionId();
			Transaction newTransaction2 = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 200.00, uniqueTId2,
					cal2.getTime(), newAccount2.getBalance() + 200.00);

			newAccount2.setBalance(newTransaction2.getBalance());
			newAccount2.getTransactions().add(newTransaction2);

			bs.createAccount(newAccount2);
			List<Account> accounts = new ArrayList<>();
			accounts.add(bs.getAccountRepo().getAccounts().get(0));
			accounts.add(bs.getAccountRepo().getAccounts().get(1));

			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	public Account setUpForPrintLastTenTransaction(IBankService bs) {

		Account newAccount = this.setUpNormalAccount(bs);

		int month = 0;
		int day = 13;
		double deposit = 200.00;
		for (int i = 0; i <= 5; i++) {
			Calendar depositCal = Calendar.getInstance();
			depositCal.set(Calendar.MONTH, month);
			depositCal.set(Calendar.DATE, day);
			depositCal.set(Calendar.YEAR, 2017);

			int uniqueDepositId = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction deposits = new Transaction(DEPOSIT, DEBIT, deposit, uniqueDepositId, depositCal.getTime(),
					newAccount.getBalance() + deposit);
			double newBalance = newAccount.getBalance() + deposit;
			newAccount.setBalance(newBalance);
			newAccount.getTransactions().add(deposits);
			++month;
			++day;
			++deposit;
			bs.getTransactionRepo().saveTransaction(deposits);
		}

		int month2 = 7;
		int day2 = 23;
		double withdraw = 300.00;
		for (int i = 0; i <= 4; i++) {
			Calendar withdrawCal = Calendar.getInstance();
			withdrawCal.set(Calendar.MONTH, month2);
			withdrawCal.set(Calendar.DATE, day2);
			withdrawCal.set(Calendar.YEAR, 2017);

			int uniqueWithdrawId = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction withdrawals = new Transaction(WITHDRAW, DEBIT, withdraw, uniqueWithdrawId,
					withdrawCal.getTime(), newAccount.getBalance() - withdraw);
			double newBalance = newAccount.getBalance() - withdraw;
			newAccount.setBalance(newBalance);
			newAccount.getTransactions().add(withdrawals);
			++month;
			++day;
			--withdraw;
			bs.getTransactionRepo().saveTransaction(withdrawals);
		}

		bs.getAccountRepo().getAccounts().add(newAccount);

		return newAccount;

	}

	public Account setUpForPrintPeriodTransaction(IBankService bs) {
		bs.createUser(TEST_USERNAME);

		User user = bs.getUserRepo().getUsers().get(0);

		CreateUniqueNumber uniqueNumberPrintCreator = new CreateUniqueNumber();

		int uniqueAId = uniqueNumberPrintCreator.generateUniqueAccNum();
		Account newAccount = new Account(0.00, new ArrayList<Transaction>(), uniqueAId, user);

		// set date
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DATE, 3);
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.HOUR, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		int uniqueTId = uniqueNumberPrintCreator.generateUniqueTransactionId();
		Transaction newTransaction = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 500.00, uniqueTId, cal.getTime(),
				newAccount.getBalance() + 500.00);

		newAccount.setBalance(newTransaction.getBalance());
		newAccount.getTransactions().add(newTransaction);

		int month = 0;
		int day = 13;
		int hour = 13;
		int minute = 12;
		int second = 14;
		double deposit = 200.00;
		for (int i = 0; i <= 5; i++) {
			Calendar depositCal = Calendar.getInstance();
			depositCal.set(Calendar.MONTH, month);
			depositCal.set(Calendar.DATE, day);
			depositCal.set(Calendar.YEAR, 2017);
			depositCal.set(Calendar.HOUR, hour);
			depositCal.set(Calendar.MINUTE, minute);
			depositCal.set(Calendar.SECOND, second);

			int uniqueDepositId = uniqueNumberPrintCreator.generateUniqueTransactionId();
			Transaction deposits = new Transaction(DEPOSIT, DEBIT, deposit, uniqueDepositId, depositCal.getTime(),
					newAccount.getBalance() + deposit);
			double newBalance = newAccount.getBalance() + deposit;
			newAccount.setBalance(newBalance);
			newAccount.getTransactions().add(deposits);
			++month;
			++day;
			++deposit;
			++hour;
			++minute;
			++second;
			bs.getTransactionRepo().saveTransaction(deposits);
		}

		int month2 = 7;
		int day2 = 23;
		int withHour = 9;
		int withMinute = 10;
		int withSecond = 11;
		double withdraw = 300.00;
		for (int i = 0; i <= 4; i++) {
			Calendar withdrawCal = Calendar.getInstance();
			withdrawCal.set(Calendar.MONTH, month2);
			withdrawCal.set(Calendar.DATE, day2);
			withdrawCal.set(Calendar.YEAR, 2017);
			withdrawCal.set(Calendar.HOUR, withHour);
			withdrawCal.set(Calendar.MINUTE, withMinute);
			withdrawCal.set(Calendar.SECOND, withSecond);

			int uniqueWithdrawId = uniqueNumberPrintCreator.generateUniqueTransactionId();
			Transaction withdrawals = new Transaction(WITHDRAW, DEBIT, withdraw, uniqueWithdrawId,
					withdrawCal.getTime(), newAccount.getBalance() - withdraw);
			double newBalance = newAccount.getBalance() - withdraw;
			newAccount.setBalance(newBalance);
			newAccount.getTransactions().add(withdrawals);
			++month;
			++day;
			++withHour;
			++withMinute;
			++withSecond;
			--withdraw;
			bs.getTransactionRepo().saveTransaction(withdrawals);
		}

		bs.getAccountRepo().getAccounts().add(newAccount);

		return newAccount;
	}

	public Account setUpNormalAccount(IBankService bs) {
		bs.createUser("John");

		User user = bs.getUserRepo().getUsers().get(0);

		uniqueNumberCreator = new CreateUniqueNumber();

		int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
		Account newAccount = new Account(0.00, new ArrayList<Transaction>(), uniqueAId, user);

		// set date
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 4);
		cal.set(Calendar.DATE, 3);
		cal.set(Calendar.YEAR, 2017);
		int uniqueTId = uniqueNumberCreator.generateUniqueTransactionId();
		Transaction newTransaction = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 500.00, uniqueTId, cal.getTime(),
				newAccount.getBalance() + 500.00);
		newAccount.setBalance(newTransaction.getBalance());
		newAccount.getTransactions().add(newTransaction);

		return newAccount;
	}

	public Account createVariantAccount(IBankService bs, double amount, double balance) {
		try {
			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberVariantCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberVariantCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

			// set date
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DATE, 3);
			cal.set(Calendar.YEAR, 2017);
			int uniqueTId = uniqueNumberVariantCreator.generateUniqueTransactionId();
			Transaction newTransaction = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, balance, uniqueTId, cal.getTime(),
					newAccount.getBalance() + amount);
			newAccount.setBalance(newTransaction.getBalance());
			newAccount.getTransactions().add(newTransaction);


			return newAccount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Account();
	}
}
