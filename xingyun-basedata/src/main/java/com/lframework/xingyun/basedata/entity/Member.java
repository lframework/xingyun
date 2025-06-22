package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.inner.enums.system.Gender;
import com.lframework.starter.web.core.dto.BaseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
@Data
@TableName("base_data_member")
public class Member extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "Member";
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
   * 所属门店
   */
  private String shopId;

  /**
   * 所属导购
   */
  private String guiderId;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;


}
