package com.doctoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DoctoreserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctoreserviceApplication.class, args);
	}

}
