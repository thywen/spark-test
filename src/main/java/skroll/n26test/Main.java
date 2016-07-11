package skroll.n26test;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.SparkBase.port;

import java.util.ArrayList;

import skroll.n26test.controller.TransactionController;
import skroll.n26test.utility.Path;

public class Main {

    public static void main(String[] args) {
    	TransactionService transactionService = new TransactionService();
    	Status status = new Status();
    	JsonBuilder jsonbuilder = new JsonBuilder();
        port(getHerokuAssignedPort());
        
        get(Path.Web.TRANSACTION, TransactionController.getTransaction);
        
        put(Path.Web.TRANSACTION, TransactionController.putTransaction);
        
        
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