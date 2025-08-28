package com.bilibili.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc  // 启用接口文档
public class Knife4jConfig {
    @Bean
    public Docket docketApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.bilibili.controller"))
                .build().apiInfo(apiInfo()).groupName("前台接口");
    }
    @Bean
    public Docket docketAdmin(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.bilibili.controller"))
                .build().apiInfo(apiInfo()).groupName("后台接口");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("java项目接口文档")
                .version("0.0.1")
                .contact(new Contact("wang","www.wang.com","446470238@qq.com"))
                .license("")
                .license("")
                .termsOfServiceUrl("www.woniu.com")
                .build();
    }

}