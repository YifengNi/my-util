package com.yifeng.util.config;

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
                .apis(RequestHandlerSelectors.basePackage("com.yifeng.util"))
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
