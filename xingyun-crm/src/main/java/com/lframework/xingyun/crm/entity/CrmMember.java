package com.lframework.xingyun.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * Crm会员
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_member")
public class CrmMember extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * 会员ID
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
   * 会员等级
   */
  private String levelId;

  /**
   * 当前经验值
   */
  private Integer exp;

  /**
   * 末次降级日期
   */
  private LocalDate lastDropDate;
}
