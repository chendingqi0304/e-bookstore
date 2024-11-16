package org.example.ebookstore.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientService {

    private final WebClient webClient;

    public ClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8090").build();
    }

    public List<Integer> getTotals(String request) {
        return webClient.post()
                .uri("/pricecalculate/bookprice")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(List.class).block();
    }
}