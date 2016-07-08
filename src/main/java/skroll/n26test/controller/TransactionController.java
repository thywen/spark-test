package skroll.n26test.controller;

import skroll.n26test.Mapper;
import skroll.n26test.Status;
import skroll.n26test.Transaction;
import skroll.n26test.TransactionService;
import spark.Request;
import spark.Response;
import spark.Route;

public class TransactionController {

	public static Route getTransaction = (Request request, Response response) -> {
			TransactionService transactionService = new TransactionService();
			Mapper objectMapper = new Mapper();
			Status status = new Status();
    		long id = Long.parseLong(request.params("transaction_id"));
    		Transaction transaction = transactionService.getTransaction(id);
    		if (transaction == null){
    			return status.statusNotFound();
    		}
            response.status(200);
            response.type("application/json");
            return objectMapper.dataToJson(transaction);
	};
}
