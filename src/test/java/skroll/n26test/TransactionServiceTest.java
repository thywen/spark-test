package skroll.n26test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TransactionServiceTest {
	private Transaction transaction;
	private TransactionService transactionService;
	private final double amount = 20;
	private final String type = "car";
	private final long transactionId = 23;
	private final long parentTransactionId = 22;
	
	@Before
	public void setUp() {
		transactionService = new TransactionService();
		transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setType(type);
		transaction.setParentId(parentTransactionId);
		transaction.setTransactionId(transactionId);
	}
	
	@Test
	public void newTypeSetInArray() {
		transactionService.addTransaction(transactionId, transaction);
		ArrayList<Long> types = transactionService.getIdsForType(transaction.getType());
		assertEquals(1, types.size());
		assertEquals((long) transaction.getTransactionId(), (long) types.get(0));
	}

	@Test
	public void emptyListAtBeginning() {
		int numberOfItems = transactionService.getAllTransactions().size();
		assertEquals("List is not empty", 0, numberOfItems);
	}
	
	@Test
	public void itemAddedProperly() {
		transactionService.addTransaction(transactionId, transaction);
		int numberOfItems = transactionService.getAllTransactions().size();
		assertEquals("Item not added", 1, numberOfItems);
	}
	
	@Test
	public void getExistingItem() {
		transactionService.addTransaction(transactionId, transaction);
		Transaction recievedTransaction = transactionService.getTransaction(transactionId);
		assertEquals("Objects not the same", transaction, recievedTransaction);
	}
}
