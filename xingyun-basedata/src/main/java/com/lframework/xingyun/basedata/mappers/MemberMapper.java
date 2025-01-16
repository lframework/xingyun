package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
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
  @Sorts({
      @Sort(value = "code", autoParse = true),
      @Sort(value = "name", autoParse = true),
      @Sort(value = "createTime", autoParse = true),
      @Sort(value = "updateTime", autoParse = true),
  })
  List<Member> query(@Param("vo") QueryMemberVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Member> selector(@Param("vo") QueryMemberSelectorVo vo);
}
