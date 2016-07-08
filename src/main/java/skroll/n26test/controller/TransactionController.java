package skroll.n26test.controller;

import java.io.IOException;

import skroll.n26test.Mapper;
import skroll.n26test.Status;
import skroll.n26test.Transaction;
import skroll.n26test.TransactionService;
import spark.Request;
import spark.Response;
import spark.Route;

public class TransactionController {
	private static Mapper mapper = new Mapper();
	private static Status status = new Status();

	public static Route getTransaction = (Request request, Response response) -> {
    		long id = Long.parseLong(request.params("transaction_id"));
    		Transaction transaction = TransactionService.getTransaction(id);
    		if (transaction == null){
    			return status.statusNotFound();
    		}
            response.status(200);
            response.type("application/json");
            return mapper.dataToJson(transaction);
	};
	
	public static Route putTransaction = (Request request, Response response) -> {
    	try {
        	String jsonString = request.body();
    		long id = Long.parseLong(request.params("transaction_id"));
        	Transaction transaction = (Transaction) mapper.jsonToObject(jsonString, Transaction.class);
        	TransactionService.addTransaction(id, transaction);
        	return status.statusOK();
    	} catch (IOException e) {
    		return status.statusError();
    	}
	};
}
