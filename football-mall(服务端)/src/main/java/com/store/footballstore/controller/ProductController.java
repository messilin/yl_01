package com.store.footballstore.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.store.footballstore.entity.Product;
import com.store.footballstore.entity.ProductSwiperImage;
import com.store.footballstore.entity.R;
import com.store.footballstore.service.IProductService;
import com.store.footballstore.service.IProductSwiperImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品Controller
 * @author yuanlin
 * @create 2023/8/16 0016 17:35
 */

/**
 *Controller用于在页面显示数据表的JSON形式
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSwiperImageService productSwiperImageService;
    /**
    * 查询轮播商品，根据isSwiper为true升序查询商品，用于首页
    * @return
    */
    @GetMapping("/findSwiper")
    public R findSwiper(){
        List<Product> swiperProductList=productService.list(new QueryWrapper<Product>().eq("isSwiper",true).orderByAsc("swiperSort"));
        Map<String,Object> map=new HashMap<>();
        map.put("message",swiperProductList);
        return R.ok(map);
    }

    /**
     * 查询热门推荐商品8个，用于首页
     * @return
     */
    @GetMapping("/findHot")
    public R findHot(){
        Page<Product> page=new Page<>(0,8);
        Page<Product> productPage=productService.page(page,new QueryWrapper<Product>().eq("isHot",true).orderByAsc("hotDateTime"));
        List<Product> hotProductList=productPage.getRecords();
        Map<String,Object> map=new HashMap<>();
        map.put("message",hotProductList);
        return R.ok(map);
    }

    /**
     * 根据id查询商品详情信息，用于商品详情页面显示
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public R detail(Integer id){
        Product product=productService.getById(id);
        List<ProductSwiperImage> productSwiperImageList = productSwiperImageService.list(new QueryWrapper<ProductSwiperImage>().eq("productId", product.getId()).orderByAsc("sort"));
        product.setProductSwiperImageList(productSwiperImageList);
        Map<String,Object> map=new HashMap<>();
        map.put("message",product);
        return R.ok(map);
    }

    /**
     *商品搜索
     * @param keywords
     * @return
     */
    @GetMapping("/search")
    public R search(String keywords){
        List<Product> productList=productService.list(new QueryWrapper<Product>().like("name",keywords));
        Map<String,Object> map=new HashMap<>();
        map.put("productList",productList);
        return R.ok(map);
    }
}
