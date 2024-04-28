package com.store.footballstore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.footballstore.entity.SmallType;
import com.store.footballstore.mapper.SmallTypeMapper;
import com.store.footballstore.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yuanlin
 * @create 2023/8/18 0018 19:09
 */
@Service("smallTypeService")
public class ISmallTypeServiceImpl extends ServiceImpl<SmallTypeMapper, SmallType> implements ISmallTypeService {
    @Autowired
    private SmallTypeMapper smallTypeMapper;

    @Override
    public List<SmallType> list(Map<String,Object> map){
        return smallTypeMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String,Object> map){
        return smallTypeMapper.getTotal(map);
    }

    @Override
    public Integer add(SmallType smallType){
        return smallTypeMapper.add(smallType);
    }

    @Override
    public Integer update(SmallType smallType){
        return smallTypeMapper.update(smallType);
    }
}
