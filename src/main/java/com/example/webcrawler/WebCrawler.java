package com.example.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebCrawler {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawler.class, args);
	}

}
