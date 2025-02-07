package com.example.spring4shellweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Spring4ShellWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Spring4ShellWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Spring4ShellWebApplication.class);
    }
}
