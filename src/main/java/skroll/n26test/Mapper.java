package skroll.n26test;
import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {
	private ObjectMapper mapper = new ObjectMapper();
	
    public Object dataToJson(Object data) {
        try {
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
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
