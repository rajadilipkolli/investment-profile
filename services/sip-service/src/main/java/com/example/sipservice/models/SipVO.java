package com.example.sipservice.models;

import java.io.Serializable;
import java.math.BigDecimal;

public record SipVO(
        BigDecimal monthlyInvestment,
        BigDecimal expectedRateOfInterest,
        int investmentDuration,
        BigDecimal investedAmount,
        BigDecimal predictedReturn)
        implements Serializable {}
