package com.airgraft.services.apiaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.airgraft.services.apiaccess.config",
        "com.airgraft.services.apiaccess.services",
        "com.airgraft.services.apiaccess.controllers",
        "com.airgraft.services.apiaccess.filters",
})
public class GeolocalizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeolocalizationApplication.class, args);
    }


}
