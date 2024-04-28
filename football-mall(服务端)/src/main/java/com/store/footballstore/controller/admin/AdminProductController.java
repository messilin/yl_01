package com.store.footballstore.controller.admin;

import com.store.footballstore.entity.PageBean;
import com.store.footballstore.entity.Product;
import com.store.footballstore.entity.R;
import com.store.footballstore.service.IProductService;
import com.store.footballstore.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员-商品Controller
 * @author yuanlin
 * @create 2023/9/16 0016 16:20
 */
@RestController
@RequestMapping("/admin/product")
public class AdminProductController {

    @Autowired
    private IProductService productService;

//    商品图片
    @Value("${productImagesFilePath}")
    private String productImagesFilePath;

//    商品轮播图片
    @Value("${swiperImagesFilePath}")
    private String swiperImagesFilePath;

//    获取所有商品信息
    @RequestMapping("/list")
    public R list(@RequestBody PageBean pageBean){
        Map<String,Object> map=new HashMap<>();
        map.put("name",pageBean.getQuery().trim());
        map.put("start",pageBean.getStart());
        map.put("pageSize",pageBean.getPageSize());
        List<Product> productList=productService.list(map);
        Long total=productService.getTotal(map);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("productList",productList);
        resultMap.put("total",total);
        return R.ok(resultMap);
    }

    /**
     * 更新商品热卖状态
     * @param id
     * @param hot
     * @return
     */
    @GetMapping("/updateHot/{id}/state/{hot}")
    public R updateHot(@PathVariable(value = "id")Integer id,@PathVariable(value = "hot")boolean hot){
//        获取商品实体
        Product product=productService.getById(id);
        product.setHot(hot);
        if (hot){
            product.setHotDateTime(new Date());
        }else{
            product.setHotDateTime(null);
        }
        productService.saveOrUpdate(product);
        return R.ok();
    }

    /**
     * 更新商品swiper状态
     * @param id
     * @param swiper
     * @return
     */
    @GetMapping("/updateSwiper/{id}/state/{swiper}")
    public R updateSwiper(@PathVariable(value = "id")Integer id,@PathVariable(value = "swiper")boolean swiper){
        Product product=productService.getById(id);
        product.setSwiper(swiper);
        productService.saveOrUpdate(product);
        return R.ok();
    }

    /**
     * 更新商品图片
     * @param product
     * @return
     */
    @PostMapping("/saveImage")
    public R saveImage(@RequestBody Product product){
//        获取要修改图片的商品实体
        Product product1=productService.getById(product.getId());
//        更改实体的图片
        product1.setProPic(product.getProPic());
//        修改数据库中的图片
        productService.saveOrUpdate(product1);
        return R.ok();
    }

    /**
     * 更新首页轮播图片
     * @param product
     * @return
     */
    @PostMapping("/saveSwiper")
    public R saveSwiper(@RequestBody Product product){
        Product product1=productService.getById(product.getId());
        product1.setSwiperPic(product.getSwiperPic());
        product1.setSwiperSort(product.getSwiperSort());
        productService.saveOrUpdate(product1);
        return R.ok();
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id")Integer id){
        productService.removeById(id);
        return R.ok();
    }

    /**
     * 上传商品图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadProductImage")
    public Map<String,Object> uploadProductImage(MultipartFile file) throws Exception{
        Map<String,Object> resultMap=new HashMap<String,Object>();
        if (!file.isEmpty()){
//            获取文件名
            String originalFilename=file.getOriginalFilename();
//            获取文件名的后缀名,substring进行截取，从.开始，例如.jpg
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
//            拼接新文件名
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(productImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","图片上传成功");
            Map<String,Object> dataMap=new HashMap<String,Object>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/product/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

    /**
     * 上传商品首页轮播图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadSwiperImage")
    public Map<String,Object> uploadSwiperImage(MultipartFile file) throws Exception{
        Map<String,Object> resultMap=new HashMap<String,Object>();
        if (!file.isEmpty()){
//            获取文件名
            String originalFilename=file.getOriginalFilename();
            System.out.println("originalFilename="+originalFilename);
            //            获取文件名的后缀名,substring进行截取，从.开始，例如.jpg
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
            System.out.println("suffixName="+suffixName);
//            拼接新文件名
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            System.out.println("newFileName="+newFileName);
            System.out.println(swiperImagesFilePath+newFileName);
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(swiperImagesFilePath+newFileName));
            resultMap.put("code",0);
            resultMap.put("msg","图片上传成功");
            Map<String,Object> dataMap=new HashMap<String,Object>();
            dataMap.put("title",newFileName);
            dataMap.put("src","image/swiper/"+newFileName);
            resultMap.put("data",dataMap);
        }
        return resultMap;
    }

  /**
   * 添加或者修改商品
   *
   * @param product
   * @return
   */
  @PostMapping("/save")
  public R save(@RequestBody Product product) {
//      根据product的Id判断
    //添加商品
    if (product.getId() == null || product.getId() == -1) {
      productService.add(product);
    }
    //        修改商品
    else {
      productService.update(product);
    }
    return R.ok();
    }
}
