package com.store.footballstore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品详情轮播图片
 * @author yuanlin
 * @create 2023/8/22 0022 15:26
 */
@TableName("table_product_swiper_image")
@Data
public class ProductSwiperImage {
    private Integer id; // 编号

    private String image; // 图片名称

    private Integer sort; // 排列序号 从小到大排序

    private Integer productId; // 所属产品
}
