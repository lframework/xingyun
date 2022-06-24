package com.lframework.xingyun.crm.vo.member.level.config;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.crm.enums.DownGradeCycle;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMemberLevelConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 每消费1元获得的经验值
   */
  @ApiModelProperty("每消费1元获得的经验值")
  @NotNull(message = "每消费1元获得的经验值不能为空！")
  @TypeMismatch(message = "每消费1元获得的经验值必须为整数！")
  @Min(value = 0, message = "每消费1元获得的经验值不允许小于0！")
  private Integer exp;

  /**
   * 是否自动降级
   */
  @ApiModelProperty("是否自动降级")
  @NotNull(message = "是否自动降级不能为空！")
  private Boolean isDownGrade;

  /**
   * 降级周期
   */
  @ApiModelProperty("降级周期")
  @TypeMismatch(message = "降级周期格式错误！")
  @IsEnum(enumClass = DownGradeCycle.class, message = "降级周期格式错误！")
  private Integer downGradeCycle;

  /**
   * 每次降级的经验值
   */
  @ApiModelProperty("每次降级的经验值")
  @TypeMismatch(message = "每次降级的经验值格式错误！")
  @Min(value = 0, message = "每次降级的经验值不允许小于0！")
  private Integer downGradeExp;
}
