package com.lframework.xingyun.basedata.vo.shop;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShopVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入编号！")
  @IsCode
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 所属部门ID
   */
  @Schema(description = "所属部门ID")
  private String deptId;

  /**
   * 经度
   */
  @Schema(description = "经度")
  @TypeMismatch(message = "经度格式有误！")
  private BigDecimal lng;

  /**
   * 纬度
   */
  @Schema(description = "纬度")
  @TypeMismatch(message = "纬度格式有误！")
  private BigDecimal lat;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

}
