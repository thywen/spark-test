package skroll.n26test.api_test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import com.jayway.restassured.response.Response;

import skroll.n26test.Status;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TransactionApiTest {
	private static String baseUrl = "https://sven-n26-staging.herokuapp.com";
	private static String transactionUrl = baseUrl + "/transaction";
	private static long maxId = 23022320;
	
	@Test
	public void testPutValidTransactionStatus(){
		long transactionId = randomTransactionId();
		JSONObject transaction = createTransaction();
		String statusOk = new Status().statusOK().toJSONString();
			given()
				.contentType("application/json").
				body(transaction.toJSONString()).
			when().
				put(transactionUrl + "/" + Long.toString(transactionId)).
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
			put(transactionUrl + "/" + Long.toString(transactionId)).
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
			get(transactionUrl + "/" + Long.toString(transactionId));
		String body = response.body().asString();
		assertEquals(newTransaction.toString(), body);
	}
	
	@Test
	public void testStatusNotFound() {
		String unknownTransaction = "/2302232012";
		Response resp = get(transactionUrl + unknownTransaction); 
		String statusNotFound = new Status().statusNotFound().toJSONString();
		assertEquals(statusNotFound, resp.asString());
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
		jo.put("type", "Car");
		jo.put("amount", randomAmount());
		return jo;
	}
	
	private long randomTransactionId(){
		Random random = new Random();
		return Math.abs(random.nextLong()) % maxId;
	}
	
	private double randomAmount() {
		Random random = new Random();
		return random.nextDouble();
	}
	
	private String buildTransactionUrl(long transactionId) {
		return transactionUrl + "/" + Long.toString(transactionId);
	}
}
