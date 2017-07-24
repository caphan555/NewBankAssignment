package repository;

import java.util.ArrayList;

import pojo.Account;

public interface IAccountRepo {
	public String saveAccount(Account account);
	public Account findAccount(int accountNumber);
	public void setAccounts(ArrayList<Account> accounts);
	public ArrayList<Account> getAccounts();
}
