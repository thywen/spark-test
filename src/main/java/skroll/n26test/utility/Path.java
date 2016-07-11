package skroll.n26test.utility;

import lombok.Getter;

public class Path {
	private static String BASE = "/TransactionService";
	@Getter public static final String TRANSACTION = BASE + "/transaction/:transaction_id";
	@Getter public static final String TYPE = BASE + "/types/:type";
	@Getter public static final String SUM = BASE +"/sum/:transaction_id";
}
