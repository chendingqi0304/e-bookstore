package org.example.ebookstore.service;

import org.example.ebookstore.entity.BookTag;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;

@Service
public class ClientService {

    private final WebClient webClient;

    public ClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://gateway:8090").build();
    }

    public List<Integer> getTotals(String request) {
        return webClient.post()
                .uri("/pricecalculate/bookprice")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(List.class).block();
    }

    public List<BookTag> getRelatedTags(String tag) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/relatedtag/RelateTag")
                        .queryParam("tag", tag)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BookTag>>() {})
                .block();
    }
}