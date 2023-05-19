package dev.shulika.adminpanel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@Configuration
public class ThymeleafAutoConfiguration {
    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
