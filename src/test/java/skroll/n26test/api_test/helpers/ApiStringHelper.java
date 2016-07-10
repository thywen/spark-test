package skroll.n26test.api_test.helpers;

import com.jayway.restassured.response.Response;

public class ApiStringHelper {
	public String[] getArrayFromResponse(Response response) {
		return response.body().asString().replaceAll("[\\p{Pe}\\s]", "").split(",");
	}
}
