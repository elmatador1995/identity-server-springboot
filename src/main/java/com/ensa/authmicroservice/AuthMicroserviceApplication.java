package com.ensa.authmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AuthMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}
}
