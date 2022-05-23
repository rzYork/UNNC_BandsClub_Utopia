package com.unncbandsclub.utopia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.unncbandsclub.utopia.mapper")
public class    BandsClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandsClubApplication.class, args);
    }

}
