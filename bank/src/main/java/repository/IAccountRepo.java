package repository;

import java.util.ArrayList;
import java.util.List;

import pojo.Account;

public interface IAccountRepo {
	public String saveAccount(Account account);
	public Account findAccount(int accountNumber);
	public void setAccounts(List<Account> accounts);
	public List<Account> getAccounts();
}
