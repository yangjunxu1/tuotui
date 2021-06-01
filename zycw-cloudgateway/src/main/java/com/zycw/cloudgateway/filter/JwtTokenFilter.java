package com.zycw.cloudgateway.filter;

import com.zycw.common.context.BaseContextHandler;
import com.zycw.common.exception.auth.UserTokenException;
import com.zycw.common.handler.TokenValidateHandler;
import com.zycw.common.jwt.IJWTInfo;
import com.zycw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>权限校验的过滤器</p>
 *
 * @author: JQ.Feng
 * @create: 2019-12-19
 */
public class JwtTokenFilter implements GatewayFilter {
    @Autowired
    private TokenValidateHandler tokenValidateHandler;

    @Value("${inspiry.config.ignoreUrls}")
    private String ignoreUrls;
    private PathMatcher pathMatcher = new AntPathMatcher();

    private boolean validateRequestPathUrl(String path) {
        if (StringUtil.isEmpty(ignoreUrls)) return true;
        String[] urls = ignoreUrls.split(",");
        for (int i = 0; i < urls.length; i++) {
            if (pathMatcher.match(urls[i], path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (validateRequestPathUrl(path)) return chain.filter(exchange);
        String authToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtil.isEmpty(authToken)) {
            authToken = exchange.getRequest().getQueryParams().getFirst("token");
            if (StringUtil.isEmpty(authToken)) {
                throw new UserTokenException("token is null");
            }
        }
        try {
            authToken = authToken.substring("Bearer ".length());
            IJWTInfo info = tokenValidateHandler.doValidate(authToken);
            BaseContextHandler.setUserID(info.getId());
            BaseContextHandler.setUsername(info.getName());
            BaseContextHandler.setToken(authToken);
        } catch (Exception e) {
            throw new UserTokenException("token validate fail");
        }
        return chain.filter(exchange);
    }
}

