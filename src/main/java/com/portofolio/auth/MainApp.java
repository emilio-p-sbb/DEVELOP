package com.portofolio.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class MainApp {

	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
		log.info("## start log AUTH-COOKIE-BACKEND ##");
	}
	
}