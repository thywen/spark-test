package skroll.n26test;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import skroll.n26test.utility.Status;

public class StatusTest {
	private Status status;
	private JSONObject jo;
	
	@Before
	public void setUp() {
		status = new Status();
		jo = new JSONObject();
	}
	
	@Test
	public void statusOk() {
		jo.put("Status", "OK");
		assertEquals(jo, status.statusOK());
	}
	
	public void statusNotFound() {
		jo.put("Status", "Not Found");
		assertEquals(jo, status.statusNotFound());
	}

}
