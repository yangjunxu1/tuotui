package com.zycw.cloudgateway.config;

import com.zycw.cloudgateway.filter.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Inspiry
 * Created on 2021/5/31.
 */
@ComponentScan(basePackages = "com.zycw.common.handler")
@Configuration
public class TokenFilterConfig {
    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }
}
