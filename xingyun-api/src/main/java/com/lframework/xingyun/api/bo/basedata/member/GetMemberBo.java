package com.lframework.xingyun.api.bo.basedata.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetMemberBo extends BaseBo<MemberDto> {

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
   * 性别
   */
  @ApiModelProperty("性别")
  private Integer gender;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 电子邮箱
   */
  @ApiModelProperty("电子邮箱")
  private String email;

  /**
   * 出生日期
   */
  @ApiModelProperty("出生日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate birthday;

  /**
   * 入会日期
   */
  @ApiModelProperty("入会日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate joinDay;

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

  public GetMemberBo() {

  }

  public GetMemberBo(MemberDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<MemberDto> convert(MemberDto dto) {

    return super.convert(dto, GetMemberBo::getGender);
  }

  @Override
  protected void afterInit(MemberDto dto) {

    this.gender = dto.getGender().getCode();
  }
}
