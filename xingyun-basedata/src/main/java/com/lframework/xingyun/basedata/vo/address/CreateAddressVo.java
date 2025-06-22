package com.lframework.xingyun.basedata.vo.address;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAddressVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 实体ID
   */
  @ApiModelProperty(value = "实体ID", required = true)
  @NotBlank(message = "实体ID不能为空！")
  private String entityId;

  /**
   * 实体类型
   */
  @ApiModelProperty(value = "实体类型", required = true)
  @NotNull(message = "实体类型不能为空！")
  @IsEnum(message = "实体类型格式错误！", enumClass = AddressEntityType.class)
  private Integer entityType;

  /**
   * 地址类型
   */
  @ApiModelProperty(value = "地址类型", required = true)
  @NotNull(message = "地址类型不能为空！")
  @IsEnum(message = "地址类型格式错误！", enumClass = AddressType.class)
  private Integer addressType;

  /**
   * 姓名
   */
  @ApiModelProperty(value = "姓名", required = true)
  @NotBlank(message = "姓名不能为空！")
  private String name;

  /**
   * 手机号
   */
  @ApiModelProperty(value = "手机号", required = true)
  @NotBlank(message = "手机号不能为空！")
  private String telephone;

  /**
   * 地区ID
   */
  @ApiModelProperty(value = "地区ID", required = true)
  @NotBlank(message = "地区ID不能为空！")
  private String cityId;

  /**
   * 详细地址
   */
  @ApiModelProperty(value = "详细地址", required = true)
  @NotBlank(message = "详细地址不能为空！")
  private String address;

  /**
   * 是否默认地址
   */
  @ApiModelProperty(value = "是否默认地址", required = true)
  @NotNull(message = "是否默认地址不能为空！")
  private Boolean isDefault;
}
