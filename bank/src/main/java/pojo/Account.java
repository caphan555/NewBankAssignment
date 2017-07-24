package pojo;

import java.util.ArrayList;

public class Account {
	private double balance;
	private ArrayList<Transaction> transactions;
	private int accountNo;
	private User user;
	
	
	public Account() {
		super();
	}

	public Account(double balance, ArrayList<Transaction> transactions, int accountNo, User user) {
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
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(ArrayList<Transaction> transactions) {
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
