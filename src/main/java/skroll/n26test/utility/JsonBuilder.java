package skroll.n26test.utility;

import org.json.simple.JSONObject;

public class JsonBuilder {
	private final String SUM_NAME = "sum";
	private JSONObject jo;
	
	public JsonBuilder() {
		jo = new JSONObject();
	}
	
	public JSONObject buildSumJson(double amount) {
		jo.put(SUM_NAME, amount);
		return jo;
	}
}
