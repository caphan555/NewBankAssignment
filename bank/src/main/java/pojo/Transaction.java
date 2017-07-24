package pojo;

import java.util.Date;

public class Transaction {
	private String description;
	private String type;
	private double amount;
	private int transactionId;
	private Date date;
	private double balance;
	
	public Transaction(String description, String type, double amount, int transactionId, Date date, double balance) {
		this.description = description;
		this.type = type;
		this.amount = amount;
		this.transactionId = transactionId;
		this.date = date;
		this.balance = balance;
	}

	public String getDescription() {
		return description;
	}
	
	public String getType() {
		return type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public double getBalance() {
		return balance;
	}
	
}
