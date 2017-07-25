package assignmenttwo.bank;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
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
import utilities.CreateStandardTestUserAccount;

public class BankTest {
	IBankService bs;
	CreateStandardTestUserAccount cstua = new CreateStandardTestUserAccount();
	private static final String SUCCESS = "success";
	private static final String TEST_USERNAME = "John";
	
	@Before
	public void init() {
		List<User> users = new ArrayList<>();
		IUserRepo urp = new UserRepo(users);

		List<Transaction> transactions = new ArrayList<>();
		ITransactionRepo trp = new TransactionRepo(transactions);

		List<Account> accounts = new ArrayList<>();
		IAccountRepo arp = new AccountRepo(accounts);

		bs = new BankService(arp, trp, urp);
	}
	
	@Test
	public void testCreateUserSuccess() {
		

		String result = bs.createUser(TEST_USERNAME);

		assertEquals(SUCCESS, result);
	}

	@Test
	public void testCannotCreateEmptyUser() {

		String result = bs.createUser("");

		assertEquals("Username provided cannot be an empty String!", result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testCannotCreateNullUser() {

			bs.createUser(null);
	}

	@Test
	public void testCreateAccountSuccess() {

		assertEquals(SUCCESS, cstua.createStandardTest(bs));
		
	}

	@Test(expected = NullPointerException.class)
	public void testCannotCreateNullAccount() {

		try{
			bs.createAccount(null);
		} catch(InvalidStartAccountAmountException e) {
			e.printStackTrace();
		}
		
	}

	@Test(expected = exception.InvalidStartAccountAmountException.class)
	public void testInsufficientAmountToCreateAccount() throws  InvalidStartAccountAmountException{

		
		Account newAccount =	cstua.createVariantAccount(bs, 10.00, 10.00);
		bs.createAccount(newAccount);
	}

	@Test
	public void testAccountShowBalanceSuccess() {

		try{
			cstua.createStandardTest(bs);
			Account retrievedAccount = bs.showBalance(1);
			
			String balanceResult;
			if(Double.compare(500.00, retrievedAccount.getBalance()) == 0) {
				balanceResult = SUCCESS;
			} else {
				balanceResult = "failure";
			}
			assertEquals(SUCCESS, balanceResult);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testAccountDoesNotExist() throws AccountDoesNotExistException{

			cstua.createStandardTest(bs);
			bs.showBalance(5);
		
	}
	
	@Test
	public void testDepositSuccess() {

		try{
			cstua.createStandardTest(bs);
			
			Account depositedAccount = bs.deposit(1, 300.00);
			double newBalance = depositedAccount.getBalance();
			String depositResult;
			if(Double.compare(800.00, newBalance) == 0){
				depositResult = "Money successfully deposited!";
			} else {
				depositResult = "Failed to deposit money!";
			}
			
			assertEquals("Money successfully deposited!", depositResult);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = exception.InsufficientAmountToDepositException.class)
	public void testInsufficientAmountToDeposit() throws InsufficientAmountToDepositException{

		try{
			cstua.createStandardTest(bs);
			
			bs.deposit(1, -100.00);
		} catch(AccountDoesNotExistException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testInvalidAccountToDeposit() throws AccountDoesNotExistException{

		try{
			cstua.createStandardTest(bs);
			
			bs.deposit(40, 400.00);
		} catch(InsufficientAmountToDepositException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithdrawalSuccess() {
		try {
			cstua.createStandardTest(bs);
			
			Account withdrawAccount = bs.withdraw(1, 300.00);
			double newBalance = withdrawAccount.getBalance();
			String depositResult;
			if(Double.compare(newBalance, 200.00) == 0) {
				depositResult = "Money successfully withdrew!";
			} else {
				depositResult = "Failed to withdraw money!";
			}
			
			assertEquals("Money successfully withdrew!", depositResult);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = exception.ExceedDailyWithdrawalAmountException.class)
	public void testExceedDailyLimitWithdrawal() throws ExceedDailyWithdrawalAmountException{

		try{
			Account newAccount = cstua.createVariantAccount(bs, 5000.00, 500.00);
			
			
			
			
			bs.createAccount(newAccount);
			
			//Setting some withdrawals on the same day
			for(int i=0; i<=2; ++i) {
				bs.withdraw(1, 100.00);
			}
			
			bs.withdraw(1, 800.00);
		} catch(AccountDoesNotExistException | InvalidStartAccountAmountException | InsufficientFundsWithdrawalException e) {
			e.printStackTrace();
		}
			
	}
	
	@Test(expected = exception.InsufficientFundsWithdrawalException.class)
	public void testInsufficientFundsWithdrawal() throws InsufficientFundsWithdrawalException{

		try{
			Account newAccount = cstua.setUpNormalAccount(bs);
			
			 bs.createAccount(newAccount);
			
			 bs.withdraw(1, 700.00);
		} catch(AccountDoesNotExistException | InvalidStartAccountAmountException |
				ExceedDailyWithdrawalAmountException e) {
			e.printStackTrace();
		}
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testInvalidAccountWithdrawal() throws AccountDoesNotExistException {

		try{
			cstua.createStandardTest(bs);
			
			bs.withdraw(89, 100.00);
		} catch(ExceedDailyWithdrawalAmountException | 
			InsufficientFundsWithdrawalException e) {
			e.printStackTrace();
		}
			
	}
	
	@Test
	public void testTransferSuccess() {
		try{
			List<Account> accounts = cstua.setUpTransferAccounts(bs);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.set(Calendar.MONTH, 10);
			cal3.set(Calendar.DATE, 14);
			cal3.set(Calendar.YEAR, 2017);
			
			bs.fundTransfer(150.00, cal3.getTime(), accounts.get(1).getAccountNo(), accounts.get(0).getAccountNo());
			
			String transferResult;
			
			if(Double.compare(350.00, accounts.get(0).getBalance()) == 0) {
				transferResult = "Funds successfully transferred!";
			} else {
				transferResult = "Funds transfer failed!";
			}
			
			assertEquals("Funds successfully transferred!", transferResult);
		} catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	@Test(expected = exception.InsufficientFundsToTransferException.class)
	public void testInsufficientFundsTransfer() throws InsufficientFundsToTransferException {

		try{
			List<Account> accounts = cstua.setUpTransferAccounts(bs);
			
			Calendar cal3 = Calendar.getInstance();
			cal3.set(Calendar.MONTH, 10);
			cal3.set(Calendar.DATE, 14);
			cal3.set(Calendar.YEAR, 2017);
			
			bs.fundTransfer(600.00, cal3.getTime(), accounts.get(1).getAccountNo(), accounts.get(0).getAccountNo());
		} catch(AccountDoesNotExistException e) {
			e.printStackTrace();
		}
			
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testAccountDoesNotExistForTransfer() throws AccountDoesNotExistException {

		try{
			cstua.setUpTransferAccounts(bs);
			
				
			Calendar cal3 = Calendar.getInstance();
			cal3.set(Calendar.MONTH, 10);
			cal3.set(Calendar.DATE, 14);
			cal3.set(Calendar.YEAR, 2017);
			
			 bs.fundTransfer(100.00, cal3.getTime(), 9, 10);
		} catch(InsufficientFundsToTransferException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPrintTransactionLastTenSuccess() {

		try{
			Account newAccount = cstua.setUpForPrintLastTenTransaction(bs);
			Account retrievalAccount = bs.printTransaction(1);
			
			int t = 11;
			for(int i=0; i<10; i++) {
				
				assertEquals(newAccount.getTransactions().get(t), retrievalAccount.getTransactions().get(i));
				--t;
			}
		} catch(AccountDoesNotExistException e) {
			e.printStackTrace();
		}
			
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testAccountDoesNotExistPrintTransactionLastTen() throws 
				AccountDoesNotExistException {
		
			cstua.setUpForPrintLastTenTransaction(bs);
			
			bs.printTransaction(10);
		
			
	}
	
	@Test
	public void testPrintTransactionPeriodSuccess() {
		try{
			Account newAccount = cstua.setUpForPrintPeriodTransaction(bs);
			
			Calendar fromCal = Calendar.getInstance();
			fromCal.set(Calendar.MONTH, 4);
			fromCal.set(Calendar.DATE, 2);
			fromCal.set(Calendar.YEAR, 2017);
			fromCal.set(Calendar.HOUR,00);
	        fromCal.set(Calendar.MINUTE,00);
	        fromCal.set(Calendar.SECOND,00);
			
			Calendar toCal = Calendar.getInstance();
			toCal.set(Calendar.MONTH, 10);
			toCal.set(Calendar.DATE, 30);
			toCal.set(Calendar.YEAR, 2017);
			toCal.set(Calendar.HOUR,00);
	        toCal.set(Calendar.MINUTE,00);
	        toCal.set(Calendar.SECOND,00);
			
			Account retrievalAccount = bs.printTransaction(1, fromCal.getTime(), toCal.getTime());
			
			List<Transaction> comparingTransactions = new ArrayList<>();
			comparingTransactions.add(newAccount.getTransactions().get(0));
		
			for(int r=5; r<=11; r++) {
				comparingTransactions.add(newAccount.getTransactions().get(r));
			}
			
			
			
			for(int i=0; i<8; i++) {
				assertEquals(comparingTransactions.get(i), retrievalAccount.getTransactions().get(i));
			}
		} catch(AccountDoesNotExistException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = exception.AccountDoesNotExistException.class)
	public void testInvalidAccountPrintTransactionPeriod() throws AccountDoesNotExistException{
		
		
			cstua.setUpForPrintPeriodTransaction(bs);
			
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
