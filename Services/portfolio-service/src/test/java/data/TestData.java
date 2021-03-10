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
import com.zakura.portfolioservice.models.Investment;

public class TestData {

	private static ObjectMapper mapper = new ObjectMapper();
	private static final String INVESTMENT_RESULT = "./src/test/resources/Investment.json";
	private static final String INVESTMENT_LIST_RESULT = "./src/test/resources/InvestmentList.json";
	private static final String PROFIT_INVESTMENT_LIST_RESULT = "./src/test/resources/ProfitInvestmentList.json";
	private static final String LOSS_INVESTMENT_LIST_RESULT = "./src/test/resources/LossInvestmentList.json";

	public static final String USER_ID = "userId";

	public static Investment[] getInvestmentArray() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(INVESTMENT_LIST_RESULT), Investment[].class);
	}

	public static ArrayList<Investment> getInvestmentList()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return new ArrayList<>(Arrays.asList(getInvestmentArray()));
	}

	public static Optional<ArrayList<Investment>> getInvestmentListOptional()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return Optional.of(getInvestmentList());
	}

	public static Investment[] getLossInvestmentArray()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(LOSS_INVESTMENT_LIST_RESULT), Investment[].class);
	}

	public static ArrayList<Investment> getLossInvestmentList()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return new ArrayList<>(Arrays.asList(getLossInvestmentArray()));
	}

	public static Optional<ArrayList<Investment>> getLossInvestmentListOptional()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return Optional.of(getLossInvestmentList());
	}

	public static Investment[] getProfitInvestmentArray()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(PROFIT_INVESTMENT_LIST_RESULT), Investment[].class);
	}

	public static ArrayList<Investment> getProfitInvestmentList()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return new ArrayList<>(Arrays.asList(getProfitInvestmentArray()));
	}

	public static Optional<ArrayList<Investment>> getProfitInvestmentListOptional()
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return Optional.of(getProfitInvestmentList());
	}

	public static Investment getInvestment() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		return gson.fromJson(new FileReader(INVESTMENT_RESULT), Investment.class);
	}

	public static String getInvestmentString() throws JsonProcessingException, JsonSyntaxException, JsonIOException, FileNotFoundException {
		return mapper.writeValueAsString(getInvestment());
	}

	public static Optional<Investment> getInvestmentOptional() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return Optional.of(getInvestment());
	}
}
