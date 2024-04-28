package com.store.footballstore.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置类
 * @author yuanlin
 * @create 2023/8/18 0018 10:20
 */
@Configuration
public class MybatisPlueConfig {
    @Bean
//    分页拦截器，可以自动解析分页参数，包括页码和每页记录数，并转化为数据库对应的分页语法
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
