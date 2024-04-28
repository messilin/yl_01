package com.store.footballstore.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.store.footballstore.entity.BigType;
import com.store.footballstore.entity.PageBean;
import com.store.footballstore.entity.R;
import com.store.footballstore.entity.SmallType;
import com.store.footballstore.service.IBigTypeService;
import com.store.footballstore.service.ISmallTypeService;
import com.store.footballstore.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**管理员-商品大类Controller
 * @author yuanlin
 * @create 2023/9/9 0009 19:35
 */
@RestController
@RequestMapping("/admin/bigType")
//@CrossOrigin
//@ConfigurationProperties(prefix = "bigTypeImagesFilePath")
public class AdminBigTypeController {
    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    /**
     * 根据条件分页查询商品大类信息
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        System.out.println(pageBean);
        String query=pageBean.getQuery().trim();
        Page<BigType> page=new Page<>(pageBean.getPageNum(),pageBean.getPageSize());
        Page<BigType> pageResult=bigTypeService.page(page,new QueryWrapper<BigType>().like(StringUtil.isNotEmpty(query),"name",query));
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeList",pageResult.getRecords());
        map.put("total",pageResult.getTotal());
        return R.ok(map);
    }

    /**
     * 添加或者修改商品大类
     * @param bigType
     * @return
     */
    @PostMapping ("/save")
    public R save(@RequestBody BigType bigType){
//        添加商品大类
        if (bigType.getId()==null || bigType.getId()==-1){
            bigTypeService.save(bigType);
        }
//        修改商品大类
        else {
            bigTypeService.saveOrUpdate(bigType);
        }
        return R.ok();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable(value = "id")Integer id){
        BigType bigType=bigTypeService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("bigType",bigType);
        return R.ok(map);
    }

    /**
     * 删除商品大类
     * @param id
     * @return
     */
    @GetMapping("/deleteBigType/{id}")
    public R deleteBigType(@PathVariable(value = "id")Integer id){
//        判断大类下是否包括小类
        if (smallTypeService.count(new QueryWrapper<SmallType>().eq("bigTypeId",id))>0){
            return R.error(500,"商品大类下有小类，不能删除");
        }else {
            bigTypeService.removeById(id);
            return R.ok();
        }
    }

    /**
     * 查询所有数据 用于小类下拉框
     * @return
     */
    @RequestMapping("/all")
    public R all(){
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeList",bigTypeService.list());
        return R.ok(map);
    }
}
