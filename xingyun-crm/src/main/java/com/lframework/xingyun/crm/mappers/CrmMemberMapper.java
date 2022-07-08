package com.lframework.xingyun.crm.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.vo.member.QueryCrmMemberVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrmMemberMapper extends BaseMapper<CrmMember> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<CrmMember> query(@Param("vo") QueryCrmMemberVo vo);

  /**
   * 增加经验值
   *
   * @param memberId
   * @param exp
   */
  void addExp(@Param("memberId") String memberId, @Param("exp") Integer exp);

  /**
   * 减少经验值
   *
   * @param memberId
   * @param exp
   */
  void subExp(@Param("memberId") String memberId, @Param("exp") Integer exp);
}
