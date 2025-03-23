package com.example.pizzeria;

import com.example.pizzeria.console.ConsoleUI;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PizzeriaAppApplication {

	public static void main(String[] args) throws InterruptedException, JsonProcessingException {

		SpringApplication.run(PizzeriaAppApplication.class, args);
		new ConsoleUI().start();

	}

}
