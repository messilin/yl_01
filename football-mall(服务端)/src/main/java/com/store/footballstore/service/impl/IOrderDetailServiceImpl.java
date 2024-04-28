package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.OrderDetail;
import com.store.footballstore.mapper.OrderDetailMapper;
import com.store.footballstore.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanlin
 * @create 2023/8/26 0026 20:44
 */
@Service("orderDetailService")
public class IOrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
}
