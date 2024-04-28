package com.store.footballstore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.store.footballstore.entity.SmallType;

import java.util.List;
import java.util.Map;

/**
 * 商品小类service接口
 * @author yuanlin
 * @create 2023/8/18 0018 19:02
 */

public interface ISmallTypeService extends IService<SmallType> {
    /**
     * 根据条件查询商品小类总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String, Object> map);

    /**
     * 根据条件查询商品小类信息
     * @param map
     * @return
     */
    List<SmallType> list(Map<String,Object> map);

    public Integer add(SmallType smallType);

    public Integer update(SmallType smallType);
}
