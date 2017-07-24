package repository;

import java.util.List;

import pojo.Transaction;

public class TransactionRepo implements ITransactionRepo {

	private List<Transaction> transactions;
	private static final String SUCCESS = "success";
	
	public TransactionRepo(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String saveTransaction(Transaction transaction) {
		this.getTransactions().add(transaction);
		
		return SUCCESS;
	}

	public Transaction findTransaction(int transactionId) {
		
		for(Transaction t: this.getTransactions()) {
			if(t.getTransactionId() == transactionId) {
				return t;
			}
		}
		return null;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
}
