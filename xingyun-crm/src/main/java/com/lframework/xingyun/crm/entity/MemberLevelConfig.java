package com.lframework.xingyun.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.crm.enums.DownGradeCycle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员等级规则
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_member_level_config")
public class MemberLevelConfig extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "MemberLevelConfig";

  /**
   * ID
   */
  private String id;

  /**
   * 每消费1元获得的经验值
   */
  private Integer exp;

  /**
   * 是否自动降级
   */
  private Boolean isDownGrade;

  /**
   * 降级周期
   */
  private DownGradeCycle downGradeCycle;

  /**
   * 每次降级的经验值
   */
  private Integer downGradeExp;
}
