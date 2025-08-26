package com.odk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.odk.Entity")
@EnableJpaRepositories(basePackages = "com.odk.Repository")
@EnableScheduling
public class OdcFormationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdcFormationApplication.class, args);
    }

}
