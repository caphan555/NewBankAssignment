package repository;

import java.util.ArrayList;

import pojo.Transaction;

public interface ITransactionRepo {
	public String saveTransaction(Transaction transaction);
	public Transaction findTransaction(int transactionId);
	public ArrayList<Transaction> getTransactions();
	public void setTransactions(ArrayList<Transaction> transactions);
}
