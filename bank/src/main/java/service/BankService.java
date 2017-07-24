package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import exception.AccountDoesNotExistException;
import exception.ExceedDailyWithdrawalAmountException;
import exception.InsufficientAmountToDepositException;
import exception.InsufficientFundsToTransferException;
import exception.InsufficientFundsWithdrawalException;
import exception.InvalidStartAccountAmountException;
import pojo.Account;
import pojo.Transaction;
import pojo.User;
import repository.IAccountRepo;
import repository.ITransactionRepo;
import repository.IUserRepo;


public class BankService implements IBankService {
	private IAccountRepo accountRepo;
	private ITransactionRepo transactionRepo;
	private IUserRepo userRepo;
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	
	
	public BankService(IAccountRepo accountRepo, ITransactionRepo transactionRepo, IUserRepo userRepo) {
		this.accountRepo = accountRepo;
		this.transactionRepo = transactionRepo;
		this.userRepo = userRepo;
	}

	public String createUser(String name) {
		if(name == null) {
			throw new NullPointerException();
		}else if("".equals(name)) {
			return "Username provided cannot be an empty String!";
		}
		
		User newUser = new User(name);
		
		return this.getUserRepo().saveUser(newUser);
	}
	
	public String createAccount(Account newAccount) throws InvalidStartAccountAmountException {
		
		if(newAccount == null) {
			throw new NullPointerException();
		} else if(newAccount.getBalance() < 100.00) {
			throw new InvalidStartAccountAmountException();
		}
		
		Transaction transaction = newAccount.getTransactions().get(0);
		String transactionResult = this.getTransactionRepo().saveTransaction(transaction);
		if(SUCCESS.equals(transactionResult)) {
			return this.getAccountRepo().saveAccount(newAccount);
		} else {
			return FAILURE;
		}
		
		
		
	}

	public Account showBalance(int accountNumber) throws AccountDoesNotExistException{
		Account retrievedAccount = this.getAccountRepo().findAccount(accountNumber);
		
		if(retrievedAccount == null) {
			throw new AccountDoesNotExistException();
		}
		
		return retrievedAccount;
	}

