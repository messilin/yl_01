package com.store.footballstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuanlin
 * @create 2023/8/25 0025 22:17
 */
@TableName("table_userinfo")
@Data
public class User implements Serializable {
    private Integer id; // 用户编号

    private String openid; // 用户唯一标识

    private String nickName; // 用户昵称

    private String avatarUrl; // 用户头像图片的 URL

//    序列化
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date registerDate; // 注册日期

//    序列化
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date lastLoginDate; // 最后登录日期

//    临时用，不存入数据库中，mybatis查询也不显示
    @TableField(select = false,exist = false)
    private String code; // 微信用户code 前端传来的
}
