package org.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/author/**")
                        .filters(f->f.rewritePath("/author",""))
                        .uri("lb://BOOK-AUTHOR"))
                .route(r->r.path("/pricecalculate/**")
                        .filters(f->f.rewritePath("/pricecalculate",""))
                        .uri("lb://PRICE-CALCULATE")
                )
                .build();
    }

}
