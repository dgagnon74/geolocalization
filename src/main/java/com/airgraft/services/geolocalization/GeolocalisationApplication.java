package com.airgraft.services.geolocalization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.airgraft.services.geolocalization.services", "com.airgraft.services.geolocalization.controllers"})
public class GeolocalisationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeolocalisationApplication.class, args);
    }

}
