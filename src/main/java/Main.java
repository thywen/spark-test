import static spark.Spark.get;
import static spark.SparkBase.port;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
    	Mapper mapper = new Mapper();
    	TransactionService transactionService = new TransactionService();
    	transactionService.addTransaction(createDummyTransaction());
        port(getHerokuAssignedPort());
        get("/transaction/:transaction_id", (request, response) -> {
        	try{
        		long id = Long.parseLong(request.params("transaction_id"));
                response.status(200);
                response.type("application/json");
                return mapper.dataToJson(transactionService.getTransaction(id));
        	}
        	catch(NumberFormatException e) {
        		return "Wrong format";
        	}
        });
    }



	static int getHerokuAssignedPort() {
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