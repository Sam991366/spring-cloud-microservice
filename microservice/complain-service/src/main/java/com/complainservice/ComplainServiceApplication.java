package com.complainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ComplainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplainServiceApplication.class, args);
	}

}
