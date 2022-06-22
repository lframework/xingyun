package com.lframework.xingyun.crm.vo.member.level;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMemberLevelVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 编号
   */
  @IsCode
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 经验值
   */
  @ApiModelProperty(value = "经验值", required = true)
  @TypeMismatch(message = "经验值格式有误！")
  @Min(value = 0, message = "经验值不允许小于0！")
  @NotNull(message = "请输入经验值！")
  private Integer exp;

  /**
   * 是否默认等级
   */
  @ApiModelProperty(value = "是否默认等级", required = true)
  @TypeMismatch(message = "是否默认等级格式有误！")
  @NotNull(message = "请选择是否默认等级！")
  private Boolean isDefault;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @TypeMismatch(message = "状态格式有误！")
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}
