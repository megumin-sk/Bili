package com.bilibili.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.utils.JwtUtil;
import com.bilibili.utils.ResponseEnum;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 放行所有OPTIONS请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        response.setContentType("application/json;charset=utf-8");
        String token = request.getHeader("token");
        if (token == null){
            log.error("token为空");
            response.getWriter().write(JSONObject.toJSONString(ResponseEnum.TOKEN_IS_NULL));
            return false;
        }
        try {
            boolean flag = JwtUtil.verifyJwt(token);
            if (!flag){
                log.error("token异常");
                response.getWriter().write(JSONObject.toJSONString(ResponseEnum.TOKEN_IS_ERROR));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
