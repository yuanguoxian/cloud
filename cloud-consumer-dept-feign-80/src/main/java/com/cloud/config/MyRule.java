package com.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;

@Configuration
public class MyRule {
	@Bean
	public IRule rule() {
		return new RoundRobinRule_Custom();
	}
}
