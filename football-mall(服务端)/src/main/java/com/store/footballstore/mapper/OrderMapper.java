package com.store.footballstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.footballstore.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 * @author yuanlin
 * @create 2023/8/26 0026 19:52
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> list(Map<String, Object> map);

    Long getTotal(Map<String, Object> map);
}
