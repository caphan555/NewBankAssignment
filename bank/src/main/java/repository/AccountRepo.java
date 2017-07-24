package repository;

import java.util.ArrayList;

import pojo.Account;

public class AccountRepo implements IAccountRepo {
	
	private ArrayList<Account> accounts;
	private static final String SUCCESS = "success";
	
	public AccountRepo(ArrayList<Account> accounts) {
		super();
		this.accounts = accounts;
	}

	public String saveAccount(Account account) {
		this.getAccounts().add(account);
		
		return SUCCESS;
	}

	public Account findAccount(int accountNumber) {
		Account retrievedAccount = new Account();
		
		for(int i=0; i<this.getAccounts().size(); i++) {
			if(this.getAccounts().get(i).getAccountNo() == accountNumber) {
				retrievedAccount = this.getAccounts().get(i);
				return retrievedAccount;
			}
		}
		
		return null;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	
}
