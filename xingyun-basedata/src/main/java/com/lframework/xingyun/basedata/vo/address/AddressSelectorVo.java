package com.lframework.xingyun.basedata.vo.address;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class AddressSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 实体ID
   */
  @ApiModelProperty("实体ID")
  private String entityId;

  /**
   * 实体类型
   */
  @ApiModelProperty("实体类型")
  @IsEnum(message = "实体类型格式错误！", enumClass = AddressEntityType.class)
  private Integer entityType;

  /**
   * 地址类型
   */
  @ApiModelProperty("地址类型")
  @IsEnum(message = "地址类型格式错误！", enumClass = AddressType.class)
  private Integer addressType;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 手机号
   */
  @ApiModelProperty("手机号")
  private String telephone;

  /**
   * 详细地址
   */
  @ApiModelProperty("详细地址")
  private String address;

  /**
   * 是否默认地址
   */
  @ApiModelProperty("是否默认地址")
  private Boolean isDefault;
}
