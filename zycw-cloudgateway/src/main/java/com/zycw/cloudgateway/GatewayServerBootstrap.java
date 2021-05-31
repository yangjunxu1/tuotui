package com.zycw.cloudgateway;

import com.zycw.cloudgateway.filter.JwtTokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

//import com.newtouch.authentication.client.EnableAceAuthClient;


/**
 * <p>
 * Title: springcloud2.0中的gateway替代zuul作为网关服务，提高性能.
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author yangjunxu
 * @version 1.0
 * @Date 2018年4月4日  上午12:29:56
 * @since
 */
@SpringBootApplication(scanBasePackages = {
        "com.zycw.cloudgateway.config"
})
@EnableDiscoveryClient
//@EnableAceAuthClient
//@EnableFeignClients({"com.newtouch.authentication.client.feign","com.newtouch.cloudgateway.feign"})
public class GatewayServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerBootstrap.class, args);
    }

}
