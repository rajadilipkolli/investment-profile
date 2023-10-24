package com.example.sipservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import data.TestData;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SipServiceTest {

    private final SipService sipService = new SipService();

    @Test
    void testCalculateSIP() throws FileNotFoundException {
        BigDecimal response = sipService.calculateSIP(TestData.getSipVo());
        assertEquals(BigDecimal.valueOf(36993.51), response);
    }
}
