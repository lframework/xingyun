package com.lframework.xingyun.basedata.dto.member;

import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MemberDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "MemberDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 性别
   */
  private Gender gender;

  /**
   * 联系电话
   */
  private String telephone;

  /**
   * 电子邮箱
   */
  private String email;

  /**
   * 出生日期
   */
  private LocalDate birthday;

  /**
   * 入会日期
   */
  private LocalDate joinDay;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
