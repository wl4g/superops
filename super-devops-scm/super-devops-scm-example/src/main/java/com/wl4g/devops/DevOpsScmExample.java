package com.wl4g.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DevOpsScmExample {

	public static void main(String[] args) {
		SpringApplication.run(DevOpsScmExample.class, args);
	}

}
