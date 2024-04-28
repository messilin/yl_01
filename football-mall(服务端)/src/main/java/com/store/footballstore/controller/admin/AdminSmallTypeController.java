package com.store.footballstore.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.store.footballstore.entity.PageBean;
import com.store.footballstore.entity.R;
import com.store.footballstore.entity.SmallType;
import com.store.footballstore.service.IBigTypeService;
import com.store.footballstore.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-商品小类Controller
 * @author yuanlin
 * @create 2023/9/16 0016 16:19
 */
@RestController
@RequestMapping("/admin/smallType")
public class AdminSmallTypeController {
    /**
     * 根据条件分页查询商品小类信息
     * @param pageBean
     * @return
     */
    @Autowired
    private ISmallTypeService smallTypeService;

    @Autowired
    private IBigTypeService bigTypeService;

//    获取所有商品小类信息
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        System.out.println("pageBean="+pageBean);
        Map<String,Object> map=new HashMap<>();
//        根据name查询
        map.put("name",pageBean.getQuery().trim());
        map.put("start",pageBean.getStart());
        map.put("pageSize",pageBean.getPageSize());
        List<SmallType> smallTypeList=smallTypeService.list(map);
        Long total=smallTypeService.getTotal(map);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("smallTypeList",smallTypeList);
        resultMap.put("total",total);
        return R.ok(resultMap);
    }

    /**
     * 删除商品小类
     * @param id
     * return
     */
    @GetMapping("delete/{id}")
    public R delete(@PathVariable(value = "id")Integer id){
        smallTypeService.removeById(id);
        return R.ok();
    }

    /**
     * 添加或者修改商品小类
     * @param smallType
     * @return
     */
    @RequestMapping("/save")
    public R save(@RequestBody SmallType smallType){
//        smallType.setBigTypeId(smallType.getBigType().getId());
//        添加商品大类
        if (smallType.getId()==null || smallType.getId()==-1){
            smallTypeService.add(smallType);
        }
//        修改商品大类
        else {
            smallTypeService.update(smallType);
        }
        return R.ok();
    }

    /**
     * 查询大类id
     * @param id
     * @return
     */
    @GetMapping("/getBigTypeIdBySmallTypeId/{id}")
    public R getBigTypeIdBySmallTypeId(@PathVariable(value = "id")Integer id){
        Integer bigTypeId=smallTypeService.getById(id).getBigTypeId();
        Map<String,Object> map=new HashMap<>();
        map.put("bigTypeId",bigTypeId);
        return R.ok(map);
    }

    /**
     * 根据商品大类id 查询所有商品大类数据 下拉框用到
     * @return
     */
    @RequestMapping("/listAll/{bigTypeId}")
    public R listAll(@PathVariable(value = "bigTypeId")Integer bigTypeId){
        Map<String,Object> map=new HashMap<>();
        map.put("smallTypeList",smallTypeService.list(new QueryWrapper<SmallType>().eq("bigTypeId",bigTypeId)));
        return R.ok(map);
    }
}
