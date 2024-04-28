package com.store.footballstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.store.footballstore.mapper")
public class FootballStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootballStoreApplication.class, args);
    }

}
