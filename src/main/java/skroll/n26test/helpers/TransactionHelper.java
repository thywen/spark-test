package skroll.n26test.helpers;

import spark.Request;

public class TransactionHelper {
	public long getIdFromParam(Request request) {
		return Long.parseLong(request.params("transaction_id"));
	}
} 
