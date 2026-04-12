package com.lframework.xingyun.sc.vo.address;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.AddressType;
import com.lframework.xingyun.sc.enums.OrderAddressOrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderAddressVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  private String orderId;

  /**
   * 地址类型
   */
  @Schema(description = "订单类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "订单类型不能为空！")
  @IsEnum(message = "订单类型格式错误！", enumClass = OrderAddressOrderType.class)
  private Integer orderType;

  /**
   * 地址类型
   */
  @Schema(description = "地址类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "地址类型不能为空！")
  @IsEnum(message = "地址类型格式错误！", enumClass = AddressType.class)
  private Integer addressType;

  /**
   * 姓名
   */
  @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "姓名不能为空！")
  private String name;

  /**
   * 手机号
   */
  @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "手机号不能为空！")
  private String telephone;

  /**
   * 省ID
   */
  @Schema(description = "省ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "省ID不能为空！")
  private String provinceId;

  /**
   * 市ID
   */
  @Schema(description = "市ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "市ID不能为空！")
  private String cityId;

  /**
   * 区ID
   */
  @Schema(description = "区ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "区ID不能为空！")
  private String districtId;

  /**
   * 详细地址
   */
  @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "详细地址不能为空！")
  private String address;
}
