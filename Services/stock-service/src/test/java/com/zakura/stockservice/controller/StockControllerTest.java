package com.zakura.stockservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zakura.stockservice.repository.StockRepository;
import com.zakura.stockservice.service.StockService;
import com.zakura.stockservice.util.Constants;

import data.TestData;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

	@InjectMocks
	private StockController stockController;

	@Mock
	private StockRepository stockRepository;

	@Mock
	private StockService stockService;

	private MockMvc mockMvc;

	@BeforeAll
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
	}

	@Test
	public void testGetAvailableStocks() throws Exception {
		Mockito.when(stockRepository.findAll()).thenReturn(TestData.getStockList());
		mockMvc.perform(get("/view/all").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testSaveUserStock() throws Exception {
		final String body = TestData.getStockRequestString();
		Mockito.when(stockRepository.findByNameAndInvestmentType(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(TestData.getOptionalStock());
		Mockito.when(stockService.saveUserStock(Mockito.any(), Mockito.anyString()))
				.thenReturn(Constants.TRANSACTION_SUCCESFUL);
		mockMvc.perform(post("/add/{userId}", TestData.USER_ID).content(body).param("request", body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();
	}
}
