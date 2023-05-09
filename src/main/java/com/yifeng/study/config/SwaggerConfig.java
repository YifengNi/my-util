package com.yifeng.study.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
// Knife4j访问路径：http://localhost:8888/doc.html
@EnableKnife4j
public class SwaggerConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public Docket createRestApi() {
        // String profiles=applicationContext.getEnvironment().getProperty("spring.profiles.active", String.class,"local");

        Docket docket= new Docket(DocumentationType.SWAGGER_2)
//                .host(domain)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yifeng.study"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(Arrays.asList(parameter()))
//                .enable(enableSwagger)
                ;
        // if(!profiles.equals("local")){
        //     String domian="logan-gateway."+profiles+".logan.xiaopeng.local";
        //     docket.host(domian);
        // }
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("工具项目[API 文档]")
                .description("")
                .termsOfServiceUrl("XX")
                .build();
    }

}
