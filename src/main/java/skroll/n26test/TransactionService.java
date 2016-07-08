package skroll.n26test;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {
	private static Map<Long, Transaction> transactionHash = new HashMap<Long, Transaction>();
	
	static Map<Long, Transaction> getAllTransactions() {
		return transactionHash;
	}
	
	static public Transaction getTransaction(long id) {
		return transactionHash.get(id);
	}
	
	static public void addTransaction(long id, Transaction transaction) {
		transactionHash.put(id, transaction);
	}

}
