package com.odk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.odk.Entity")
@EnableJpaRepositories(basePackages = "com.odk.Repository")
public class OdcFormationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdcFormationApplication.class, args);
    }

}
