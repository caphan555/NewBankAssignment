package assignmenttwo.bank;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import exception.AccountDoesNotExistException;
import exception.ExceedDailyWithdrawalAmountException;
import exception.InsufficientAmountToDepositException;
import exception.InsufficientFundsToTransferException;
import exception.InsufficientFundsWithdrawalException;
import exception.InvalidStartAccountAmountException;
import pojo.Account;
import pojo.Transaction;
import pojo.User;
import repository.AccountRepo;
import repository.IAccountRepo;
import repository.ITransactionRepo;
import repository.IUserRepo;
import repository.TransactionRepo;
import repository.UserRepo;
import service.BankService;
import service.IBankService;
import utilities.CreateUniqueNumber;

public class BankTest {
	private static final String SUCCESS = "success";
	private static final  String DEPOSIT = "Deposit";
	private static final String WITHDRAW= "Withdraw";
	private static final String OPENING_NEW_ACCOUNT = "Opening a new account.";
	private static final String DEBIT = "debit";
	private static final String CREDIT = "credit";
	private static final String TEST_USERNAME = "John";
	
	@Test
	public void testCreateUserSuccess() {
		List<User> users = new ArrayList<>();
		IUserRepo urp = new UserRepo(users);

		List<Transaction> transactions = new ArrayList<>();
		ITransactionRepo trp = new TransactionRepo(transactions);

		List<Account> accounts = new ArrayList<>();
		IAccountRepo arp = new AccountRepo(accounts);

		IBankService bs = new BankService(arp, trp, urp);

		String result = bs.createUser(TEST_USERNAME);

		assertEquals(SUCCESS, result);
	}

	@Test
	public void testCannotCreateEmptyUser() {
		List<User> users = new ArrayList<>();
		IUserRepo urp = new UserRepo(users);

		List<Transaction> transactions = new ArrayList<>();
		ITransactionRepo trp = new TransactionRepo(transactions);

		List<Account> accounts = new ArrayList<>();
		IAccountRepo arp = new AccountRepo(accounts);

		IBankService bs = new BankService(arp, trp, urp);

		String result = bs.createUser("");

		assertEquals("Username provided cannot be an empty String!", result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testCannotCreateNullUser() throws NullPointerException {

			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(null);
	}

	@Test
	public void testCreateAccountSuccess() throws InvalidStartAccountAmountException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

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

			String createAccountResult = bs.createAccount(newAccount);

			assertEquals(SUCCESS, createAccountResult);
		
	}

	@Test(expected = NullPointerException.class)
	public void testCannotCreateNullAccount() throws InvalidStartAccountAmountException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createAccount(null);
		

	}

