package com.meizx.project_fund_manager_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@SpringBootApplication
@MapperScan(basePackages = {"com.meizx.project_fund_manager_system.dao"})
public class ProjectFundManagerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectFundManagerSystemApplication.class, args);
    }

}
