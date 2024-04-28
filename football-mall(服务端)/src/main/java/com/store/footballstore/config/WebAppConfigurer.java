package com.store.footballstore.config;

import com.store.footballstore.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yuanlin
 * @create 2023/8/16 15:23
 */

/**
 * Config类用于在浏览器页面中显示图片
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
//    跨域问题的解决
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .maxAge(3600);
    }

    @Override
//    资源处理
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        通过addResourceLocations映射图片资源位置,在浏览器中可以通过访问路径显示图片
//        轮播图
        registry.addResourceHandler("/image/swiper/**").addResourceLocations("file:D:\\pictures-source\\productImgs\\");
//        热卖推荐
        registry.addResourceHandler("/image/hotProduct/**").addResourceLocations("file:D:\\pictures-source\\productImgs\\");
//        分类页面
        registry.addResourceHandler("/image/product/**").addResourceLocations("file:D:\\pictures-source\\productImgs\\");
//        商品轮播图片
        registry.addResourceHandler("/image/productSwiperImage/**").addResourceLocations("file:D:\\pictures-source\\productSwiperImgs\\");
    }

    @Bean
    public SysInterceptor sysInterceptor(){
        return  new SysInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns=new String[]{
                "/adminLogin","/product/**","/bigType/**","/user/wxlogin","/weixinpay/**"
        };
//        excludePathPatterns表示排除patterns
        registry.addInterceptor(sysInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
