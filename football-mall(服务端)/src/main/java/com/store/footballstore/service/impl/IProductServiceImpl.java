package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.Product;
import com.store.footballstore.mapper.ProductMapper;
import com.store.footballstore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yuanlin
 * @create 2023/8/16 0016 17:30
 * 商品service实现类
 */
@Service("productService")
public class IProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findSwiper(){
        return productMapper.findSwiper();
    }

    @Override
    public List<Product> findHot(){
        return productMapper.findHot();
    }
    @Override
    public List<Product> list(Map<String,Object> map){
        return productMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String,Object> map){
        return productMapper.getTotal(map);
    }

    @Override
    public Integer add(Product product){
        return productMapper.add(product);
    }

    @Override
    public Integer update(Product product){
        return productMapper.update(product);
    }
}
