package com.bilibili.config;

import com.bilibili.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Resource
    private JWTInterceptor jwtInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有路径
                .allowedOrigins("http://localhost:809") // 明确指定允许的前端来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowCredentials(true) // 允许发送 Cookie
                .allowedHeaders("*") // 允许所有请求头
                .maxAge(3600); // 预检请求的有效期，单位秒
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置doc.html页面
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 配置webjars资源
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/bangumi","/mall","/doc.html","/webjars/**","/swagger-resources/**","/v2/api-docs");
    }


}
