package org.example.price_calculate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@SpringBootApplication
public class PriceCalculateApplication {
    @Bean
    public Function<Flux<Integer[]>, Flux<Integer>> book() {
        return e -> e.map(value -> value[0] * value[1]);
    }

    public static void main(String[] args) {
        SpringApplication.run(PriceCalculateApplication.class, args);
    }

}
