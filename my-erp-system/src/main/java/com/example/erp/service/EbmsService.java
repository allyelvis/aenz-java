package com.example.erp.service;

import com.example.erp.entity.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class EbmsService {

    @Value("${ebms.base.url}")
    private String ebmsBaseUrl;

    private String bearerToken;

    public String authenticate(String username, String password) {
        WebClient webClient = WebClient.create();
        Map<String, String> authRequest = new HashMap<>();
        authRequest.put("username", username);
        authRequest.put("password", password);

        Mono<String> response = webClient.post()
            .uri(ebmsBaseUrl + "/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(authRequest)
            .retrieve()
            .bodyToMono(String.class);

        bearerToken = response.block();
        return bearerToken;
    }

    public String postInvoice(Invoice invoice) {
        WebClient webClient = WebClient.create();
        Mono<String> response = webClient.post()
            .uri(ebmsBaseUrl + "/addInvoice")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invoice)
            .retrieve()
            .bodyToMono(String.class);

        return response.block();
    }
}
