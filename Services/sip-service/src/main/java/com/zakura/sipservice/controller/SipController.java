package com.zakura.sipservice.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zakura.sipservice.aspect.LogMethodInvocationAndParams;
import com.zakura.sipservice.models.SipVO;
import com.zakura.sipservice.service.SipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor
public class SipController {

	private final SipService sipService;

	@LogMethodInvocationAndParams
	@PostMapping("/sip")
	BigDecimal calculateSIP(@RequestBody SipVO sipVO) {
		return sipService.calculateSIP(sipVO);
	}
}
