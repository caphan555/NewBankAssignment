package repository;

import java.util.ArrayList;

import pojo.Transaction;

public class TransactionRepo implements ITransactionRepo {

	private ArrayList<Transaction> transactions;
	private final static String SUCCESS = "success";
	
	public TransactionRepo(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String saveTransaction(Transaction transaction) {
		this.getTransactions().add(transaction);
		
		return SUCCESS;
	}

	public Transaction findTransaction(int transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
}
