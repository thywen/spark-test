package skroll.n26test;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.SparkBase.port;

import java.io.IOException;

import skroll.n26test.controller.TransactionController;
import skroll.n26test.utility.Path;

public class Main {

    public static void main(String[] args) {
    	TransactionService.addTransaction(29, createDummyTransaction());
        port(getHerokuAssignedPort());
        
        get(Path.Web.TRANSACTION, TransactionController.getTransaction);
        
        put(Path.Web.TRANSACTION, TransactionController.putTransaction);
 
    }

	private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    
	private static Transaction createDummyTransaction(){
		
		Transaction transaction = new Transaction();
		transaction.setAmount(30.5);
		transaction.setType("car");
		
		return transaction;
		
	}

}