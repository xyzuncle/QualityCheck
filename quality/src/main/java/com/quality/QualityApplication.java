package com.quality;


import org.flowable.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class QualityApplication {

	public static void main(String[] args) {
		SpringApplication.run(QualityApplication.class, args);
	}
}
