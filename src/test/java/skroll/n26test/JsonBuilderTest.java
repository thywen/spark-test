package skroll.n26test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import skroll.n26test.utility.JsonBuilder;

public class JsonBuilderTest {
	
	private JsonBuilder jsonBuilder;
	
	@Before
	public void setUp() {
		jsonBuilder = new JsonBuilder();
	}

	@Test
	public void testBuildSumJson() {
		double sum = 27.4;
		String sumObjct = jsonBuilder.buildSumJson(sum).toJSONString();
		assertEquals("{\"sum\":" + sum + "}", sumObjct);
	}

}
