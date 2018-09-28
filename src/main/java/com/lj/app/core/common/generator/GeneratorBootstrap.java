package com.lj.app.core.common.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 */
@SpringBootApplication
@ComponentScan("com.lj.app.core.common.generator.controller")
public class GeneratorBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorBootstrap.class, args);
    }
}
