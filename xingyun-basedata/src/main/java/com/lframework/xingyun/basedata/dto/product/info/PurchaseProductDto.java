package com.lframework.xingyun.basedata.dto.product.info;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class PurchaseProductDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 类目ID
   */
  private String categoryId;

  /**
   * 类目名称
   */
  private String categoryName;

  /**
   * 品牌ID
   */
  private String brandId;

  /**
   * 品牌名称
   */
  private String brandName;

  /**
   * 是否多销售属性
   */
  private Boolean multiSaleProp;

  /**
   * SKU
   */
  private String skuCode;

  /**
   * 外部编号
   */
  private String externalCode;

  /**
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 销售属性
   */
  private List<SalePropItemByProductDto> saleProps;
}
