package com.unncbandsclub.utopia.config;

import com.unncbandsclub.utopia.utlis.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    /**
     * WebMvcConfigurer配置类其实是 Spring 内部的一种配置方式，采用 javaBean 的形式来代替传统的xml
     * 配置文件的形式进行针对框架的个性化定制，可以自定义实现一些 Handler、Interceptor、ViewResolver、MessageConverter
     */

    /**
     * 解决跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

    /**
     * 异步请求配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    /**
     * 配置拦截器、拦截路径
     * 每次请求到拦截的路径，就回去执行拦截器中的方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        // 排除拦截，除了注册登录（此时还没TOKEN）,其他都拦截
        excludePath.add("/static/**"); // 把静态资源的访问也排除
        excludePath.add("/assets/**"); // 把静态资源的访问也排除
        excludePath.add("/swagger-ui.html");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/webjars/**") ;
        excludePath.add("/swagger-ui.html/**") ;
        excludePath.add( "/v2/**");
        excludePath.add("/doc.html/**") ;

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 添加拦截的路径模式 /** 代表全部拦截
                .excludePathPatterns(excludePath);

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
