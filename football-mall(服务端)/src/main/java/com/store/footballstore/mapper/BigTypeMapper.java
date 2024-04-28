package com.store.footballstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.footballstore.entity.BigType;

/**
 * 商品大类Mapper接口
 * @author yuanlin
 * @create 2023/8/17 0017 20:50
 */
public interface BigTypeMapper extends BaseMapper<BigType> {

   public BigType findById(Integer id);
}
