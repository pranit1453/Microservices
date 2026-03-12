package com.demo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {

	 static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
