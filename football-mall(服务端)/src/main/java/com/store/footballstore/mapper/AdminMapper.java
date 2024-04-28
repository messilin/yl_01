package com.store.footballstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.footballstore.entity.Admin;

/**
 * 管理员Mapper接口
 * @author yuanlin
 * @create 2023/9/4 0004 15:49
 */
public interface AdminMapper extends BaseMapper<Admin> {
    public Integer update(Admin admin);
}
