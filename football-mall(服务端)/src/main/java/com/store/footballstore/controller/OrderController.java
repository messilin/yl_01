package com.store.footballstore.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.store.footballstore.entity.Order;
import com.store.footballstore.entity.OrderDetail;
import com.store.footballstore.entity.R;
import com.store.footballstore.service.IOrderDetailService;
import com.store.footballstore.service.IOrderService;
import com.store.footballstore.util.DateUtil;
import com.store.footballstore.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 * @author yuanlin
 * @create 2023/8/26 0026 20:00
 */
@RestController
@RequestMapping("/my/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 创建订单与支付订单
     * @param token
     * @return
     */
    @RequestMapping("/create")
    @Transactional
    public R create(@RequestBody Order order, @RequestHeader(value = "token")String token){
        //通过token获取openid
        System.out.println("token="+token);
        System.out.println("order="+order);

        //添加订单到数据库
//Claims是关于实体（通常是用户）和其他实体（例如角色或权限）的声明的集合
        Claims claims= JwtUtils.validateJWT(token).getClaims();
        if(claims!=null){
            System.out.println("openid="+claims.getId());
            order.setUserId(claims.getId());
            System.out.println("openid添加成功");
        }
//        订单号根据当前日期生成
        order.setOrderNo(DateUtil.getCurrentDateStr());
//        创建订单日期
        order.setCreateDate(new Date());
//        设置订单状态为待发货
//        order.setStatus(1);
//        创建支付日期
        order.setPayDate(new Date());
//        获取商品
        OrderDetail[] goods=order.getGoods();
//        订单信息插入数据库
        orderService.save(order);

        //添加订单详情到数据库
        for (int i=0;i<goods.length;i++){
            OrderDetail orderDetail=goods[i];
            orderDetail.setMId(order.getId());
            orderDetailService.save(orderDetail);
        }
        return R.ok();
    }

    /**
     * 订单查询 type值 全部订单 待发货 待收货 退款/退货
     * @param type
     * @return
     */
    @RequestMapping("/list")
    public R list(Integer type,Integer page,Integer pageSize){
        System.out.println("type="+type);
        List<Order> orderList=null;
        Map<String,Object> resultMap=new HashMap<String,Object>();
//        page为当前页，pageSize为页面记录数量
        Page<Order> pageOrder=new Page<>(page,pageSize);
        Page<Order> orderResult;
        if (type==0){
//            orderList=orderService.list(new QueryWrapper<Order>().orderByDesc("id"));
            orderResult=orderService.page(pageOrder,new QueryWrapper<Order>().orderByDesc("id"));
        }else {
//            orderList=orderService.list(new QueryWrapper<Order>().eq("status",type).orderByDesc("id"));
            orderResult=orderService.page(pageOrder,new QueryWrapper<Order>().eq("status",type).orderByDesc("id"));
        }
        System.out.println("总记录数："+orderResult.getTotal());
        System.out.println("总页数："+orderResult.getPages());
        System.out.println("当前页数据："+orderResult.getRecords());
        orderList=orderResult.getRecords();
        resultMap.put("total",orderResult.getTotal());
        resultMap.put("totalPage",orderResult.getPages());
        resultMap.put("orderList",orderList);
        return R.ok(resultMap);
    }
}
