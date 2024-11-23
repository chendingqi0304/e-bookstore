package org.example.ebookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
public class EBookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBookstoreApplication.class, args);
    }

}
