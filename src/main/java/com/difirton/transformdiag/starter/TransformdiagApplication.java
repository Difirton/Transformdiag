package com.difirton.transformdiag.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.difirton.transformdiag")
@EnableJpaRepositories(basePackages = "com.difirton.transformdiag.db.repository")
@EntityScan(basePackages = "com.difirton.transformdiag.db.entity")
public class TransformdiagApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformdiagApplication.class, args);
	}
}
