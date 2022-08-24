package com.lframework.xingyun.basedata.api.bo.storecenter;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StoreCenterSelectorBo extends BaseBo<StoreCenter> {

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

  public StoreCenterSelectorBo() {

  }

  public StoreCenterSelectorBo(StoreCenter dto) {

    super(dto);
  }
}
