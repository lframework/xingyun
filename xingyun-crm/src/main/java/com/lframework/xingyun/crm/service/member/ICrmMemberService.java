package com.lframework.xingyun.crm.service.member;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.vo.member.QueryCrmMemberVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * Crm会员 Service
 *
 * @author zmj
 */
public interface ICrmMemberService extends BaseMpService<CrmMember> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<CrmMember> query(Integer pageIndex, Integer pageSize, QueryCrmMemberVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<CrmMember> query(QueryCrmMemberVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  CrmMember findById(String id);

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
