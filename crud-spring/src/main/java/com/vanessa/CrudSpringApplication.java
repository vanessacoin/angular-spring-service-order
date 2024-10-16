package com.vanessa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vanessa"})
public class CrudSpringApplication {

    public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

}
