package com.example.Lab04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"controller",
		"service",
		"model",
		"com.example.Lab04"
})
public class Lab04Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab04Application.class, args);
	}
}
