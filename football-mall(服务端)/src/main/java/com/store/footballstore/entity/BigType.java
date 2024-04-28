package com.store.footballstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author yuanlin
 * @create 2023/8/17 0017 20:35
 */
@TableName("table_bigtype")
@Data
public class BigType {
        private Integer id; // 编号

        private String name; // 名称

        private String remark; // 备注

        @TableField(select = false)
        private List<SmallType> smallTypeList; //商品小类集合
}
