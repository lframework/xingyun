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
public class CreateMemberLevelVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
  @NotNull(message = "请输入经验值！")
  @Min(value = 0, message = "经验值不允许小于0！")
  @TypeMismatch(message = "经验值格式有误！")
  private Integer exp;

  /**
   * 是否默认等级
   */
  @ApiModelProperty(value = "是否默认等级", required = true)
  @NotNull(message = "请选择是否默认等级！")
  @TypeMismatch(message = "是否默认等级格式有误！")
  private Boolean isDefault;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}
