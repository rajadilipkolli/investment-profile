package com.zakura.stockservice.service;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zakura.stockservice.client.PortfolioServiceClient;
import com.zakura.stockservice.repository.StockRepository;

import data.TestData;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

	@InjectMocks
	private StockService stockService;

	@Mock
	private StockRepository stockRepository;

	@Mock
	private PortfolioServiceClient portfolioServiceClient;

	@Test
	public void testSaveUserStock() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Mockito.when(portfolioServiceClient.addUserInvestment(Mockito.any(), Mockito.anyString()))
				.thenReturn(TestData.getInvestment());
		String response = stockService.saveUserStock(TestData.getStockRequest(), TestData.USER_ID);
		assertNotNull(response);

	}

}
