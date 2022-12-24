/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zakura.apigateway.models.investment.Investment;
import com.zakura.apigateway.models.investment.Stock;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestData {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String INVESTMENT_RESULT = "./src/test/resources/Investment.json";
    private static final String INVESTMENT_LIST_RESULT = "./src/test/resources/InvestmentList.json";
    private static final String PROFIT_INVESTMENT_LIST_RESULT =
            "./src/test/resources/ProfitInvestmentList.json";
    private static final String LOSS_INVESTMENT_LIST_RESULT =
            "./src/test/resources/LossInvestmentList.json";
    private static final String STOCK_LIST_RESULT = "./src/test/resources/StockList.json";
    private static final String STOCK = "./src/test/resources/Stock.json";

    public static final String USER_ID = "userId";
    public static final String AUTHORIZATION_STRING = "Authorization";
    public static final String AUTHORIZATION_HEADER_VALUE =
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc3AxOTE5MTlAZ21haWwuY29tIiwiaWF0IjoxNjEwMDMzMjM4LCJleHAiOjE2MTAwNDMyMzh9.2ja-lZVFWFLV6Hk-7zxxLQKoA6FrAYGpH-R2wa4-Dm6TUUGMpiuOKLPLws2eUn7l5JFELsNVIpu4B3n1We0-yg";
    public static final String INVALID_AUTHORIZATION_HEADER_VALUE =
            "Bearer eyJhbGciOiJUzUxMiJ9.eyJzdWIiOiJzc3AxOTE5MTlAZ21haWwuY29tIiwiaWF0IjoxNjEwMDMzMjM4LCJleHAiOjE2MTAwNDMyMzh9.2ja-lZVFWFLV6Hk-7zxxLQKoA6FrAYGpH-R2wa4-Dm6TUUGMpiuOKLPLws2eUn7l5JFELsNVIpu4B3n1We0-yg";
    public static final String SUCCESS = "SUCCESS";

    public static Investment[] getInvestmentArray()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(INVESTMENT_LIST_RESULT), Investment[].class);
    }

    public static List<Investment> getInvestmentList()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return Arrays.asList(getInvestmentArray());
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

    public static Investment getInvestment()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(INVESTMENT_RESULT), Investment.class);
    }

    public static String getInvestmentString()
            throws JsonProcessingException, JsonSyntaxException, JsonIOException,
                    FileNotFoundException {
        return mapper.writeValueAsString(getInvestment());
    }

    public static Optional<Investment> getInvestmentOptional()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return Optional.of(getInvestment());
    }

    public static Stock[] getStockArray()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(STOCK_LIST_RESULT), Stock[].class);
    }

    public static ArrayList<Stock> getStockList()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return new ArrayList<>(Arrays.asList(getStockArray()));
    }

    public static Stock getStock()
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(STOCK), Stock.class);
    }

    public static String getStockString()
            throws JsonSyntaxException, JsonIOException, JsonProcessingException,
                    FileNotFoundException {
        return mapper.writeValueAsString(getStock());
    }
}
