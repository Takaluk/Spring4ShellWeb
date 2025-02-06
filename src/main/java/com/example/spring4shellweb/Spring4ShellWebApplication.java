package com.example.spring4shellweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Spring4ShellWebApplication extends SpringBootServletInitializer{
	
    // 이 부분 추가
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Spring4ShellWebApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Spring4ShellWebApplication.class, args);
	}
}