package skroll.n26test;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.SparkBase.port;

import java.io.IOException;
import java.util.ArrayList;

import skroll.n26test.helpers.TransactionHelper;
import skroll.n26test.model.Transaction;
import skroll.n26test.utility.JsonBuilder;
import skroll.n26test.utility.Path;
import skroll.n26test.utility.Status;

public class Main {

    public static void main(String[] args) {
    	Mapper mapper = new Mapper();
    	Status status = new Status();
    	TransactionHelper transactionHelper = new TransactionHelper();
    	TransactionService transactionService = new TransactionService();

    	JsonBuilder jsonbuilder = new JsonBuilder();
        port(getHerokuAssignedPort());
        
        get(Path.TRANSACTION, (request, response) -> {
        	try{
        		long id = transactionHelper.getIdFromParam(request);
        		Transaction transaction = transactionService.getTransaction(id);
        		if (transaction == null){
        			return status.statusNotFound();
        		}
                response.status(200);
                response.type("application/json");
                return mapper.dataToJson(transaction);
        	}
        	catch(NumberFormatException e) {
        		response.status(500);
        		return "Wrong format of id";
        	}
        });
        
        put(Path.TRANSACTION, (request, response) -> {
        	try {
	        	String jsonString = request.body();
	    		long id = transactionHelper.getIdFromParam(request);
	        	Transaction transaction = (Transaction) mapper.jsonToObject(jsonString, Transaction.class);
	        	transaction.setTransactionId(id);
	        	transactionService.addTransaction(id, transaction);
	        	return status.statusOK();
        	} catch (IOException e) {
        		response.status(500);
        		return status.statusError();
        	}
        });
        
        get(Path.TYPE, (request, response) -> {
    		String type = request.params("type");
    		ArrayList<Long> ids = transactionService.getIdsForType(type);
    		if (ids == null) {
    			return new ArrayList<Long>();
    		}
    		return ids;
        });
        
        get(Path.SUM, (request, response) -> {
	    		long id = transactionHelper.getIdFromParam(request);
        		Transaction transaction = transactionService.getTransaction(id);
        		if (transaction == null){
        			return status.statusNotFound();
        		}
	    		return jsonbuilder.buildSumJson(transactionService.calculateSum(id));	
        });
    }

	private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}