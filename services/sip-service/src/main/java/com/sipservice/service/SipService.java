package com.sipservice.service;

import java.math.BigDecimal;

import com.sipservice.models.SipVO;

public interface SipService {

    BigDecimal calculateSIP(SipVO sipVO);
    
}
