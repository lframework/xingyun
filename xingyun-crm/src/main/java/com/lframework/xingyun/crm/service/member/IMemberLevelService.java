package com.lframework.xingyun.crm.service.member;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.vo.member.level.CreateMemberLevelVo;
import com.lframework.xingyun.crm.vo.member.level.QueryMemberLevelVo;
import com.lframework.xingyun.crm.vo.member.level.UpdateMemberLevelVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员等级 Service
 *
 * @author zmj
 */
public interface IMemberLevelService extends BaseMpService<MemberLevel> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<MemberLevel> query(Integer pageIndex, Integer pageSize, QueryMemberLevelVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<MemberLevel> query(QueryMemberLevelVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  MemberLevel findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateMemberLevelVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateMemberLevelVo vo);

  /**
   * 获取默认会员等级
   * @return
   */
  MemberLevel getDefaultLevel();

  /**
   * 根据经验值匹配当前等级
   * @param exp
   * @return
   */
  MemberLevel match(Integer exp);
}
