package skroll.n26test.api_test;

import static org.junit.Assert.*;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import edu.emory.mathcs.backport.java.util.Arrays;
import skroll.n26test.Status;
import skroll.n26test.api_test.helpers.ApiStringHelper;
import skroll.n26test.api_test.helpers.DataCreationHelper;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TransactionApiTest {
	private final String BASE_URL = "https://sven-n26-staging.herokuapp.com";
	private final String TRANSACTION_URL = BASE_URL + "/transaction";
	private final String TYPE_URL = BASE_URL + "/types";
	private final String SUM_URL = BASE_URL + "/sum";
	private DataCreationHelper dataCreationHelper;
	private ApiStringHelper apiStringHelper;
	private JSONObject transaction;
	private long transactionId;
	private final double DELTA = 1e-15;
	
	@Before
	public void setUp() {
		dataCreationHelper = new DataCreationHelper();
		apiStringHelper = new ApiStringHelper();
		transactionId = dataCreationHelper.randomTransactionId();
		transaction = dataCreationHelper.createTransaction();
	}
	
	@Test
	public void getTransactionTypes() {
		addTransaction(transactionId, transaction);
	    given().
        	contentType("application/json").
        when().
        	get(TRANSACTION_URL + "/" + transactionId).
    	then().
	        body(containsString("amount")).
	        body(containsString("type")).
	        statusCode(200);
	}
	
	@Test
	public void testPutValidTransactionStatus(){
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
		addTransaction(transactionId, transaction);
		JSONObject newTransaction = dataCreationHelper.createTransaction();
		addTransaction(transactionId, newTransaction);
		given().
			contentType("application/json").
			body(newTransaction.toJSONString()).
		when().
			contentType("application/json").
			get(TRANSACTION_URL + "/" + Long.toString(transactionId)).
		then().body(equalTo(newTransaction.toString()));
	}
	
	@Test
	public void testStatusNotFound() {
		String unknownTransaction = "/-2";
		Response resp = get(TRANSACTION_URL + unknownTransaction); 
		String statusNotFound = new Status().statusNotFound().toJSONString();
		assertEquals(statusNotFound, resp.asString());
	}
	
	@Test
	public void getTypes() {
		long transactionId = dataCreationHelper.randomTransactionId();
		addTransaction(transactionId, dataCreationHelper.createTransaction());
		Response response = get(TYPE_URL + '/' + dataCreationHelper.getExampleType());
		String[] typesResponse = apiStringHelper.getArrayFromResponse(response);
		assertTrue(Arrays.asList(typesResponse).contains(String.valueOf(transactionId)));
	}
	
	@Test
	public void emptyArrayWhenTypeUnknown() {
		Response response = get(TYPE_URL + '/' + "somerandomcategory");
		assertEquals(response.body().asString(), "[]");
	}
	
	@Test
	public void updatedTypesOldTypeNotInList(){
		String newCategory = "Home";
		long transactionId = dataCreationHelper.randomTransactionId();
		addTransaction(transactionId, dataCreationHelper.createTransaction());
		addTransaction(transactionId, dataCreationHelper.createTransaction(newCategory));
		Response response = get(TYPE_URL + '/' + dataCreationHelper.getExampleType());
		String[] typesResponse = apiStringHelper.getArrayFromResponse(response);
		assertFalse(Arrays.asList(typesResponse).contains(String.valueOf(transactionId)));	
	}
	
	@Test
	public void checkSumUnkownTransaction() {
		Long unknownTransaction = Long.parseLong("-1");
		Response resp = get(buildSumUrl(unknownTransaction));
		assertEquals(new Status().statusNotFound().toJSONString(), resp.asString());
	}
	
	@Test
	public void checkSumForSingleTransaction() {
		long transactionId = dataCreationHelper.randomTransactionId();
		double amount = 23.5;
		addTransaction(transactionId, dataCreationHelper.createTransaction(amount));
		Response resp = when().
				get(buildSumUrl(transactionId));	
		JsonPath jsonPath = new JsonPath(resp.asString());
		assertEquals(amount, jsonPath.getDouble("sum"), DELTA);
	}
	
	@Test
	public void checkSumForMultiTransactionChild() {
		long transactionId = dataCreationHelper.randomTransactionId();
		long childTransactionId = dataCreationHelper.randomTransactionId();
		double amount = 23.5;
		double childAmount = 5;
		addTransaction(transactionId, dataCreationHelper.createTransaction(amount));
		addTransaction(childTransactionId, dataCreationHelper.createTransactionWithParent(transactionId, childAmount));
		Response resp = when().
				get(buildSumUrl(childTransactionId));	
		JsonPath jsonPath = new JsonPath(resp.asString());
		assertEquals(childAmount, jsonPath.getDouble("sum"), DELTA);
	}
	
	@Test
	public void checkSumForMultiTransactionParent() {
		long transactionId = dataCreationHelper.randomTransactionId();
		long childTransactionId = dataCreationHelper.randomTransactionId();
		double amount = 23.5;
		double childAmount = 5;
		addTransaction(transactionId, dataCreationHelper.createTransaction(amount));
		addTransaction(childTransactionId, dataCreationHelper.createTransactionWithParent(transactionId, childAmount));
		Response resp = when().
				get(buildSumUrl(transactionId));	
		JsonPath jsonPath = new JsonPath(resp.asString());
		assertEquals(childAmount + amount, jsonPath.getDouble("sum"), DELTA);
	}
	
	private void addTransaction(long transactionId, JSONObject transaction) {
		given()
    	.contentType("application/json").
    	body(transaction).
        when().
        put(buildTransactionUrl(transactionId));
	}
	
	private String buildTransactionUrl(long transactionId) {
		return TRANSACTION_URL + "/" + Long.toString(transactionId);
	}
	
	private String buildSumUrl(long transactionId) {
		return SUM_URL + "/" + Long.toString(transactionId);
	}
	
}
