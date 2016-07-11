package skroll.n26test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {
	private static Map<Long, Transaction> transactionHash = new HashMap<Long, Transaction>();
	private static Map<String, ArrayList<Long>> transactionTypesHash = new HashMap<String, ArrayList<Long>>();

	
	static Map<Long, Transaction> getAllTransactions() {
		return transactionHash;
	}
	
	static public Transaction getTransaction(long id) {
		return transactionHash.get(id);
	}
	
	static public void addTransaction(long id, Transaction transaction) {
		if (transaction.getParentId() != 0) {
			addChildIdToParent(id, transaction.getParentId());
		}
		putTypeToMap(transaction.getType(), id);
		transactionHash.put(id, transaction);
	}
	
	public static ArrayList<Long> getIdsForType(String type) {
		return transactionTypesHash.get(type);
	}
	
	public static double calculateSum(long id) {
		Transaction transaction = transactionHash.get(id);
		if (transaction.getChildren().size() == 0) {
			return transaction.getAmount();
		} else {
			double amount = transaction.getAmount();
			double amountFromChildren = calculateChildrenAmount(transaction.getChildren());
			return (amount + amountFromChildren);
		}
	}
	
	private static double calculateChildrenAmount(List<Long> children) {
		double amount = 0;
		for (long childId : children) {
			amount += transactionHash.get(childId).getAmount();
		}
		return amount;
	}
	
	private static void putTypeToMap(String type, long id) {
		checkOldTransaction(id, type);
		ArrayList<Long> types = transactionTypesHash.get(type);
		if (types != null) {
			types.add(id);
		} else {
			types = new ArrayList<Long>();
			types.add(id);
		}
		transactionTypesHash.put(type, types);
	}
	
	private static void addChildIdToParent(long childId, long parentId) {
		Transaction parent = transactionHash.get(parentId);
		parent.addChild(childId);
		transactionHash.put(parentId, parent);	
	}
	
	private static void checkOldTransaction(long id, String type){
		Transaction oldTransaction = transactionHash.get(id);
		if (oldTransaction != null && oldTransaction.getType() != type) {
			ArrayList<Long> oldTypes = transactionTypesHash.get(oldTransaction.getType());
			oldTypes.remove(oldTypes.indexOf(oldTransaction.getTransactionId()));
		}
	}
}
