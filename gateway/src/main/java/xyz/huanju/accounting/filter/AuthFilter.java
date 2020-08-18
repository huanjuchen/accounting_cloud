package xyz.huanju.accounting.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
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

    /**
     * 排除的路径
     */
    private List<String> excludedPaths=new ArrayList<>();

    /**
     * 超级管理路径
     */
    private List<String> adminPaths=new ArrayList<>();

    /**
     * 管理路径
     */
    private List<String> managePaths=new ArrayList<>();


    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Value("#{'${auth.filter.exclude}'.split(',')}")
    public void setExcludedPaths(List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    @Value("#{'${auth.filter.admin}'.split(',')}")
    public void setAdminPaths(List<String> adminPaths) {
        this.adminPaths = adminPaths;
    }

    @Value("#{'${auth.filter.manage}'.split(',')}")
    public void setManagePaths(List<String> managePaths) {
        this.managePaths = managePaths;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取 path
        String path = request.getPath().pathWithinApplication().value();
        if (path.length() == 0) {
            return chain.filter(exchange);
        }
        /*
        路径排除
         */
        if (havePrefixOfList(path,excludedPaths)) {
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
        if (havePrefixOfList(path,managePaths)) {
            Integer role = token.getRole();
            if (UserRole.MANAGER.equals(role) || UserRole.ADMIN.equals(role)) {
                return chain.filter(exchange);
            } else {
                return getMono(response, ResultCode.FORBIDDEN, "权限不足");
            }
        }
        /*
        超级管理员权限处理
         */
        if (havePrefixOfList(path,adminPaths)) {
            Integer role = token.getRole();
            if (UserRole.ADMIN.equals(role)) {
                return chain.filter(exchange);
            } else {
                return getMono(response, ResultCode.FORBIDDEN, "权限不足");
            }
        }
        return chain.filter(exchange);
    }


    /**
     * 检查path 是否有 list中的前缀
     *
     * @param path path
     * @param prefixList list
     * @return boolean
     */
    private boolean havePrefixOfList(String path,List<String> prefixList) {
        for (String prefix : prefixList) {
            if (path.contains(prefix) && path.indexOf(prefix) == 0) {
                return true;
            }
        }
        return false;
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
