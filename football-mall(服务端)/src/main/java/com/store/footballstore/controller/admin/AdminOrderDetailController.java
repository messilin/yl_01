package com.store.footballstore.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.store.footballstore.entity.OrderDetail;
import com.store.footballstore.entity.R;
import com.store.footballstore.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-商品详情Controller
 * @author yuanlin
 * @create 2023/9/8 0008 17:14
 */
@RestController
@RequestMapping("/admin/orderDetail")
public class AdminOrderDetailController
{
    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 根据订单号查询商品订单详情
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    public R listByOrderId(@PathVariable(value = "id")Integer id){
        System.out.println("id="+id);
        List<OrderDetail> orderDetailList=orderDetailService.list(new QueryWrapper<OrderDetail>().eq("mId",id));
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("list",orderDetailList);
        return R.ok(resultMap);
    }
}
