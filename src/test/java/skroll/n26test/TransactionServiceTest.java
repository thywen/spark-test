package skroll.n26test;

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TransactionServiceTest {
	private Transaction transaction;
	private TransactionService transactionService;
	private final double amount = 20;
	private final long id = 2;
	private final String type = "car";
	
	@Before
	public void setUp() {
		transactionService = new TransactionService();
		transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setType(type);
	}

	@Test
	public void emptyListAtBeginning() {
		int numberOfItems = transactionService.getAllTransactions().size();
		assertEquals("List is not empty", 0, numberOfItems);
	}

}
