package com.store.footballstore.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.store.footballstore.constant.SystemConstant;
import com.store.footballstore.entity.Admin;
import com.store.footballstore.entity.R;
import com.store.footballstore.service.IAdminService;
import com.store.footballstore.util.JwtUtils;
import com.store.footballstore.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员Controller
 * @author yuanlin
 * @create 2023/9/4 0004 15:58
 */
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;
    /**
     * 管理员登录
     * @param admin
     * @return
     */
    @PostMapping("/adminLogin")
    public R adminLogin(@RequestBody Admin admin){
        if (admin==null){
            return R.error();
        }
        if (StringUtil.isEmpty(admin.getUserName())){
            return R.error("用户名不为空");
        }
        if (StringUtil.isEmpty(admin.getPassword())){
            return R.error("密码不能为空");
        }
        Admin resultAdmin=adminService.getOne(new QueryWrapper<Admin>().eq("userName",admin.getUserName()));
        if (resultAdmin==null){
            return R.error("用户名不存在");
        }
        if (!resultAdmin.getPassword().trim().equals(admin.getPassword())){
            return R.error("用户名或者密码错误!");
        }
        String token= JwtUtils.createJWT("-1","admin", SystemConstant.JWT_TTL);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("token",token);
        return R.ok(resultMap);
    }

    /**
     * 修改密码
     * @return admin
     * return
     */
    @PostMapping("/admin/modifyPassword")
    public R modifyPassword(@RequestBody Admin admin){
        if(StringUtil.isEmpty(admin.getUserName())){
            return R.error("用户名不能为空！");
        }
        if (StringUtil.isEmpty(admin.getNewPassword())){
            return R.error("新密码不能为空！");
        }
        adminService.update(admin);
        return R.ok();
    }
}
