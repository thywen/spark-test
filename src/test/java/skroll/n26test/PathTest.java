package skroll.n26test;

import static org.junit.Assert.*;

import org.junit.Test;

import skroll.n26test.utility.Path;

public class PathTest {
	
	@Test
	public void testTransactionIdPath() {
		String stringPath = "/TransactionService/transaction/:transaction_id";
		assertEquals(stringPath, Path.TRANSACTION);
	}
	
	@Test
	public void testSumIdPath() {
		String stringPath = "/TransactionService/sum/:transaction_id";
		assertEquals(stringPath, Path.SUM);
	}
	
	@Test
	public void testTypeIdPath() {
		String stringPath = "/TransactionService/types/:type";
		assertEquals(stringPath, Path.TYPE);
	}

}
