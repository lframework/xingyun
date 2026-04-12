package com.lframework.xingyun.basedata.bo.shop;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  public ShopSelectorBo() {

  }

  public ShopSelectorBo(Shop dto) {

    super(dto);
  }
}
