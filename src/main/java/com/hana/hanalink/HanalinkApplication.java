package com.hana.hanalink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanalinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(HanalinkApplication.class, args);
    }

}
