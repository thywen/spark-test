package skroll.n26test;
import org.json.simple.JSONObject;

public class Status {
	private final String STATUS_NAME = "Status";
	private final String OK = "OK";
	public JSONObject statusOK(){
		JSONObject jo = new JSONObject();
		jo.put(STATUS_NAME, OK);
		return jo;	
	}
}
