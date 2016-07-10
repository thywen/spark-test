package skroll.n26test;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.SparkBase.port;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
    	Mapper mapper = new Mapper();
    	TransactionService transactionService = new TransactionService();
    	Status status = new Status();
    	JsonBuilder jsonbuilder = new JsonBuilder();
        port(getHerokuAssignedPort());
        
        get("/transaction/:transaction_id", (request, response) -> {
        	try{
        		long id = Long.parseLong(request.params("transaction_id"));
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
        
        put("/transaction/:transaction_id", (request, response) -> {
        	try {
	        	String jsonString = request.body();
	    		long id = Long.parseLong(request.params("transaction_id"));
	        	Transaction transaction = (Transaction) mapper.jsonToObject(jsonString, Transaction.class);
	        	transaction.setTransactionId(id);
	        	transactionService.addTransaction(id, transaction);
	        	return status.statusOK();
        	} catch (IOException e) {
        		response.status(500);
        		return status.statusError();
        	}
        });
        
        get("/types/:type", (request, response) -> {
    		String type = request.params("type");
    		ArrayList<Long> ids = transactionService.getIdsForType(type);
    		if (ids == null) {
    			return new ArrayList<Long>();
    		}
    		return ids;
        });
        
        get("/sum/:transaction_id", (request, response) -> {
	    		long id = Long.parseLong(request.params("transaction_id"));
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