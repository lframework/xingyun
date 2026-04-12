package com.lframework.xingyun.basedata.vo.storecenter;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStoreCenterVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 联系人
   */
  @Schema(description = "联系人")
  private String contact;

  /**
   * 联系人手机号码
   */
  @Schema(description = "联系人手机号码")
  private String telephone;

  /**
   * 地区ID
   */
  @Schema(description = "地区ID")
  private String cityId;

  /**
   * 地址
   */
  @Schema(description = "地址")
  private String address;

  /**
   * 仓库人数
   */
  @Schema(description = "仓库人数")
  private Integer peopleNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
