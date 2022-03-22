package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberVo;
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
  List<MemberDto> query(@Param("vo") QueryMemberVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  MemberDto getById(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<MemberDto> selector(@Param("vo") QueryMemberSelectorVo vo);
}
