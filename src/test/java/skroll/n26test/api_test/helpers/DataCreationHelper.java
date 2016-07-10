package skroll.n26test.api_test.helpers;

import java.util.Random;

import org.json.simple.JSONObject;

public class DataCreationHelper {
	private final String EXAMPLE_TYPE = "car";
	private final long MAX_ID = 23022320;
	
	public long getMaxId() {
		return MAX_ID;
	}
	
	public String getExampleType() {
		return EXAMPLE_TYPE;
	}
	
	public JSONObject createTransaction(String type) {
		JSONObject jo = new JSONObject();
		jo.put("type", type);
		jo.put("amount", randomAmount());
		return jo;
	}
	
	public JSONObject createTransaction() {
		return createTransaction(EXAMPLE_TYPE);
	}
	
	public long randomTransactionId(){
		Random random = new Random();
		return Math.abs(random.nextLong()) % MAX_ID;
	}
	
	public double randomAmount() {
		Random random = new Random();
		return random.nextDouble();
	}
	
}
