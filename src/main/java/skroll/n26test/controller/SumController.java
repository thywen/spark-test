package skroll.n26test.controller;

import skroll.n26test.JsonBuilder;
import skroll.n26test.Status;
import skroll.n26test.Transaction;
import skroll.n26test.TransactionService;
import spark.Request;
import spark.Response;
import spark.Route;

public class SumController {
	public static Route getSum = (Request request, Response response) -> {
		long id = Long.parseLong(request.params("transaction_id"));
		Transaction transaction = TransactionService.getTransaction(id);
		if (transaction == null) {
			return new Status().statusNotFound();
		}
		return new JsonBuilder().buildSumJson(TransactionService.calculateSum(id));
	};
}
