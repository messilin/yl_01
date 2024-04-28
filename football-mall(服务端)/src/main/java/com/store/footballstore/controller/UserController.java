package com.store.footballstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.store.footballstore.constant.SystemConstant;
import com.store.footballstore.entity.R;
import com.store.footballstore.entity.User;
import com.store.footballstore.properties.WeixinProperties;
import com.store.footballstore.service.IUserService;
import com.store.footballstore.util.HttpClientUtil;
import com.store.footballstore.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信用户Controller
 * @author yuanlin
 * @create 2023/8/25 0025 22:14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private WeixinProperties weixinProperties;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private IUserService userService;
    /**
     * 微信用户登录
     * return
     */
    @RequestMapping("/wxlogin")
//    @RequestBody用于接收前端传给后端的json格式的数据
    public R wxLogin(@RequestBody User user){
        System.out.println("code="+user.getCode());
//        通过jscode2session 获取openId
        String jscode2sessionUrl=weixinProperties.getJscode2sessionUrl()+"?appid="+weixinProperties.getAppid()+"&secret="+weixinProperties.getSecret()+"&js_code="+user.getCode()+"&grant_type=authorization_code";
        System.out.println(jscode2sessionUrl);
        String result=httpClientUtil.sendHttpGet(jscode2sessionUrl);
        System.out.println(result);
        JSONObject jsonObject= JSON.parseObject(result);
        String openid=jsonObject.get("openid").toString();
        System.out.println("openid="+openid);
//      插入用户到数据库 判断用户是否存在 不存在的话 插入 存在的话 更新
//      从数据库中获取用户信息
        User resultuser=userService.getOne(new QueryWrapper<User>().eq("openid",openid));
        System.out.println(resultuser);
        if (resultuser==null){//不存在 插入用户信息
            System.out.println("不存在,插入用户信息");
            user.setOpenid(openid);
            user.setRegisterDate(new Date());
            user.setLastLoginDate(new Date());
            userService.save(user);
            System.out.println("信息保存成功");
        }else{//存在 更新用户信息
            System.out.println("存在,更新用户信息");
            resultuser.setNickName(user.getNickName());
            resultuser.setAvatarUrl(user.getAvatarUrl());
            resultuser.setLastLoginDate(new Date());
            userService.updateById(resultuser);
        }
//        利用jwt生成token返回到前端
        String token= JwtUtils.createJWT(openid,user.getNickName(), SystemConstant.JWT_TTL);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("token",token);
        return R.ok(resultMap);
    }
}
