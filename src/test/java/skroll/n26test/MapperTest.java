package skroll.n26test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class MapperTest {
	private Mapper mapper;
	private Transaction transaction;
	private final String TYPE = "car";
	private final long TRANSACTION_ID = 1;
	private final double AMOUNT = 23.5;
	private final long PARENT_ID = 5;
	private final double DELTA = 1e-15;
	@Before
	public void setUp() {
		mapper = new Mapper();
		transaction = new Transaction();
		transaction.setType(TYPE);
		transaction.setTransactionId(TRANSACTION_ID);
		transaction.setAmount(AMOUNT);
		transaction.setParentId(PARENT_ID);
	}
	
	@Test
	public void marshallTransaction() {
		JsonNode node = mapper.dataToJson(transaction);
		assertEquals(TYPE, node.get("type").textValue());
		assertEquals(AMOUNT, node.get("amount").doubleValue(), DELTA);
		assertEquals(PARENT_ID, node.get("parent_id").longValue());
		assertNull(node.get("transaction_id"));
	}

}
