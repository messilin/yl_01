package com.store.footballstore.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.store.footballstore.entity.BigType;
import com.store.footballstore.entity.Product;
import com.store.footballstore.entity.R;
import com.store.footballstore.entity.SmallType;
import com.store.footballstore.service.IBigTypeService;
import com.store.footballstore.service.IProductService;
import com.store.footballstore.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品大类Controller
 * @author yuanlin
 * @create 2023/8/17 0017 21:02
 */
@RestController
@RequestMapping("/bigType")
public class BigTypeController {
    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    @Autowired
    private IProductService productService;

    /**
     * 查询所有商品大类，用于首页
     */
    @GetMapping("/findAll")
    public R findAll(){
        List<BigType> bigTypeList=bigTypeService.list();
        Map<String,Object> map=new HashMap<>();
        map.put("message",bigTypeList);
        return R.ok(map);
    }

    /**
     * 获取所有菜单信息，用于分类页面
     * @return
     */
    @GetMapping("/findCategories")
    public R findCategories(){
        //获取所有大类
        List<BigType> bigTypeList=bigTypeService.list();
        //通过大类获取商品小类
        for (BigType bigType:bigTypeList){
            //通过查询小类的typeId获取每个大类下的所有小类
            List<SmallType> smallTypeList=smallTypeService.list(new QueryWrapper<SmallType>().eq("bigTypeId",bigType.getId()));
            //把所有小类放入对应大类中
            bigType.setSmallTypeList(smallTypeList);
            for (SmallType smallType:smallTypeList){
                //获取每个小类中的所有商品
                List<Product> productList=productService.list(new QueryWrapper<Product>().eq("typeId",smallType.getId()));
                //把所有商品放入对应小类中
                smallType.setProductList(productList);
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("message",bigTypeList);
        return R.ok(map);
    }
}
