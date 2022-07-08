package com.lframework.xingyun.api.bo.crm.member.level.config;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetMemberLevelConfigBo extends BaseBo<MemberLevelConfig> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 每消费1元获得的经验值
   */
  @ApiModelProperty("每消费1元获得的经验值")
  private Integer exp;

  /**
   * 是否自动降级
   */
  @ApiModelProperty("是否自动降级")
  private Boolean isDownGrade;

  /**
   * 降级周期
   */
  @ApiModelProperty("降级周期")
  private Integer downGradeCycle;

  /**
   * 每次降级的经验值
   */
  @ApiModelProperty("每次降级的经验值")
  private Integer downGradeExp;

  public GetMemberLevelConfigBo() {
  }

  public GetMemberLevelConfigBo(MemberLevelConfig dto) {
    super(dto);
  }

  @Override
  protected void afterInit(MemberLevelConfig dto) {
    this.downGradeCycle = dto.getDownGradeCycle().getCode();
  }
}
