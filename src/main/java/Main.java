import static spark.Spark.*;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {

    public static void main(String[] args) {
    	Transaction transaction = createDummyTransaction();
        port(getHerokuAssignedPort());
        get("/hello", (req, res) -> "Hello Heroku World");
        get("/transaction", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return dataToJson(transaction);
        });
    }

    private static Object dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter?");
        }
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