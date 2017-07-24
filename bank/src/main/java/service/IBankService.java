package service;

import java.util.Date;

import exception.AccountDoesNotExistException;
import exception.ExceedDailyWithdrawalAmountException;
import exception.InsufficientAmountToDepositException;
import exception.InsufficientFundsToTransferException;
import exception.InsufficientFundsWithdrawalException;
import exception.InvalidStartAccountAmountException;
import pojo.Account;

import repository.IAccountRepo;
import repository.ITransactionRepo;
import repository.IUserRepo;

public interface IBankService {

	public String createUser(String name);
	public String createAccount(Account newAccount) throws InvalidStartAccountAmountException;
	public Account showBalance(int accountNumber) throws AccountDoesNotExistException;
	public Account withdraw(int accountNumber, double amount) throws ExceedDailyWithdrawalAmountException, InsufficientFundsWithdrawalException, AccountDoesNotExistException;
	public Account deposit(int accountNumber, double amount) throws InsufficientAmountToDepositException, AccountDoesNotExistException;
	public Account fundTransfer(double amount, Date date, int toAccountNo, int fromAccount) throws AccountDoesNotExistException, InsufficientFundsToTransferException;
	public Account printTransaction(int accountNumber, Date fromDate, Date toDate) throws AccountDoesNotExistException;
	public Account printTransaction(int accountNumber) throws AccountDoesNotExistException;
	public IAccountRepo getAccountRepo();
	public void setAccountRepo(IAccountRepo accountRepo);
	public ITransactionRepo getTransactionRepo();
	public void setTransactionRepo(ITransactionRepo transactionRepo);
	public IUserRepo getUserRepo();
	public void setUserRepo(IUserRepo userRepo);
}
