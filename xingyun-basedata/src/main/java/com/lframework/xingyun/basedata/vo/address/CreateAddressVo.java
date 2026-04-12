package com.lframework.xingyun.basedata.vo.address;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAddressVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 实体ID
   */
  @Schema(description = "实体ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "实体ID不能为空！")
  private String entityId;

  /**
   * 实体类型
   */
  @Schema(description = "实体类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "实体类型不能为空！")
  @IsEnum(message = "实体类型格式错误！", enumClass = AddressEntityType.class)
  private Integer entityType;

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
   * 地区ID
   */
  @Schema(description = "地区ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "地区ID不能为空！")
  private String cityId;

  /**
   * 详细地址
   */
  @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "详细地址不能为空！")
  private String address;

  /**
   * 是否默认地址
   */
  @Schema(description = "是否默认地址", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "是否默认地址不能为空！")
  private Boolean isDefault;
}
