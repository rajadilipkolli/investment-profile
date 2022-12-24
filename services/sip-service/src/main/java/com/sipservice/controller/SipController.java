package com.sipservice.controller;

import java.math.BigDecimal;

import com.sipservice.models.SipVO;
import com.sipservice.service.SipService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

@Controller("/calculator")
@Validated
public class SipController {

    @Inject
    private SipService sipService;

    @Post(value = "/sip", consumes = MediaType.APPLICATION_JSON)
    HttpResponse<BigDecimal> calculateSIP(@Body SipVO sipVO) {
        return HttpResponse.ok(sipService.calculateSIP(sipVO));
    }

}
