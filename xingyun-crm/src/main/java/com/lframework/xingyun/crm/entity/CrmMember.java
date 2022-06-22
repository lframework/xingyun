package com.lframework.xingyun.crm.entity;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CrmMember {

  /**
   * 会员ID
   */
  private String id;

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
