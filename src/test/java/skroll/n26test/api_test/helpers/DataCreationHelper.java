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
	
	public JSONObject createTransactionWithParent(long parentId, double amount) {
		JSONObject jo = createTransaction(amount);
		jo.put("parent_id", parentId);
		return jo;
	}

	public JSONObject createTransaction(String type, double amount) {
		JSONObject jo = new JSONObject();
		jo.put("type", type);
		jo.put("amount", amount);
		return jo;
	}
	
	public JSONObject createTransaction(String type) {
		return createTransaction(type, randomAmount());
	}
	
	public JSONObject createTransaction() {
		return createTransaction(EXAMPLE_TYPE, randomAmount());
	}
	
	public JSONObject createTransaction(double amount) {
		return createTransaction(EXAMPLE_TYPE, amount);
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
