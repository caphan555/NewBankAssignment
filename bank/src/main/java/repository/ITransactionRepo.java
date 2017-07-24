package repository;

import java.util.ArrayList;
import java.util.List;

import pojo.Transaction;

public interface ITransactionRepo {
	public String saveTransaction(Transaction transaction);
	public Transaction findTransaction(int transactionId);
	public List<Transaction> getTransactions();
	public void setTransactions(List<Transaction> transactions);
}
