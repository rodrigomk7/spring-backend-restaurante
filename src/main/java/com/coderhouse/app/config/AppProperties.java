package com.coderhouse.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class AppProperties {

    private String host;
    private Integer port;
    private Integer timeOfLife;
}
