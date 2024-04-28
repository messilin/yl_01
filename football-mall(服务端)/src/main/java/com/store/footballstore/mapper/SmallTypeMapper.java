package com.store.footballstore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.footballstore.entity.SmallType;

import java.util.List;
import java.util.Map;

/**
 * 商品小类Mapper接口
 * @author yuanlin
 * @create 2023/8/18 0018 19:01
 */
public interface SmallTypeMapper extends BaseMapper<SmallType> {
    /**
     * 根据条件 分页查询商品小类
     * @param map
     * @return
     */
    public List<SmallType> list(Map<String,Object> map);

    /**
     * 根据条件 查询商品小类总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String,Object> map);

    /**
     *根据id查询商品小类
     * @param id
     * @return
     */
    public SmallType findById(Integer id);

    /**
     * 添加商品小类
     * @param smallType
     * @return
     */
    public Integer add(SmallType smallType);

    /**
     * 修改商品小类
     * @param smallType
     * @return
     */
    public Integer update(SmallType smallType);
}
