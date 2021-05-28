package com.zycw.cloudgateway.config;


import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * <p>
 * Title: 路由定义.
 * </p>
 * <p>
 * Description: 
 * </p>
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月8日  上午12:29:56	 
 *
 */
@Configuration
public class RouteConfiguration {
    @Bean
    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient);
    }
}
