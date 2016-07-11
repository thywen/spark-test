package skroll.n26test;
import static spark.Spark.get;
import static spark.Spark.put;
import static spark.SparkBase.port;

import java.util.ArrayList;

import skroll.n26test.controller.SumController;
import skroll.n26test.controller.TransactionController;
import skroll.n26test.controller.TypeController;
import skroll.n26test.utility.Path;

public class Main {
	
	public static TransactionService transactionService = new TransactionService();

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        
        get(Path.Web.TRANSACTION, TransactionController.getTransaction);
        
        put(Path.Web.TRANSACTION, TransactionController.putTransaction);
        
        get(Path.Web.TYPE, TypeController.getTypes);
        
        get(Path.Web.SUM, SumController.getSum);
 
    }

	private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}