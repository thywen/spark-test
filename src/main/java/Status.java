import org.json.simple.JSONObject;

public class Status {
	public JSONObject statusOK(){
		JSONObject jo = new JSONObject();
		jo.put("Status", "OK");
		return jo;	
	}
}
