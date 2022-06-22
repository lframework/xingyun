package com.lframework.xingyun.crm.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.crm.entity.CrmMember;
import org.apache.ibatis.annotations.Param;

public interface CrmMemberMapper extends BaseMapper<CrmMember> {

  /**
   * 增加经验值
   * @param memberId
   * @param exp
   */
  void addExp(@Param("memberId") String memberId, @Param("exp") Integer exp);

  /**
   * 减少经验值
   * @param memberId
   * @param exp
   */
  void subExp(@Param("memberId") String memberId, @Param("exp") Integer exp);
}
