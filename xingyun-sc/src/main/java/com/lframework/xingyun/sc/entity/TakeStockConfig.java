package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 盘点参数
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_take_stock_config")
public class TakeStockConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 库存盘点单关联盘点任务后，是否显示盘点任务中的商品数据
     */
    private Boolean showProduct;

    /**
     * 库存盘点单是否显示盘点任务创建时商品的系统库存数量
     */
    private Boolean showStock;

    /**
     * 盘点差异生成时是否自动调整盘点任务中商品的系统库存数量
     */
    private Boolean autoChangeStock;

    /**
     * 盘点差异单中的盘点数量是否允许手动修改
     */
    private Boolean allowChangeNum;

    /**
     * 盘点任务创建后多少小时内未完成，则自动作废
     */
    private Integer cancelHours;

}
