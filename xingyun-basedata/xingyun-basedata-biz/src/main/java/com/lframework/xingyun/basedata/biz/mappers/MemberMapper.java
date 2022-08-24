package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface MemberMapper extends BaseMapper<Member> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Member> query(@Param("vo") QueryMemberVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Member> selector(@Param("vo") QueryMemberSelectorVo vo);
}
