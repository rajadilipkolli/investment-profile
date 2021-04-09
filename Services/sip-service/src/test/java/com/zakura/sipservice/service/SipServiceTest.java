package com.zakura.sipservice.service;

import data.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SipServiceTest {

	private final SipService sipService = new SipService();

	@Test
	public void testCalculateSIP() throws FileNotFoundException {
		BigDecimal response = sipService.calculateSIP(TestData.getSipVo());
		assertEquals(BigDecimal.valueOf(36993.51), response);
	}
}
