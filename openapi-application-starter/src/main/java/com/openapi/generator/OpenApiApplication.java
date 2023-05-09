package com.openapi.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.log.LogAccessor;

/**
 * @author Fqq
 * <p> 2022/12/2
 * openApi 生成器和桥接器，生成器根据需要生成多份 SDK，桥接器只有一个，与系统（starter）隔离，但被 starter 扫描，
 * 生成器所有的配置只在生成器中，桥接器和 starter 模块也是如此
 * 变更模块组名时需要改以下配置:
 * <p>- 项目包名
 * <p>- 生成代码的包名
 * <p>- 配置类 feign 客户端扫描位置: @EnableFeignClients({"com.openapi.generator.robotms.server.api.**.client"})
 * <p>- 代理类 aop 切面位置: @Pointcut("execution(public * com.openapi.generator.robotms.server.api..*.server.*Api.*(..))")
 */
@SpringBootApplication
public class OpenApiApplication {
    private static final LogAccessor log = new LogAccessor(OpenApiApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OpenApiApplication.class, args);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (beanDefinitionName.endsWith("ApiClient")) {
                log.info("------ 找到的 client -----: " + beanDefinitionName);
            }
            if (beanDefinitionName.endsWith("ApiController")) {
                log.info("---- 找到的 server ----: " + beanDefinitionName);
            }
            if (beanDefinitionName.endsWith("Interceptor")) {
                log.info("----- 拦截器 --------: " + beanDefinitionName);
            }
            if (beanDefinitionName.endsWith("ClientProxy")) {
                log.info("------- 代理 --------: " + beanDefinitionName);
            }
        }
        log.info(">>>>>>>>>>>>>> 生成 openapi 代码需要在配置模块（openapi-generator-generator）中运行编译指令: mvn compile >>>>>>>>>>>>>>>>");
    }
}
