package pojo;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private double balance;
	private List<Transaction> transactions;
	private int accountNo;
	private User user;
	
	
	public Account() {
		super();
	}

	public Account(double balance, List<Transaction> transactions, int accountNo, User user) {
		this.balance = balance;
		this.transactions = transactions;
		this.accountNo = accountNo;
		this.user = user;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public int getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
