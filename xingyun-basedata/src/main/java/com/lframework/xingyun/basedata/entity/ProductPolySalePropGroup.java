package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_data_product_poly_sale_prop_group")
public class ProductPolySalePropGroup extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 商品聚合ID
   */
  private String polyId;

  /**
   * 销售属性组ID
   */
  private String salePropGroupId;

  /**
   * 排序
   */
  private Integer orderNo;

}
