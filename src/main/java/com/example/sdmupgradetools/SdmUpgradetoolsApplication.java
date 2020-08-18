package com.example.sdmupgradetools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value="com")
public class SdmUpgradetoolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdmUpgradetoolsApplication.class, args);
    }

}
