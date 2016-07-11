package skroll.n26test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TransactionServiceTest {
	private Transaction transaction;
	private final double AMOUNT = 20;
	private final String TYPE = "car";
	private final long TRANSACTION_ID = 23;
	private final String NEW_TYPE = "home";
	private final double DELTA = 1e-15;
	
	@Before
	public void setUp() {
		transaction = buildTransaction(TRANSACTION_ID, TYPE, AMOUNT);
	}
	
	@Test
	public void newTypeSetInArray() {
		TransactionService.addTransaction(transaction.getTransactionId(), transaction);
		ArrayList<Long> types = TransactionService.getIdsForType(transaction.getType());
		assertEquals(1, types.size());
		assertEquals((long) transaction.getTransactionId(), (long) types.get(0));
	}
	
	@Test
	public void existingType() {
		String type = "atotalnewtype";
		transaction = buildTransaction(TRANSACTION_ID, type, AMOUNT);
		TransactionService.addTransaction(transaction.getTransactionId(), transaction);
		Transaction updatedTransaction = new Transaction();
		updatedTransaction.setAmount(AMOUNT);
		updatedTransaction.setType(NEW_TYPE);
		updatedTransaction.setTransactionId(TRANSACTION_ID);
		TransactionService.addTransaction(updatedTransaction.getTransactionId(), updatedTransaction);
		assertEquals(0, TransactionService.getIdsForType(type).size());
		assertEquals(1, TransactionService.getIdsForType(NEW_TYPE).size());
	}
	
	@Test
	public void itemAddedProperly() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		int numberOfItems = TransactionService.getAllTransactions().size();
		assertEquals("Item not added", 1, numberOfItems);
	}
	
	@Test
	public void transactionIDSet() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction transaction = TransactionService.getTransaction(TRANSACTION_ID);
		assertEquals(TRANSACTION_ID, transaction.getTransactionId());
	}
	
	@Test
	public void amountSet() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction transaction = TransactionService.getTransaction(TRANSACTION_ID);
		assertEquals(AMOUNT, transaction.getAmount(), DELTA);
	}
	
	@Test
	public void addNewType() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		assertTrue(TransactionService.getIdsForType(TYPE).contains(TRANSACTION_ID));
	}
	
	@Test
	public void addKnownType() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction secondTransaction =  buildTransaction(TRANSACTION_ID + 1, TYPE, AMOUNT);
		TransactionService.addTransaction(TRANSACTION_ID +1, secondTransaction);
		assertTrue(TransactionService.getIdsForType(TYPE).contains(TRANSACTION_ID)
				&& TransactionService.getIdsForType(TYPE).contains(TRANSACTION_ID + 1));
	}
	
	@Test
	public void getExistingItem() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction recievedTransaction = TransactionService.getTransaction(TRANSACTION_ID);
		assertEquals("Objects not the same", transaction, recievedTransaction);
	}
	
	@Test
	public void addChildToParent() {
		long transactionId = 293;
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction child = buildTransaction(transactionId, "car", 23.5);
		child.setParentId(TRANSACTION_ID);
		TransactionService.addTransaction(transactionId, child);
		Transaction parent = TransactionService.getTransaction(TRANSACTION_ID);
		assertTrue(parent.getChildren().contains(transactionId));
	}
	
	@Test
	public void sumWithoutChildren() {
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		assertEquals(AMOUNT, TransactionService.calculateSum(TRANSACTION_ID), DELTA);
	}
	
	@Test
	public void sumWithChildren() {
		long transactionId = 293;
		double amount = 23.5;
		double expectedAmount = amount + AMOUNT;
		TransactionService.addTransaction(TRANSACTION_ID, transaction);
		Transaction child = buildTransaction(transactionId, "car", amount);
		child.setParentId(TRANSACTION_ID);
		TransactionService.addTransaction(transactionId, child);
		double sum = TransactionService.calculateSum(TRANSACTION_ID);
		assertEquals(expectedAmount, sum, DELTA);
	}
	
	private Transaction buildTransaction(long transactionId, String type, double amount) {
		Transaction transactionToBuild = new Transaction();
		transactionToBuild.setAmount(amount);
		transactionToBuild.setType(type);
		transactionToBuild.setTransactionId(transactionId);
		return transactionToBuild;
	}
}
