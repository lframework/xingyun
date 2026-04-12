package com.lframework.xingyun.basedata.vo.address;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

@Data
public class AddressSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 实体ID
   */
  @Schema(description = "实体ID")
  private String entityId;

  /**
   * 实体类型
   */
  @Schema(description = "实体类型")
  @IsEnum(message = "实体类型格式错误！", enumClass = AddressEntityType.class)
  private Integer entityType;

  /**
   * 地址类型
   */
  @Schema(description = "地址类型")
  @IsEnum(message = "地址类型格式错误！", enumClass = AddressType.class)
  private Integer addressType;

  /**
   * 姓名
   */
  @Schema(description = "姓名")
  private String name;

  /**
   * 手机号
   */
  @Schema(description = "手机号")
  private String telephone;

  /**
   * 详细地址
   */
  @Schema(description = "详细地址")
  private String address;

  /**
   * 是否默认地址
   */
  @Schema(description = "是否默认地址")
  private Boolean isDefault;
}
