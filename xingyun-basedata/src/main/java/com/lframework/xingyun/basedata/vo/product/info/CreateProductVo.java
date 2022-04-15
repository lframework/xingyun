package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * polyID
   */
  @ApiModelProperty(value = "polyID", required = true)
  @NotBlank(message = "PolyId不能为空！")
  private String polyId;

  /**
   * 商品SKU编号
   */
  @ApiModelProperty(value = "商品SKU编号", required = true)
  @NotBlank(message = "商品SKU编号不能为空！")
  private String skuCode;

  /**
   * 外部编号
   */
  @ApiModelProperty("外部编号")
  private String externalCode;

  /**
   * 规格
   */
  @ApiModelProperty("规格")
  private String spec;

  /**
   * 单位
   */
  @ApiModelProperty("单位")
  private String unit;

  /**
   * 采购价
   */
  @ApiModelProperty("采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ApiModelProperty("销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ApiModelProperty("零售价")
  private BigDecimal retailPrice;

  /**
   * 销售属性ID
   */
  @ApiModelProperty("销售属性ID")
  private List<String> salePropItems;
}
