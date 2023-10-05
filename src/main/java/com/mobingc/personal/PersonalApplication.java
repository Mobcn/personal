package com.mobingc.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PersonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalApplication.class, args);
    }

}
