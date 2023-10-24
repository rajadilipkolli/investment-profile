package com.example.sipservice.controller;

import com.example.sipservice.aspect.LogMethodInvocationAndParams;
import com.example.sipservice.models.SipVO;
import com.example.sipservice.service.SipService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class SipController {

    private final SipService sipService;

    public SipController(SipService sipService) {
        this.sipService = sipService;
    }

    @LogMethodInvocationAndParams
    @PostMapping("/sip")
    BigDecimal calculateSIP(@RequestBody SipVO sipVO) {
        return sipService.calculateSIP(sipVO);
    }
}
