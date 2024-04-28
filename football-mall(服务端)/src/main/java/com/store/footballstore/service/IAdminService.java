package com.store.footballstore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.store.footballstore.entity.Admin;

/**
 * 管理员service接口
 * @author yuanlin
 * @create 2023/9/4 0004 15:50
 */
public interface IAdminService extends IService<Admin> {
    public Integer update(Admin admin);
}
