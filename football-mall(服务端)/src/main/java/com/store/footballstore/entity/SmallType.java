package com.store.footballstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuanlin
 * @create 2023/8/18 0018 18:52
 */
@TableName("table_smalltype")
@Data
public class SmallType implements Serializable {
    private Integer id; // 编号

    private String name; // 名称

    private String remark; // 描述

    private Integer bigTypeId; // 大类id

//    添加TableField是为了不让属性参与数据库的操作
    @TableField(select = false)
    private BigType bigType;  // 所属商品大类

    @TableField(select = false)
    private List<Product> productList; // 商品集合

    public BigType getBigType(){
        return bigType;
    }

    public void setBigType(BigType bigType){
        this.bigType=bigType;
    }
}
