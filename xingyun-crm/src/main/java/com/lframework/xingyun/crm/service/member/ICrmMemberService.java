package com.lframework.xingyun.crm.service.member;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.crm.entity.CrmMember;
import java.math.BigDecimal;

/**
 * Crm会员 Service
 *
 * @author zmj
 */
public interface ICrmMemberService extends BaseMpService<CrmMember> {

  /**
   * 提升等级
   *
   * @param memberId
   * @param amount
   */
  void addLevel(String memberId, BigDecimal amount);

  /**
   * 降级等级
   *
   * @param memberId
   * @param amount
   */
  void dropLevel(String memberId, BigDecimal amount);

  /**
   * 提升等级
   *
   * @param memberId
   * @param exp
   */
  void addLevel(String memberId, Integer exp);

  /**
   * 降级等级
   *
   * @param memberId
   * @param exp
   */
  void dropLevel(String memberId, Integer exp);
}
