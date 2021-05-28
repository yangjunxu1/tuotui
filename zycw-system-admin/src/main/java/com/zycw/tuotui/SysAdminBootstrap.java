package com.zycw.tuotui;

//import com.zycw.authentication.client.EnableAceAuthClient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * <p>
 * Title: 系统管理服务启动类
 * </p>
 * <p>
 * Description: 
 * </p>
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:29:56	 
 *
 */
@ImportResource({ "classpath:spring-mybatis.xml" })
@EnableEurekaClient
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
},scanBasePackages = {
		"com.zycw"
})

@EnableScheduling	//开启对计划任务的支持,@Scheduled(fixedRate = 5000) 通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间执行
//@EnableAceAuthClient
@EnableTransactionManagement
public class SysAdminBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SysAdminBootstrap.class).web(true).run(args);
        }
}
