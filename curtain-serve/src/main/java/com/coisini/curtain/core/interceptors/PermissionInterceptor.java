package com.coisini.curtain.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.coisini.curtain.core.annotation.ScopeLevel;
import com.coisini.curtain.core.common.LocalUser;
import com.coisini.curtain.exception.http.ForbiddenException;
import com.coisini.curtain.exception.http.UnAuthenticatedException;
import com.coisini.curtain.entity.User;
import com.coisini.curtain.service.UserService;
import com.coisini.curtain.util.JwtToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description 权限拦截器
 *  interceptor实现在SpringBoot中的两种方式：
 *      implements HandlerInterceptor
 *      extends HandlerInterceptorAdapter
 * @author coisini
 * @date Aug 19, 2021
 * @Version 1.0
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevelOptional = this.getScopeLevel(handler);
        if (!scopeLevelOptional.isPresent()) {
            // 权限不存在时允许访问公共API
            return true;
        }

        // 获取Token
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(bearerToken)) {
            throw new UnAuthenticatedException(10004);
        }

        // Bearer
        if (!bearerToken.startsWith("Bearer")) {
            throw new UnAuthenticatedException(10004);
        }

        String[] tokens = bearerToken.split(" ");
        if (tokens.length != 2) {
            throw new UnAuthenticatedException(10004);
        }

        String jwtToken = tokens[1];
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(jwtToken);
        Map<String, Claim> claimMap = optionalMap.orElseThrow(() -> new UnAuthenticatedException(10004));
        boolean permissionValid = this.hasPermission(scopeLevelOptional.get(), claimMap);

        if (permissionValid) {
            this.setToThreadLocal(claimMap);
        }

        return permissionValid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 设置用户信息
     * @param map
     */
    private void setToThreadLocal(Map<String, Claim> map) {
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();

        User user = userService.getUserById(uid);
        LocalUser.set(user, scope);
    }

    /**
     * 权限验证
     * @return
     */
    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> claimMap) {
        Integer level = scopeLevel.value();
        Integer scope = claimMap.get("scope").asInt();

        if (level > scope) {
            throw new ForbiddenException(10005);
        }

        return true;
    }

    /**
     * 获取权限
     * @return
     */
    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);

            if (Objects.isNull(scopeLevel)) {
                return Optional.empty();
            }

            return Optional.of(scopeLevel);
        }

        return Optional.empty();
    }

}
