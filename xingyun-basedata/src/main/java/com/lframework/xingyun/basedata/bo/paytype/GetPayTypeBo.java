package com.lframework.xingyun.basedata.bo.paytype;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPayTypeBo extends BaseBo<PayType> {

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
   * 是否记录内容
   */
  @ApiModelProperty("是否记录内容")
  private Boolean recText;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetPayTypeBo() {

  }

  public GetPayTypeBo(PayType dto) {

    super(dto);
  }
}
