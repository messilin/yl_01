package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.Admin;
import com.store.footballstore.mapper.AdminMapper;
import com.store.footballstore.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanlin
 * @create 2023/9/4 0004 15:51
 */
@Service("adminService")
public class IAdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Integer update(Admin admin){
        return adminMapper.update(admin);
    }
}
