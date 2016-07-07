package skroll.n26test;
import java.io.IOException;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class Mapper {
	private ObjectMapper mapper = new ObjectMapper();
	
	public Mapper() {
		this.mapper.setPropertyNamingStrategy(
			    PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}
	
    public JsonNode dataToJson(Object data) {
    	try {
    		return mapper.valueToTree(data);
    	} catch (IllegalArgumentException e) {
    		return null;
    	}
	}
    
    public Object jsonToObject(String jsonString, Class<?> klass) {
    	try {
			return mapper.readValue(jsonString, klass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
