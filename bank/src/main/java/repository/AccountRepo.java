package repository;

import java.util.List;

import pojo.Account;

public class AccountRepo implements IAccountRepo {
	
	private List<Account> accounts;
	private static final String SUCCESS = "success";
	
	public AccountRepo(List<Account> accounts) {
		super();
		this.accounts = accounts;
	}

	public String saveAccount(Account account) {
		this.getAccounts().add(account);
		
		return SUCCESS;
	}

	public Account findAccount(int accountNumber) {
	
		for(Account a: this.getAccounts()) {
			if(a.getAccountNo() == accountNumber) {
				return a;
			}
		}
		
		return null;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	
}
