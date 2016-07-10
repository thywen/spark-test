package skroll.n26test.api_test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;
import org.junit.Test;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;

import edu.emory.mathcs.backport.java.util.Arrays;
import skroll.n26test.Status;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TransactionApiTest {
	private static String BASE_URL = "https://sven-n26-staging.herokuapp.com";
	private static String TRANSACTION_URL = BASE_URL + "/transaction";
	private static String TYPE_URL = BASE_URL + "/types";
	private static String EXAMPLE_TYPE = "car";
	private static long MAX_ID = 23022320;
	
	@Test
	public void testPutValidTransactionStatus(){
		long transactionId = randomTransactionId();
		JSONObject transaction = createTransaction();
		String statusOk = new Status().statusOK().toJSONString();
			given()
				.contentType("application/json").
				body(transaction.toJSONString()).
			when().
				put(TRANSACTION_URL + "/" + Long.toString(transactionId)).
			then().
				statusCode(200).
				body(equalTo(statusOk));
	}
	
	@Test
	public void testPutInvalidTransactionStatus(){
		long transactionId = randomTransactionId();
		JSONObject transaction = createTransaction();
		transaction.put("I", "Willfail");
		String statusError = new Status().statusError().toJSONString();
		given().
			contentType("application/json").
			body(transaction.toJSONString()).
		when().
			put(TRANSACTION_URL + "/" + Long.toString(transactionId)).
		then().
			statusCode(500).
			body(equalTo(statusError));
	}
	
	@Test
	public void updateExistingTransaction(){	
		long transactionId = randomTransactionId();
		JSONObject transaction = createTransaction();
		addTransaction(transactionId, transaction);
		JSONObject newTransaction = createTransaction();
		addTransaction(transactionId, newTransaction);
		Response response = given().
			contentType("application/json").
			body(newTransaction.toJSONString()).
		when().
			contentType("application/json").
			get(TRANSACTION_URL + "/" + Long.toString(transactionId));
		String body = response.body().asString();
		assertEquals(newTransaction.toString(), body);
	}
	
	@Test
	public void testStatusNotFound() {
		String unknownTransaction = "/2302232012";
		Response resp = get(TRANSACTION_URL + unknownTransaction); 
		String statusNotFound = new Status().statusNotFound().toJSONString();
		assertEquals(statusNotFound, resp.asString());
	}
	
	@Test
	public void getTypes() {
		long transactionId = randomTransactionId();
		JSONObject transaction = createTransaction();
		addTransaction(transactionId, transaction);
		Response response = get(TYPE_URL + '/' + EXAMPLE_TYPE);
		String[] body = response.body().asString().replaceAll("[\\p{Pe}\\s]", "").split(",");
		assertTrue(Arrays.asList(body).contains(String.valueOf(transactionId)));
	}
	
	private void addTransaction(long transactionId, JSONObject transaction) {
		given()
    	.contentType("application/json").
    	body(transaction).
        when().
        put(buildTransactionUrl(transactionId));
	}
	
	private JSONObject createTransaction() {
		JSONObject jo = new JSONObject();
		jo.put("type", EXAMPLE_TYPE);
		jo.put("amount", randomAmount());
		return jo;
	}
	
	private long randomTransactionId(){
		Random random = new Random();
		return Math.abs(random.nextLong()) % MAX_ID;
	}
	
	private double randomAmount() {
		Random random = new Random();
		return random.nextDouble();
	}
	
	private String buildTransactionUrl(long transactionId) {
		return TRANSACTION_URL + "/" + Long.toString(transactionId);
	}
}
