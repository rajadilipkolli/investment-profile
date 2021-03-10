package com.zakura.portfolioservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zakura.portfolioservice.repository.PortfolioRepository;

import data.TestData;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioControllerTest {

	@InjectMocks
	private PortfolioController portfolioController;

	@Mock
	private PortfolioRepository portfolioRepository;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();
	}

	@Test
	public void testGetAllInvestments() throws Exception {
		Mockito.when(portfolioRepository.findByUserName(Mockito.anyString()))
				.thenReturn(TestData.getInvestmentListOptional());
		mockMvc.perform(get("/investments/all/{userId}", new Object[] { TestData.USER_ID })
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testGetProfitInvestments() throws Exception {
		Mockito.when(portfolioRepository.findByUserName(Mockito.anyString()))
				.thenReturn(TestData.getProfitInvestmentListOptional());
		mockMvc.perform(get("/investments/profit/{userId}", new Object[] { TestData.USER_ID })
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testGetLossInvestments() throws Exception {
		Mockito.when(portfolioRepository.findByUserName(Mockito.anyString()))
				.thenReturn(TestData.getLossInvestmentListOptional());
		mockMvc.perform(get("/investments/loss/{userId}", new Object[] { TestData.USER_ID })
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testAddUserInvestment() throws Exception {
		Mockito.when(portfolioRepository.findByUserNameAndNameAndType(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(TestData.getInvestmentOptional());
		Mockito.when(portfolioRepository.deleteByUserNameAndNameAndType(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(Integer.valueOf(1));
		Mockito.when(portfolioRepository.save(Mockito.any())).thenReturn(TestData.getInvestment());
		final String body = TestData.getInvestmentString();
		mockMvc.perform(post("/investments/add/{userId}", new Object[] { TestData.USER_ID }).content(body)
				.param("request", body).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateUserInvestment() throws Exception {

		Mockito.when(portfolioRepository.findByUserNameAndNameAndType(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(TestData.getInvestmentOptional());
		Mockito.when(portfolioRepository.deleteByUserNameAndNameAndType(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(Integer.valueOf(1));
		Mockito.when(portfolioRepository.save(Mockito.any())).thenReturn(TestData.getInvestment());
		final String body = TestData.getInvestmentString();
		mockMvc.perform(post("/investments/update/{userId}", new Object[] { TestData.USER_ID }).content(body)
				.param("request", body).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testDeleteUserInvestment() throws Exception {

		Mockito.when(portfolioRepository.findByUserNameAndNameAndType(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(TestData.getInvestmentOptional());
		Mockito.when(portfolioRepository.deleteByUserNameAndNameAndType(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(Integer.valueOf(1));
		final String body = TestData.getInvestmentString();
		mockMvc.perform(post("/investments/delete/{userId}", new Object[] { TestData.USER_ID }).content(body)
				.param("request", body).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();

	}
}
