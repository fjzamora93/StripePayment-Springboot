package org.stopmultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import io.github.cdimascio.dotenv.Dotenv;

// TRAS ARRANCAR, ACCEDER A http://localhost:8080/api/
@SpringBootApplication
@EntityScan({"org.stopmultas.config"})

public class StopMultasApplication {

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
    public static void main(String[] args) {
        SpringApplication.run(StopMultasApplication.class, args);
    }
}