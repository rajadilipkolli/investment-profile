package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zakura.stockservice.models.Investment;
import com.zakura.stockservice.models.Stock;
import com.zakura.stockservice.models.StockRequest;

public class TestData {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String STOCK_RESULT = "./src/test/resources/Stock.json";
	private static final String STOCK_LIST_RESULT = "./src/test/resources/StockList.json";
	private static final String STOCK_REQUEST = "./src/test/resources/StockRequest.json";
	private static final String INVESTMENT_REQUEST = "./src/test/resources/Investment.json";
	public static final String USER_ID = "userId";

	public static Stock[] getStockArray() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(STOCK_LIST_RESULT), Stock[].class);
	}

	public static ArrayList<Stock> getStockList() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return new ArrayList<>(Arrays.asList(getStockArray()));
	}

	public static StockRequest getStockRequest() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(STOCK_REQUEST), StockRequest.class);
	}

	public static String getStockRequestString()
			throws JsonSyntaxException, JsonIOException, JsonProcessingException, FileNotFoundException {
		return mapper.writeValueAsString(getStockRequest());
	}

	public static Optional<Stock> getOptionalStock()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		Stock stock = gson.fromJson(new FileReader(STOCK_RESULT), Stock.class);
		return Optional.of(stock);
	}

	public static Investment getInvestment() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(INVESTMENT_REQUEST), Investment.class);
	}
}
