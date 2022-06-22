package com.lframework.xingyun.api.bo.basedata.member.level;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.crm.entity.MemberLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员等级 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetMemberLevelBo extends BaseBo<MemberLevel> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 经验值
   */
  @ApiModelProperty("经验值")
  private Integer exp;

  /**
   * 是否默认等级
   */
  @ApiModelProperty("是否默认等级")
  private Boolean isDefault;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetMemberLevelBo() {

  }

  public GetMemberLevelBo(MemberLevel dto) {

    super(dto);
  }

}
