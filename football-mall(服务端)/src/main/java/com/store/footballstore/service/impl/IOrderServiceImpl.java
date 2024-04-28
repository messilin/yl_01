package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.Order;
import com.store.footballstore.mapper.OrderMapper;
import com.store.footballstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yuanlin
 * @create 2023/8/26 0026 19:56
 */
@Service("orderService")
public class IOrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> list(Map<String,Object> map){
        return orderMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String,Object> map){
        return orderMapper.getTotal(map);
    }
}
