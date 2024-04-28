package com.store.footballstore.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@TableName("table_product")
@Data//可以省略get,set方法
public class Product {

    private Integer id; // 编号

    private String name; // 名称

    private BigDecimal price; // 价格

    private Integer stock; // 库存

    private String proPic="default.jpg"; // 商品图片

    private boolean isHot=false; // 是否热门推荐商品

    private boolean isSwiper=false; // 是否轮播图片商品

    private Integer swiperSort=0; // 轮播排序

    private String swiperPic="default.jpg"; // 商品轮播图片

    private String description; // 描述

//    日期对象可以被序列化，这使得在需要将数据存储到数据库中时，可以更轻松地直接将对象写入数据库。
    @JsonSerialize(using=CustomDateTimeSerializer.class)
//    mybatis plus默认的更新策略是非null,就是null值不更新进数据库，所以添加以下策略
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date hotDateTime; // 设置热门推荐日期时间

//   商品轮播图片集合
//   查询库表映射时不返回指定字段给前端
    @TableField(select = false)
    private List<ProductSwiperImage> productSwiperImageList;

    @TableField(select = false)
    private SmallType type;//商品类别
}