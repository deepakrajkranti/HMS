package com.appointment.gatewayServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport() {
        System.out.println("⚠️ Fallback HIT");
        return Mono.just("An error occurred. Please try after some time or contact support team!!!");
    }

}