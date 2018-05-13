package com.prizy.pricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ApplicationEntry {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationEntry.class, args);
	}

	
}
