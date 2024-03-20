package com.steganography;

import com.vaadin.flow.server.LoadDependenciesOnStartup;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin
public class SteganographyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteganographyApplication.class, args);
    }
}
