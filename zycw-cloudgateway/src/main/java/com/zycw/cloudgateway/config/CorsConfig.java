package com.zycw.cloudgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * created with IDEA.
 *
 * @Author：wqz
 * @Date：2020-01-09
 * @Description：Mult_AdPlatform
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        //设置预检请求的缓存时间（秒），在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**",config);
        return new CorsWebFilter(source);
    }
}