	public Account withdraw(int accountNumber, double amount) throws ExceedDailyWithdrawalAmountException, InsufficientFundsWithdrawalException, AccountDoesNotExistException{
		
		if(amount > 1000.00) {
			throw new ExceedDailyWithdrawalAmountException();
		}
		
		Account retrievedAccount = this.getAccountRepo().findAccount(accountNumber);
		if(retrievedAccount == null) {
			throw new AccountDoesNotExistException();
		}
		double currentBalance = retrievedAccount.getBalance();
		
		if(currentBalance < amount) {
			throw new InsufficientFundsWithdrawalException();
		}
		
		double newBalance = currentBalance - amount;
		retrievedAccount.setBalance(newBalance);
		
		Date withdrawDate = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.MONTH, 7);
		cal2.set(Calendar.DATE, 26);
		cal2.set(Calendar.YEAR, 2017);
		withdrawDate = cal2.getTime();
		Transaction withdrawTransaction = new Transaction("Withdraw", "debit", 300.00, 2, withdrawDate,
				retrievedAccount.getBalance());
		retrievedAccount.getTransactions().add(withdrawTransaction);
		this.getTransactionRepo().saveTransaction(withdrawTransaction);
		
		
		return retrievedAccount;
	}

	public Account deposit(int accountNumber, double amount) throws InsufficientAmountToDepositException, AccountDoesNotExistException{
		if(amount <= 0.00) {
			throw new InsufficientAmountToDepositException();
		} 
		
		Account retrievedAccount = this.getAccountRepo().findAccount(accountNumber);
		if(retrievedAccount == null) {
			throw new AccountDoesNotExistException();
		}
		double currentBalance = retrievedAccount.getBalance();
		double newBalance = currentBalance + amount;
		retrievedAccount.setBalance(newBalance);
				
		Date depositDate = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.MONTH, 7);
		cal2.set(Calendar.DATE, 26);
		cal2.set(Calendar.YEAR, 2017);
		depositDate = cal2.getTime();
		Transaction depositTransaction = new Transaction("Deposit", "credit", 300.00, 2, depositDate,
				retrievedAccount.getBalance());
		retrievedAccount.getTransactions().add(depositTransaction);
		this.getTransactionRepo().saveTransaction(depositTransaction);
		
		
		return retrievedAccount;
	}

	public Account fundTransfer(double amount, Date date, int toAccountNo, int fromAccount) throws AccountDoesNotExistException, InsufficientFundsToTransferException {
		Account transferringAccount = this.getAccountRepo().findAccount(fromAccount);
		Account receivingAccount = this.getAccountRepo().findAccount(toAccountNo);
		
		if(transferringAccount == null || receivingAccount == null) {
			throw new AccountDoesNotExistException();
		} else if(transferringAccount.getBalance() < amount) {
			throw new InsufficientFundsToTransferException();
		}
		
		double transferringAccountBalance = transferringAccount.getBalance();
		double newTransferringAccountBal = transferringAccountBalance-amount;
		transferringAccount.setBalance(newTransferringAccountBal);
		double receivingAccountBalance = receivingAccount.getBalance();
		double newReceivingAccountBal = receivingAccountBalance + amount;
		receivingAccount.setBalance(newReceivingAccountBal);
		
		Transaction transferringTransaction = new Transaction("Funds transfer", "debit", 150.00, 3, date, newTransferringAccountBal);
		Transaction receivingTransaction = new Transaction("Funds transfer", "credit", 150.00, 4, date, newReceivingAccountBal);
		this.getTransactionRepo().saveTransaction(transferringTransaction);
		this.getTransactionRepo().saveTransaction(receivingTransaction);
		transferringAccount.getTransactions().add(transferringTransaction);
		receivingAccount.getTransactions().add(receivingTransaction);
		
		
		return new Account(transferringAccount.getBalance(), null, transferringAccount.getAccountNo(), transferringAccount.getUser());
	}

	public Account printTransaction(int accountNumber, Date fromDate, Date toDate) throws AccountDoesNotExistException {
		Account retrievedAccount = this.getAccountRepo().findAccount(accountNumber);
		
		if(retrievedAccount == null) {
			throw new AccountDoesNotExistException();
		}
		ArrayList<Transaction> retrievedTransactions = retrievedAccount.getTransactions();
		ArrayList<Transaction> printingTransactions = new ArrayList<Transaction>();
		
		for(Transaction t:retrievedTransactions) {
			Date transactionDate = t.getDate();
			if(transactionDate.after(fromDate) && transactionDate.before(toDate)) {
				printingTransactions.add(t);
			} else if(transactionDate.equals(fromDate)) {
				printingTransactions.add(t);
			} else if(transactionDate.equals(toDate)) {
				printingTransactions.add(t);
			}
		}
		
		
		return new Account(retrievedAccount.getBalance(), printingTransactions, retrievedAccount.getAccountNo(), retrievedAccount.getUser());
	}

	public Account printTransaction(int accountNumber) throws AccountDoesNotExistException {
		
		Account retrievedAccount = this.getAccountRepo().findAccount(accountNumber);
		if(retrievedAccount == null) {
			throw new AccountDoesNotExistException();
		}
		
		ArrayList<Transaction> retrievedTransactions = retrievedAccount.getTransactions();
		int transactionSize = retrievedTransactions.size();
		--transactionSize;
		ArrayList<Transaction> printingTransactions = new ArrayList<Transaction>();
		
		for(int i=1; i<=10; i++) {
			printingTransactions.add(retrievedTransactions.get(transactionSize));
			--transactionSize;
		}
		
		
		return new Account(retrievedAccount.getBalance(), printingTransactions, retrievedAccount.getAccountNo(), retrievedAccount.getUser());
	}

	public IAccountRepo getAccountRepo() {
		return accountRepo;
	}

	public void setAccountRepo(IAccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	public ITransactionRepo getTransactionRepo() {
		return transactionRepo;
	}

	public void setTransactionRepo(ITransactionRepo transactionRepo) {
		this.transactionRepo = transactionRepo;
	}

	public IUserRepo getUserRepo() {
		return userRepo;
 	}

	public void setUserRepo(IUserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	

}
