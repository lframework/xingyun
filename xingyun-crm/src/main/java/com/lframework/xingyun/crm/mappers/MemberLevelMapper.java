package com.lframework.xingyun.crm.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.vo.member.level.QueryMemberLevelVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员等级 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface MemberLevelMapper extends BaseMapper<MemberLevel> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<MemberLevel> query(@Param("vo") QueryMemberLevelVo vo);
}
