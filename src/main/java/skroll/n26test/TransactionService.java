package skroll.n26test;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {
	private Map<Long, Transaction> transactionHash = new HashMap<Long, Transaction>();
	
	public Map<Long, Transaction> getAllTransactions() {
		return transactionHash;
	}
	
	public Transaction getTransaction(long id) {
		return transactionHash.get(id);
	}
	
	public void addTransaction(long id, Transaction transaction) {
		transactionHash.put(id, transaction);
	}

}
