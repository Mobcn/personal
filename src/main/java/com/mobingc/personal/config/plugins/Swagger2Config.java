package com.mobingc.personal.config.plugins;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)  // DocumentationType.SWAGGER_2 固定的，代表swagger2
                // .groupName("分布式任务系统")    // 如果配置多个文档的时候，那么需要配置groupName来分组标识
                .apiInfo(apiInfo()) // 用于生成API信息
                .select()   // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))   // 用于指定扫描哪个包下的接口
                .paths(PathSelectors.any()) // 选择所有的API,如果你想只为部分API生成文档，可以配置这里
                .build()
                .globalOperationParameters(this.getParameterList());
    }

    /**
     * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("个人空间API")   // 可以用来自定义API的主标题
                .description("个人空间SwaggerAPI管理")    // 可以用来描述整体的API
                .termsOfServiceUrl("http://127.0.0.1:5000")  // 用于定义服务的域名
                .version("1.0") // 可以用来定义版本。
                .build();
    }

    /**
     * 添加header参数配置
     */
    private List<Parameter> getParameterList() {
        List<Parameter> parameters = new ArrayList<>();
        Parameter Authorization = new ParameterBuilder()
                .name("Authorization")
                .description("登录认证")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer ")
                .required(false)    // 设置false，表示clientId参数 非必填,可传可不传！
                .build();
        parameters.add(Authorization);
        return parameters;
    }

}
