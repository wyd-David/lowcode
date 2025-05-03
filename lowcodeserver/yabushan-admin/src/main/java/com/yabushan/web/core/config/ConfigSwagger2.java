package com.yabushan.web.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 *
 * @author ly
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
@Import(BeanValidatorPluginsConfiguration.class)
public class ConfigSwagger2 {
    /*@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan"))
                .paths(PathSelectors.any())
                .build()
                .groupName("1、所有API接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }*/

    @Bean
    public Docket commonsApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.common"))
                .paths(PathSelectors.ant("/minio/**"))
                .build()
                .groupName("1、minio文件服务接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }
    @Bean
    public Docket commonsApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.common"))
                .paths(PathSelectors.ant("/S3/**"))
                .build()
                .groupName("2、S3文件服务接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket commonsApi3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.common"))
                .paths(PathSelectors.ant("/localFile/**"))
                .build()
                .groupName("3、本地文件服务接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket commonsApi4() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.datasource"))
                .paths(PathSelectors.ant("/kafka/**"))
                .build()
                .groupName("4、kafka服务接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket commonsApi5() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.common"))
                .paths(PathSelectors.ant("/lunwen/**"))
                .build()
                .groupName("5、文章比对服务接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }



    @Bean
    public Docket commonsApi6() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.datasource"))
                .paths(PathSelectors.ant("/datasource/**"))
                .build()
                .groupName("5、数据源管理")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }


    @Bean
    public Docket captchaApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller"))
                .paths(PathSelectors.ant("/captchaImage/**"))
                .build()
                .groupName("10、验证码接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

   

    @Bean
    public Docket activitiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("2.X")
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.yabushan.web.controller.activiti"))
                .paths(PathSelectors.ant("/activiti/**"))
                .build()
                .groupName("8、工作流接口")
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }



    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("基础框架接口API")
                //作者
                .contact(new Contact("ly", "/v2/api-docs", ""))
                //版本号
                .version("2.0")
                //描述
                .description("基础框架接口API")
                .build();
    }
}

