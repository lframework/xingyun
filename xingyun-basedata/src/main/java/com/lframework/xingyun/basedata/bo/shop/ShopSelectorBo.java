package com.lframework.xingyun.basedata.bo.shop;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.Shop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 门店选择器 Bo
 * </p>
 *
 * @author zmj
 */
@Data
public class ShopSelectorBo extends BaseBo<Shop> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public ShopSelectorBo() {

  }

  public ShopSelectorBo(Shop dto) {

    super(dto);
  }
}