	@Test(expected = exception.InvalidStartAccountAmountException.class)
	public void testInsufficientAmountToCreateAccount()
			throws  InvalidStartAccountAmountException {

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

			// set date
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DATE, 3);
			cal.set(Calendar.YEAR, 2017);
			int uniqueTId = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction newTransaction = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 500.00, uniqueTId, cal.getTime(),
					newAccount.getBalance() + 10.00);
			newAccount.setBalance(newTransaction.getBalance());
			newAccount.getTransactions().add(newTransaction);

			bs.createAccount(newAccount);

		

	}

	@Test
	public void testAccountShowBalanceSuccess() throws InvalidStartAccountAmountException, AccountDoesNotExistException{

		
			List<User> users = new ArrayList<User>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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

		    bs.createAccount(newAccount);
			Account retrievedAccount = bs.showBalance(1);
			
			String balanceResult;
			if(Double.compare(500.00, retrievedAccount.getBalance()) == 0) {
				balanceResult = SUCCESS;
			} else {
				balanceResult = "failure";
			}
			assertEquals(SUCCESS, balanceResult);

	}

	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testAccountDoesNotExist() throws AccountDoesNotExistException, InvalidStartAccountAmountException{

		
			ArrayList<User> users = new ArrayList<User>();
			IUserRepo urp = new UserRepo(users);

			ArrayList<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			ArrayList<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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

			bs.createAccount(newAccount);
			bs.showBalance(5);

	}
	
	@Test
	public void testDepositSuccess() throws AccountDoesNotExistException, 
	InvalidStartAccountAmountException, InsufficientAmountToDepositException{

		
			List<User> users = new ArrayList<User>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			
			bs.createAccount(newAccount);
			
			Account depositedAccount = bs.deposit(1, 300.00);
			double newBalance = depositedAccount.getBalance();
			String depositResult;
			if(Double.compare(800.00, newBalance) == 0){
				depositResult = "Money successfully deposited!";
			} else {
				depositResult = "Failed to deposit money!";
			}
			
			assertEquals("Money successfully deposited!", depositResult);
	}
	
	@Test(expected = exception.InsufficientAmountToDepositException.class)
	public void testInsufficientAmountToDeposit() throws AccountDoesNotExistException, 
					InvalidStartAccountAmountException, InsufficientAmountToDepositException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

			// set date
			Date myDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DATE, 3);
			cal.set(Calendar.YEAR, 2017);
			myDate = cal.getTime();
			int uniqueTId = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction newTransaction = new Transaction("Opening a new account.", "credit", 500.00, uniqueTId, myDate,
					newAccount.getBalance() + 500.00);
			
			newAccount.setBalance(newTransaction.getBalance());
			newAccount.getTransactions().add(newTransaction);
			
			
			bs.createAccount(newAccount);
			
			bs.deposit(1, -100.00);
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testInvalidAccountToDeposit() throws AccountDoesNotExistException, 
					InvalidStartAccountAmountException, InsufficientAmountToDepositException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			
			 bs.createAccount(newAccount);
			
			bs.deposit(40, 400.00);
	}
	
	@Test
	public void testWithdrawalSuccess() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, ExceedDailyWithdrawalAmountException, 
			InsufficientFundsWithdrawalException{

		
			List<User> users = new ArrayList<User>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<Account>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

			// set date
			Date myDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DATE, 3);
			cal.set(Calendar.YEAR, 2017);
			myDate = cal.getTime();
			int uniqueTId = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction newTransaction = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 500.00, uniqueTId, myDate,
					newAccount.getBalance() + 500.00);
			
			newAccount.setBalance(newTransaction.getBalance());
			newAccount.getTransactions().add(newTransaction);
			
			
			bs.createAccount(newAccount);
			
			Account withdrawAccount = bs.withdraw(1, 300.00);
			double newBalance = withdrawAccount.getBalance();
			String depositResult;
			if(Double.compare(newBalance, 200.00) == 0) {
				depositResult = "Money successfully withdrew!";
			} else {
				depositResult = "Failed to withdraw money!";
			}
			
			assertEquals("Money successfully withdrew!", depositResult);
	}
	
	@Test(expected = exception.ExceedDailyWithdrawalAmountException.class)
	public void testExceedDailyLimitWithdrawal() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, ExceedDailyWithdrawalAmountException, 
			InsufficientFundsWithdrawalException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			
			bs.createAccount(newAccount);
			
			bs.withdraw(1, 2000.00);
			
	}
	
	@Test(expected = exception.InsufficientFundsWithdrawalException.class)
	public void testInsufficientFundsWithdrawal() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, ExceedDailyWithdrawalAmountException, 
			InsufficientFundsWithdrawalException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			
			 bs.createAccount(newAccount);
			
			 bs.withdraw(1, 800.00);
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testInvalidAccountWithdrawal() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, ExceedDailyWithdrawalAmountException, 
			InsufficientFundsWithdrawalException{

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			
			bs.createAccount(newAccount);
			
			bs.withdraw(89, 100.00);
			
	}
	
	@Test
	public void testTransferSuccess() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, InsufficientFundsToTransferException {

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			//2nd user & account
			bs.createUser("Ramal");

			User secondUser = bs.getUserRepo().getUsers().get(1);


			int uniqueAId2 = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount2 = new Account(0.00, new ArrayList<>(), uniqueAId2, secondUser);

			// set date
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.MONTH, 9);
			cal2.set(Calendar.DATE, 28);
			cal2.set(Calendar.YEAR, 2017);
			int uniqueTId2 = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction newTransaction2 = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 200.00, uniqueTId2, cal2.getTime(),
					newAccount2.getBalance() + 200.00);
			
			newAccount2.setBalance(newTransaction2.getBalance());
			newAccount2.getTransactions().add(newTransaction2);
			
			 bs.createAccount(newAccount);
			bs.createAccount(newAccount2);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.set(Calendar.MONTH, 10);
			cal3.set(Calendar.DATE, 14);
			cal3.set(Calendar.YEAR, 2017);
			
			bs.fundTransfer(150.00, cal3.getTime(), newAccount2.getAccountNo(), newAccount.getAccountNo());
			
			String transferResult;
			if(Double.compare(350.00, newAccount.getBalance()) == 0) {
				transferResult = "Funds successfully transferred!";
			} else {
				transferResult = "Funds transfer failed!";
			}
			
			assertEquals("Funds successfully transferred!", transferResult);
			
	}
	
	@Test(expected = exception.InsufficientFundsToTransferException.class)
	public void testInsufficientFundsTransfer() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, InsufficientFundsToTransferException {

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			//2nd user & account
			 bs.createUser("Ramal");

			User secondUser = bs.getUserRepo().getUsers().get(1);


			int uniqueAId2 = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount2 = new Account(0.00, new ArrayList<>(), uniqueAId2, secondUser);

			// set date
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.MONTH, 9);
			cal2.set(Calendar.DATE, 28);
			cal2.set(Calendar.YEAR, 2017);
			int uniqueTId2 = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction newTransaction2 = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 200.00, uniqueTId2, cal2.getTime(),
					newAccount2.getBalance() + 200.00);
			
			newAccount2.setBalance(newTransaction2.getBalance());
			newAccount2.getTransactions().add(newTransaction2);
			
			bs.createAccount(newAccount);
			bs.createAccount(newAccount2);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.set(Calendar.MONTH, 10);
			cal3.set(Calendar.DATE, 14);
			cal3.set(Calendar.YEAR, 2017);
			
			bs.fundTransfer(600.00, cal3.getTime(), newAccount2.getAccountNo(), newAccount.getAccountNo());
			
			
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testAccountDoesNotExistForTransfer() throws AccountDoesNotExistException, 
			InvalidStartAccountAmountException, InsufficientFundsToTransferException {

		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			//2nd user & account
			bs.createUser("Ramal");

			User secondUser = bs.getUserRepo().getUsers().get(1);


			int uniqueAId2 = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount2 = new Account(0.00, new ArrayList<Transaction>(), uniqueAId2, secondUser);

			// set date
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.MONTH, 9);
			cal2.set(Calendar.DATE, 28);
			cal2.set(Calendar.YEAR, 2017);
			int uniqueTId2 = uniqueNumberCreator.generateUniqueTransactionId();
			Transaction newTransaction2 = new Transaction(OPENING_NEW_ACCOUNT, CREDIT, 200.00, uniqueTId2, cal2.getTime(),
					newAccount2.getBalance() + 200.00);
			
			newAccount2.setBalance(newTransaction2.getBalance());
			newAccount2.getTransactions().add(newTransaction2);
			
			 bs.createAccount(newAccount);
			bs.createAccount(newAccount2);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.set(Calendar.MONTH, 10);
			cal3.set(Calendar.DATE, 14);
			cal3.set(Calendar.YEAR, 2017);
			
			 bs.fundTransfer(100.00, cal3.getTime(), 9, 10);
	}
	
	@Test
	public void testPrintTransactionLastTenSuccess() throws AccountDoesNotExistException, 
	InvalidStartAccountAmountException, InsufficientAmountToDepositException{
		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

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
			
			int month = 0;
			int day = 13;
			double deposit = 200.00;
			for(int i=0; i<=5; i++) {
				Calendar depositCal = Calendar.getInstance();
				depositCal.set(Calendar.MONTH, month);
				depositCal.set(Calendar.DATE, day);
				depositCal.set(Calendar.YEAR, 2017);
				
				int uniqueDepositId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction deposits = new Transaction(DEPOSIT, DEBIT, deposit, uniqueDepositId, depositCal.getTime(), newAccount.getBalance() + deposit);
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
			for(int i=0; i<=4; i++) {
				Calendar withdrawCal = Calendar.getInstance();
				withdrawCal.set(Calendar.MONTH, month2);
				withdrawCal.set(Calendar.DATE, day2);
				withdrawCal.set(Calendar.YEAR, 2017);
				
				int uniqueWithdrawId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction withdrawals = new Transaction(WITHDRAW, DEBIT, withdraw, uniqueWithdrawId, withdrawCal.getTime(), newAccount.getBalance() - withdraw);
				double newBalance = newAccount.getBalance() - withdraw;
				newAccount.setBalance(newBalance);
				newAccount.getTransactions().add(withdrawals);
				++month;
				++day;
				--withdraw;
				bs.getTransactionRepo().saveTransaction(withdrawals);
			}
			
			bs.getAccountRepo().getAccounts().add(newAccount);
			
			Account retrievalAccount = bs.printTransaction(1);
			
			int t = 11;
			for(int i=0; i<10; i++) {
				
				assertEquals(newAccount.getTransactions().get(t), retrievalAccount.getTransactions().get(i));
				--t;
			}
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testAccountDoesNotExistPrintTransactionLastTen() throws 
				AccountDoesNotExistException, InvalidStartAccountAmountException,
				InsufficientAmountToDepositException{
		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

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
			
			int month = 0;
			int day = 13;
			double deposit = 200.00;
			for(int i=0; i<=5; i++) {
				Calendar depositCal = Calendar.getInstance();
				depositCal.set(Calendar.MONTH, month);
				depositCal.set(Calendar.DATE, day);
				depositCal.set(Calendar.YEAR, 2017);
				
				int uniqueDepositId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction deposits = new Transaction(DEPOSIT, DEBIT, deposit, uniqueDepositId, depositCal.getTime(), newAccount.getBalance() + deposit);
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
			for(int i=0; i<=4; i++) {
				Calendar withdrawCal = Calendar.getInstance();
				withdrawCal.set(Calendar.MONTH, month2);
				withdrawCal.set(Calendar.DATE, day2);
				withdrawCal.set(Calendar.YEAR, 2017);
				
				int uniqueWithdrawId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction withdrawals = new Transaction(WITHDRAW, DEBIT, withdraw, uniqueWithdrawId, withdrawCal.getTime(), newAccount.getBalance() - withdraw);
				double newBalance = newAccount.getBalance() - withdraw;
				newAccount.setBalance(newBalance);
				newAccount.getTransactions().add(withdrawals);
				++month;
				++day;
				--withdraw;
				bs.getTransactionRepo().saveTransaction(withdrawals);
			}
			
			bs.getAccountRepo().getAccounts().add(newAccount);
			
			bs.printTransaction(10);
			
	}
	
	@Test
	public void testPrintTransactionPeriodSuccess() throws AccountDoesNotExistException, 
	InvalidStartAccountAmountException, InsufficientAmountToDepositException{
		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<Transaction>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<Transaction>(), uniqueAId, user);

			// set date
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 4);
			cal.set(Calendar.DATE, 3);
			cal.set(Calendar.YEAR, 2017);
			cal.set(Calendar.HOUR,00);
	        cal.set(Calendar.MINUTE,00);
	        cal.set(Calendar.SECOND,00);
			int uniqueTId = uniqueNumberCreator.generateUniqueTransactionId();
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
			for(int i=0; i<=5; i++) {
				Calendar depositCal = Calendar.getInstance();
				depositCal.set(Calendar.MONTH, month);
				depositCal.set(Calendar.DATE, day);
				depositCal.set(Calendar.YEAR, 2017);
				depositCal.set(Calendar.HOUR,hour);
		        depositCal.set(Calendar.MINUTE,minute);
		        depositCal.set(Calendar.SECOND,second);
				
				int uniqueDepositId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction deposits = new Transaction(DEPOSIT, DEBIT, deposit, uniqueDepositId, depositCal.getTime(), newAccount.getBalance() + deposit);
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
			for(int i=0; i<=4; i++) {
				Calendar withdrawCal = Calendar.getInstance();
				withdrawCal.set(Calendar.MONTH, month2);
				withdrawCal.set(Calendar.DATE, day2);
				withdrawCal.set(Calendar.YEAR, 2017);
				withdrawCal.set(Calendar.HOUR,withHour);
		        withdrawCal.set(Calendar.MINUTE,withMinute);
		        withdrawCal.set(Calendar.SECOND,withSecond);
				
				int uniqueWithdrawId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction withdrawals = new Transaction(WITHDRAW, DEBIT, withdraw, uniqueWithdrawId, withdrawCal.getTime(), newAccount.getBalance() - withdraw);
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
			
			Calendar fromCal = Calendar.getInstance();
			fromCal.set(Calendar.MONTH, 4);
			fromCal.set(Calendar.DATE, 2);
			fromCal.set(Calendar.YEAR, 2017);
			fromCal.set(Calendar.HOUR,00);
	        fromCal.set(Calendar.MINUTE,00);
	        fromCal.set(Calendar.SECOND,00);
			
			Date toDate = new Date();
			Calendar toCal = Calendar.getInstance();
			toCal.set(Calendar.MONTH, 10);
			toCal.set(Calendar.DATE, 30);
			toCal.set(Calendar.YEAR, 2017);
			toCal.set(Calendar.HOUR,00);
	        toCal.set(Calendar.MINUTE,00);
	        toCal.set(Calendar.SECOND,00);
			toDate = toCal.getTime();
			
			Account retrievalAccount = bs.printTransaction(1, fromCal.getTime(), toDate);
			
			List<Transaction> comparingTransactions = new ArrayList<>();
			comparingTransactions.add(newAccount.getTransactions().get(0));
		
			for(int r=5; r<=11; r++) {
				comparingTransactions.add(newAccount.getTransactions().get(r));
			}
			
			
			
			for(int i=0; i<8; i++) {
				assertEquals(comparingTransactions.get(i), retrievalAccount.getTransactions().get(i));
			}
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testInvalidAccountPrintTransactionPeriod() throws AccountDoesNotExistException, 
	InvalidStartAccountAmountException, InsufficientAmountToDepositException{
		
			List<User> users = new ArrayList<>();
			IUserRepo urp = new UserRepo(users);

			List<Transaction> transactions = new ArrayList<>();
			ITransactionRepo trp = new TransactionRepo(transactions);

			List<Account> accounts = new ArrayList<>();
			IAccountRepo arp = new AccountRepo(accounts);

			IBankService bs = new BankService(arp, trp, urp);

			bs.createUser(TEST_USERNAME);

			User user = bs.getUserRepo().getUsers().get(0);

			CreateUniqueNumber uniqueNumberCreator = new CreateUniqueNumber();

			int uniqueAId = uniqueNumberCreator.generateUniqueAccNum();
			Account newAccount = new Account(0.00, new ArrayList<>(), uniqueAId, user);

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
			
			int month = 0;
			int day = 13;
			double deposit = 200.00;
			for(int i=0; i<=5; i++) {
				Calendar depositCal = Calendar.getInstance();
				depositCal.set(Calendar.MONTH, month);
				depositCal.set(Calendar.DATE, day);
				depositCal.set(Calendar.YEAR, 2017);
				
				int uniqueDepositId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction deposits = new Transaction(DEPOSIT, DEBIT, deposit, uniqueDepositId, cal.getTime(), newAccount.getBalance() + deposit);
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
			for(int i=0; i<=4; i++) {
				Calendar withdrawCal = Calendar.getInstance();
				withdrawCal.set(Calendar.MONTH, month2);
				withdrawCal.set(Calendar.DATE, day2);
				withdrawCal.set(Calendar.YEAR, 2017);
				
				int uniqueWithdrawId = uniqueNumberCreator.generateUniqueTransactionId();
				Transaction withdrawals = new Transaction(WITHDRAW, DEBIT, withdraw, uniqueWithdrawId, withdrawCal.getTime(), newAccount.getBalance() - withdraw);
				double newBalance = newAccount.getBalance() - withdraw;
				newAccount.setBalance(newBalance);
				newAccount.getTransactions().add(withdrawals);
				++month;
				++day;
				--withdraw;
				bs.getTransactionRepo().saveTransaction(withdrawals);
			}
			
			bs.getAccountRepo().getAccounts().add(newAccount);
			
			Calendar fromCal = Calendar.getInstance();
			fromCal.set(Calendar.MONTH, 4);
			fromCal.set(Calendar.DATE, 3);
			fromCal.set(Calendar.YEAR, 2017);
			
			Calendar toCal = Calendar.getInstance();
			toCal.set(Calendar.MONTH, 10);
			toCal.set(Calendar.DATE, 30);
			toCal.set(Calendar.YEAR, 2017);
			
			 bs.printTransaction(21, fromCal.getTime(), toCal.getTime());
			
	}
	
	
}
