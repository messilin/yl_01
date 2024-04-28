package com.store.footballstore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.store.footballstore.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * 商品service接口
 * @author yuanlin
 * @create 2023/8/16 0016 17:28
 */
public interface IProductService extends IService<Product> {
    public List<Product> list(Map<String,Object> map);

    public Long getTotal(Map<String, Object> map);

    public Integer add(Product product);

    public Integer update(Product product);

    public List<Product> findSwiper();

    public List<Product> findHot();
}
