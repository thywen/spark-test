package skroll.n26test;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class StatusTest {
	private Status status;
	
	@Before
	public void setUp() {
		status = new Status();
	}
	
	@Test
	public void statusOk() {
		JSONObject jo = new JSONObject();
		jo.put("Status", "OK");
		assertEquals(jo, status.statusOK());
	}

}
