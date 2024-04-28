package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.ProductSwiperImage;
import com.store.footballstore.mapper.ProductSwiperImageMapper;
import com.store.footballstore.service.IProductSwiperImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanlin
 * @create 2023/8/22 0022 15:45
 */
@Service("ProductSwiperImageService")
public class IProductSwiperImageImpl extends ServiceImpl<ProductSwiperImageMapper, ProductSwiperImage> implements IProductSwiperImageService {
    @Autowired
    private ProductSwiperImageMapper productSwiperImageMapper;
}
