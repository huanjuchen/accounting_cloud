package xyz.huanju.accounting.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.huanju.accounting.domain.UserRole;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.response.ResultCode;
import xyz.huanju.accounting.domain.token.Token;
import xyz.huanju.accounting.service.TokenService;
import xyz.huanju.accounting.utils.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 登录验证过滤器
 *
 * @author HuanJu
 * @date 2020/8/12 17:10
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    private final String LOGIN_PATH = "/login";

    private final String MANAGE_PATH_PREFIX = "/manage";

    private final String ADMIN_PATH_PREFIX = "/admin";

    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取 path
        String path = request.getPath().pathWithinApplication().value();
        /*
        排除用户登录
         */
        if (path.contains(LOGIN_PATH) && path.indexOf(LOGIN_PATH) == 0) {
            return chain.filter(exchange);
        }

        //获取tokenId
        HttpHeaders headers = request.getHeaders();
        List<String> tokenIds = headers.get("token_id");
        ServerHttpResponse response = exchange.getResponse();
        String tokenId = null;
        Token token = null;
        /*
        验证 是否登录
         */
        if (tokenIds == null || tokenIds.size() == 0) {
            return getMono(response, ResultCode.UNAUTHORIZED, "未登录");
        } else {
            tokenId = tokenIds.get(0);
            token = tokenService.getToken(tokenId);
            if (token == null) {
                return getMono(response, ResultCode.UNAUTHORIZED, "未登录");
            }

        }
        /*
        管理员权限验证
         */
        if (path.contains(MANAGE_PATH_PREFIX) && path.indexOf(MANAGE_PATH_PREFIX) == 0) {
            Integer role = token.getRole();
            if (UserRole.MANAGER.equals(role)||UserRole.ADMIN.equals(role)) {
                return chain.filter(exchange);
            } else {
                return getMono(response, ResultCode.FORBIDDEN, "权限不足");
            }
        }
        /*
        超级管理员权限处理
         */
        if (path.contains(ADMIN_PATH_PREFIX) && path.indexOf(ADMIN_PATH_PREFIX) == 0) {
            Integer role = token.getRole();
            if (UserRole.ADMIN.equals(role)) {
                return chain.filter(exchange);
            } else {
                return getMono(response, ResultCode.FORBIDDEN, "权限不足");
            }
        }
        return chain.filter(exchange);
    }


    private Mono<Void> getMono(ServerHttpResponse response, Integer code, String message) {
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        CommonResult<Object> commonResult = new CommonResult<>(code, message, null);
        String resultJson = JsonUtils.toJson(commonResult);
        if (resultJson != null) {
            DataBuffer dataBuffer = response.bufferFactory().wrap(resultJson.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Flux.just(dataBuffer));
        } else {
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
