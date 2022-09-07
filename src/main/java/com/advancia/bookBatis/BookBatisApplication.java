package com.advancia.bookBatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.advancia.bookBatis.dao")
public class BookBatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookBatisApplication.class, args);
	}

}
