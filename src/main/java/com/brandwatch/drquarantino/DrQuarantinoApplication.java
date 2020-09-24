package com.brandwatch.drquarantino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DrQuarantinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrQuarantinoApplication.class, args);
    }

}
