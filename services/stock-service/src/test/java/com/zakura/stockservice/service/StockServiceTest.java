package com.zakura.stockservice.service;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zakura.stockservice.client.PortfolioServiceClient;
import com.zakura.stockservice.repository.StockRepository;

import data.TestData;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
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
		assertThat(response).isNotNull();

	}

}
