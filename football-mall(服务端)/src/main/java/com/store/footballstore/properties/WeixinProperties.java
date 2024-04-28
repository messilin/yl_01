package com.store.footballstore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置文件
 * @author yuanlin
 * @create 2023/8/26 0026 15:07
 */
@Component
//读取配置文件的注解
@ConfigurationProperties(prefix = "weixin")
@Data
public class WeixinProperties {
//    登录凭证校验请求地址
    private String jscode2sessionUrl;

//    小程序appId
    private String appid;

//    小程序appSecret
    private String secret;
}
