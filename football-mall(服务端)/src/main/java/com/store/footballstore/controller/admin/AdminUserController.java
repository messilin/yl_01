package com.store.footballstore.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.store.footballstore.entity.PageBean;
import com.store.footballstore.entity.R;
import com.store.footballstore.entity.User;
import com.store.footballstore.service.IUserService;
import com.store.footballstore.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理-用户Controller
 * @author yuanlin
 * @create 2023/9/5 0005 16:28
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private IUserService userService;

    /**
     * 根据条件分页查询用户信息
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        System.out.println(pageBean);
//        获取查询语句 trim表示去除首尾空格
        String query=pageBean.getQuery().trim();
        Page<User> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<User> pageResult=userService.page(page,new QueryWrapper<User>().like(StringUtil.isNotEmpty(query),"nickName",query));
        Map<String,Object> map=new HashMap<>();
        map.put("userList",pageResult.getRecords());
        map.put("total",pageResult.getTotal());
        return R.ok(map);
    }
}
