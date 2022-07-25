package com.woniu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket api() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("共享车位项目接口文挡") //  可以用来自定义API的主标题
                .description("Project Swagger3 UserService Interface") // 可以用来描述整体的API
                .termsOfServiceUrl("http://localhost:8080/swagger-ui/index.html") // 用于定义服务的域名
                .version("1.0") // 可以用来定义版本。
                .build();

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .enable(true)   // 使用使用 swagger 开关。默认 true ，可省略。
                .select()
//      .apis(RequestHandlerSelectors.any())    // 扫描所有所有路径
//      .apis(RequestHandlerSelectors.none())   // 所有路径都不扫描
                .apis(RequestHandlerSelectors.basePackage("com.woniu.web.controller")) // 扫描指定包路径
                .paths(PathSelectors.any())     // 过滤器：对外暴露所有 uri
//      .paths(PathSelectors.none())    // 过滤器：一个 uri 都不对外暴露
//      .paths(PathSelectors.ant())     // 过滤器：对外暴露符合 ant 风格正则表达式的 uri
//      .paths(PathSelectors.regex()    // 过滤器：对外暴露符合正则表达式的 uri
                .build();
    }

}
