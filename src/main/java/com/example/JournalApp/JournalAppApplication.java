package com.example.JournalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement // Either all operations succeed OR everything is rolled back
public class JournalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAppApplication.class, args);
	}



}
