package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.cloud"})
@ComponentScan(basePackages = {"com.cloud"})
public class DeptConsumerFeign80_App {
	public static void main(String[] args) {
		SpringApplication.run(DeptConsumerFeign80_App.class, args);
	}
}
