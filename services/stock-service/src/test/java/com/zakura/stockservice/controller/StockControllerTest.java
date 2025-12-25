package com.zakura.stockservice.controller;

import com.zakura.stockservice.repository.StockRepository;
import com.zakura.stockservice.service.StockService;
import com.zakura.stockservice.util.Constants;
import data.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StockController.class)
class StockControllerTest {

	@MockitoBean
	private StockService stockService;

	@MockitoBean
	private StockRepository stockRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAvailableStocks() throws Exception {
		given(stockService.findAll()).willReturn(TestData.getStockList());
		mockMvc.perform(get("/view/all").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	void testSaveUserStock() throws Exception {
		final String body = TestData.getStockRequestString();
		given(stockService.findByNameAndInvestmentType(Mockito.anyString(), Mockito.anyString()))
				.willReturn(TestData.getOptionalStock());
		given(stockService.saveUserStock(Mockito.any(), Mockito.anyString()))
				.willReturn(Constants.TRANSACTION_SUCCESFUL);
		mockMvc.perform(post("/add/{userId}", TestData.USER_ID).content(body)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();
	}
}
