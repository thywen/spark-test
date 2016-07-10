package skroll.n26test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TransactionServiceTest {
	private Transaction transaction;
	private TransactionService transactionService;
	private final double AMOUNT = 20;
	private final String TYPE = "car";
	private final long TRANSACTION_ID = 23;
	private final String NEW_TYPE = "home";
	private final double DELTA = 1e-15;
	
	@Before
	public void setUp() {
		transactionService = new TransactionService();
		transaction = buildTransaction(TRANSACTION_ID, TYPE, AMOUNT);
	}
	
	@Test
	public void newTypeSetInArray() {
		transactionService.addTransaction(transaction.getTransactionId(), transaction);
		ArrayList<Long> types = transactionService.getIdsForType(transaction.getType());
		assertEquals(1, types.size());
		assertEquals((long) transaction.getTransactionId(), (long) types.get(0));
	}
	
	@Test
	public void existingType() {
		transactionService.addTransaction(transaction.getTransactionId(), transaction);
		Transaction updatedTransaction = new Transaction();
		updatedTransaction.setAmount(AMOUNT);
		updatedTransaction.setType(NEW_TYPE);
		updatedTransaction.setTransactionId(TRANSACTION_ID);
		transactionService.addTransaction(updatedTransaction.getTransactionId(), updatedTransaction);
		assertEquals(0, transactionService.getIdsForType(TYPE).size());
		assertEquals(1, transactionService.getIdsForType(NEW_TYPE).size());
	}

	@Test
	public void emptyListAtBeginning() {
		int numberOfItems = transactionService.getAllTransactions().size();
		assertEquals("List is not empty", 0, numberOfItems);
	}
	
	@Test
	public void itemAddedProperly() {
		transactionService.addTransaction(TRANSACTION_ID, transaction);
		int numberOfItems = transactionService.getAllTransactions().size();
		assertEquals("Item not added", 1, numberOfItems);
	}
	
	@Test
	public void transactionIDSet() {
		transactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction transaction = transactionService.getTransaction(TRANSACTION_ID);
		assertEquals(TRANSACTION_ID, transaction.getTransactionId());
	}
	
	@Test
	public void amountSet() {
		transactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction transaction = transactionService.getTransaction(TRANSACTION_ID);
		assertEquals(AMOUNT, transaction.getAmount(), DELTA);
	}
	
	@Test
	public void typeSet() {
		transactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction transaction = transactionService.getTransaction(TRANSACTION_ID);
		assertEquals(TYPE, transaction.getType());
	}
	
	@Test
	public void getExistingItem() {
		transactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction recievedTransaction = transactionService.getTransaction(TRANSACTION_ID);
		assertEquals("Objects not the same", transaction, recievedTransaction);
	}
	
	@Test
	public void addChildToParent() {
		long transactionId = 293;
		transactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction child = buildTransaction(transactionId, "car", 23.5);
		child.setParentId(TRANSACTION_ID);
		transactionService.addTransaction(transactionId, child);
		Transaction parent = transactionService.getTransaction(TRANSACTION_ID);
		assertTrue(parent.getChildren().contains(transactionId));
	}
	
	private Transaction buildTransaction(long transactionId, String type, double amount) {
		Transaction transactionToBuild = new Transaction();
		transactionToBuild.setAmount(amount);
		transactionToBuild.setType(type);
		transactionToBuild.setTransactionId(transactionId);
		return transactionToBuild;
	}
}
