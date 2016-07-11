package skroll.n26test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import skroll.n26test.model.Transaction;

public class TransactionTest {
	private Transaction transaction;
	private final String TYPE = "car";
	private final long PARENT_ID = 1;
	private final double AMOUNT = 20.5;
	private final long TRANSACTION_ID = 2;
	private final double DELTA = 1e-15;
	@Before
	public void setUp() {
		transaction  = new Transaction();
		transaction.setParentId(PARENT_ID);
		transaction.setType(TYPE);
		transaction.setAmount(AMOUNT);
		transaction.setTransactionId(TRANSACTION_ID);
	}
	
	@Test
	public void parentId() {
		assertEquals(PARENT_ID, transaction.getParentId());
	}
	
	@Test
	public void type() {
		assertEquals(TYPE, transaction.getType());
	}
	
	@Test
	public void amount() {
		assertEquals(AMOUNT, transaction.getAmount(), DELTA);
	}
	
	@Test
	public void transactionId() {
		assertEquals(TRANSACTION_ID, transaction.getTransactionId());
	}
}
