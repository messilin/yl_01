package com.store.footballstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.footballstore.entity.User;

/**
 * 微信用户信息Mapper接口
 * @author yuanlin
 * @create 2023/8/25 0025 22:22
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据Openid查询用户信息
     * @param Openid
     * @return
     */
    User findByOpenId(String openid);
}
