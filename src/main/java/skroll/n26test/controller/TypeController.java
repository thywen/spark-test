package skroll.n26test.controller;

import java.util.ArrayList;

import skroll.n26test.TransactionService;
import spark.Request;
import spark.Response;
import spark.Route;

public class TypeController {
	public static Route getTypes = (Request request, Response response) -> {
		String type = request.params("type");
		ArrayList<Long> ids = TransactionService.getIdsForType(type);
		if (ids == null) {
			return new ArrayList<Long>();
		}
		return ids;
	};
}
