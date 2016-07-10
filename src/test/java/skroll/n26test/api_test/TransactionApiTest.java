package skroll.n26test.api_test;

import static org.junit.Assert.*;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
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
	private DataCreationHelper dataCreationHelper;
	private ApiStringHelper apiStringHelper;
	
	@Before
	public void setUp() {
		dataCreationHelper = new DataCreationHelper();
		apiStringHelper = new ApiStringHelper();
	}
	
	@Test
	public void testPutValidTransactionStatus(){
		long transactionId = dataCreationHelper.randomTransactionId();
		JSONObject transaction = dataCreationHelper.createTransaction();
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
		long transactionId = dataCreationHelper.randomTransactionId();
		JSONObject transaction = dataCreationHelper.createTransaction();
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
		long transactionId = dataCreationHelper.randomTransactionId();
		JSONObject transaction = dataCreationHelper.createTransaction();
		addTransaction(transactionId, transaction);
		JSONObject newTransaction = dataCreationHelper.createTransaction();
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
	
}
