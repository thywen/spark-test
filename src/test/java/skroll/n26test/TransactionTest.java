package skroll.n26test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
	private Transaction transaction;
	@Before
	public void setUp() {
		transaction  = new Transaction();
	}
	
	@Test
	public void setParentId() {
		final long parentId = 1;
		transaction.setParentId(parentId);
		assertEquals("Fields didn't match", transaction.getParentId(), parentId);
	}
	
}
