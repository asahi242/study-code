package com.asahi.demo;

import com.asahi.demo.springSecurity.model.AuthUser;
import com.asahi.demo.springSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling // 支持定时任务
@EnableJpaAuditing //启用springdata的审计功能

public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Scheduled(cron = "* 0/2 * * * ?")
    public void getMessage(){
        System.out.println("定时任务执行: "+new Date());
    }
}
