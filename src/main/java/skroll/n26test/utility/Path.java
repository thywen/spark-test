package skroll.n26test.utility;

import lombok.*;

public class Path {
    public static class Web {
    	@Getter public static final String TRANSACTION = "/transaction/:transaction_id";
    }
}
