package com.zakura.sipservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import data.TestData;

@RunWith(MockitoJUnitRunner.class)
public class SipServiceTest {

	@InjectMocks
	private SipService sipService;

	@Test
	public void testCalculateSIP() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		BigDecimal response = sipService.calculateSIP(TestData.getSipVo());
		assertEquals(BigDecimal.valueOf(36993.51), response);
	}
}
