package com.lframework.xingyun.crm.service.member;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import com.lframework.xingyun.crm.vo.member.level.config.UpdateMemberLevelConfigVo;

/**
 * 会员等级规则 Service
 *
 * @author zmj
 */
public interface IMemberLevelConfigService extends BaseMpService<MemberLevelConfig> {

  /**
   * 查询
   *
   * @return
   */
  MemberLevelConfig get();

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateMemberLevelConfigVo vo);
}
