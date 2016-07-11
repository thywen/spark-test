package skroll.n26test.utility;

import lombok.*;

public class Path {
    public static class Web {
    	@Getter public static final String TRANSACTION = "/transaction/:transaction_id";
    	@Getter public static final String TYPE = "/types/:type";
    	@Getter public static final String SUM = "/sum/:transaction_id";
    }
}
