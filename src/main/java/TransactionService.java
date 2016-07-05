import java.util.ArrayList;
import java.util.List;

public class TransactionService {
	private List<Transaction> transactionList = new ArrayList<Transaction>();
	
	public List<Transaction> getAllTransactions() {
		return transactionList;
	}
	
	public Transaction getTransaction(long id) {
		return transactionList.get((int) (id-1));
	}
	
	public void addTransaction(Transaction transaction) {
		transactionList.add(transaction);
	}

}
