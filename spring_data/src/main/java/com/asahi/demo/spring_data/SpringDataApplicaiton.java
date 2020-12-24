package com.asahi.demo.spring_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //启用springdata的审计功能
public class SpringDataApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplicaiton.class, args);
    }
}
