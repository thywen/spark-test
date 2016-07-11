package skroll.n26test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import skroll.n26test.helpers.TransactionHelper;
import spark.Request;

public class TransactionHelperTest {
	
	private Request mockedRequest;
	private TransactionHelper transactionHelper;

	@Before
	public void setUp() {
		mockedRequest = mock(Request.class);
		transactionHelper = new TransactionHelper();
	}

	@Test
	public void testConversion() {
		long transactionIdLong = 123456;
		String transactionId = "123456";
		when(mockedRequest.params("transaction_id")).thenReturn(transactionId);
		assertEquals(transactionIdLong, transactionHelper.getIdFromParam(mockedRequest));
	}

}
