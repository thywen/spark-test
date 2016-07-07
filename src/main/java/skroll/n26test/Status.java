package skroll.n26test;
import org.json.simple.JSONObject;

public class Status {
	private final String STATUS_NAME = "Status";
	private final String OK = "OK";
	private final String NOT_FOUND = "Not Found";
	private final String ERROR = "Error";
	private JSONObject jo;
	
	public Status() {
		jo = new JSONObject();
	}
	
	public JSONObject statusOK(){
		jo.put(STATUS_NAME, OK);
		return jo;	
	}
	
	public JSONObject statusNotFound(){
		jo.put(STATUS_NAME, NOT_FOUND);
		return jo;
	}
	
	public JSONObject statusError(){
		jo.put(STATUS_NAME, ERROR);
		return jo;
	}
}
