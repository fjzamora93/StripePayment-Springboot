package org.stopmultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.PostConstruct;


// TRAS ARRANCAR, ACCEDER A http://localhost:8080/api/
@SpringBootApplication
@EntityScan({"org.stopmultas.config"})

public class StopMultasApplication {

    public static void main(String[] args) {
        SpringApplication.run(StopMultasApplication.class, args);
    }

}