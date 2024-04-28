package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.BigType;
import com.store.footballstore.mapper.BigTypeMapper;
import com.store.footballstore.service.IBigTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanlin
 * @create 2023/8/17 0017 20:55
 * 商品大类service实现类
 */
@Service("bigTypeService")
public class IBigTypeServiceImpl extends ServiceImpl<BigTypeMapper, BigType> implements IBigTypeService {
    @Autowired
    private BigTypeMapper bigTypeMapper;
}
