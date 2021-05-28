package com.zycw.tuotui.config;


//import com.newtouch.authentication.client.interceptor.ClientServiceAuthRestInterceptor;
//import com.newtouch.authentication.client.interceptor.ClientUserAuthRestInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zycw.common.handler.GlobalExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ace
 * @date 2017/9/8
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // registry.addInterceptor(getClientServiceAuthRestInterceptor()).
        //        addPathPatterns(getIncludePathPatterns()).addPathPatterns("/api/user/validate");
       // logger.info("sysadmin----添加拦截器ClientServiceAuthRestInterceptor");
       // registry.addInterceptor(getClientUserAuthRestInterceptor()).
       //         addPathPatterns(getIncludePathPatterns());
       // logger.info("sysadmin----添加拦截器ClientUserAuthRestInterceptor");
    }

   /* @Bean
    ClientServiceAuthRestInterceptor getClientServiceAuthRestInterceptor() {
        return new ClientServiceAuthRestInterceptor();
    }

    @Bean
    ClientUserAuthRestInterceptor getClientUserAuthRestInterceptor() {
        return new ClientUserAuthRestInterceptor();
    }
*/
    /**
     * 需要用户和服务认证判断的路径
     * @return
     */
    private ArrayList<String> getIncludePathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                "/element/**",
                "/gateLog/**",
                "/group/**",
                "/groupType/**",
                "/menu/**",
                "/user/**",
                "/api/permissions",
                "/api/user/un/**"
        };
        Collections.addAll(list, urls);
        return list;
    }

}
