package com.mm.config;

import com.mm.controller.MainController;
import com.mm.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by lmy on 2018/2/9.
 */
@Configuration
@ComponentScan(basePackageClasses = {LoginService.class, MainController.class})
@EnableTransactionManagement
@Import({JpaConfig.class, StartupConfig.class})
public class SpringConfig {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:test.db");
        return dataSource;
    }

}
