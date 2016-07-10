package skroll.n26test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {
	private Map<Long, Transaction> transactionHash;
	private Map<String, ArrayList<Long>> transactionTypesHash;
	
	public TransactionService(){		
		transactionHash = new HashMap<Long, Transaction>();
		transactionTypesHash = new HashMap<String, ArrayList<Long>>();
	}
	
	public Map<Long, Transaction> getAllTransactions() {
		return transactionHash;
	}
	
	public Transaction getTransaction(long id) {
		return transactionHash.get(id);
	}
	
	public void addTransaction(long id, Transaction transaction) {
		transactionHash.put(id, transaction);
		putTypeToMap(transaction.getType(), id);
	}
	
	public ArrayList<Long> getIdsForType(String type) {
		return transactionTypesHash.get(type);
	}
	
	private void putTypeToMap(String type, long id) {
		ArrayList<Long> types = transactionTypesHash.get(type);
		if (types != null) {
			//
		} else {
			ArrayList<Long> typesArray = new ArrayList<Long>();
			typesArray.add(id);
			transactionTypesHash.put(type, typesArray);
		}
	}

}
