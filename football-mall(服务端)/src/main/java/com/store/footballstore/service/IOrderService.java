package com.store.footballstore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.store.footballstore.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单service接口
 * @author yuanlin
 * @create 2023/8/26 0026 19:54
 */
public interface IOrderService extends IService<Order> {
    /**
     * 根据条件查询订单总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 根据条件查询订单数据
     * @param map
     * @return
     */
    List<Order> list(Map<String,Object> map);
}
