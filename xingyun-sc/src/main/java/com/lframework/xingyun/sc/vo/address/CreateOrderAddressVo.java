package com.lframework.xingyun.sc.vo.address;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.AddressType;
import com.lframework.xingyun.sc.enums.OrderAddressOrderType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderAddressVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @ApiModelProperty(value = "订单ID", required = true)
  private String orderId;

  /**
   * 地址类型
   */
  @ApiModelProperty(value = "订单类型", required = true)
  @NotNull(message = "订单类型不能为空！")
  @IsEnum(message = "订单类型格式错误！", enumClass = OrderAddressOrderType.class)
  private Integer orderType;

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
   * 省ID
   */
  @ApiModelProperty(value = "省ID", required = true)
  @NotBlank(message = "省ID不能为空！")
  private String provinceId;

  /**
   * 市ID
   */
  @ApiModelProperty(value = "市ID", required = true)
  @NotBlank(message = "市ID不能为空！")
  private String cityId;

  /**
   * 区ID
   */
  @ApiModelProperty(value = "区ID", required = true)
  @NotBlank(message = "区ID不能为空！")
  private String districtId;

  /**
   * 详细地址
   */
  @ApiModelProperty(value = "详细地址", required = true)
  @NotBlank(message = "详细地址不能为空！")
  private String address;
}
