package com.lframework.xingyun.crm.impl.member;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import com.lframework.xingyun.crm.mappers.CrmMemberMapper;
import com.lframework.xingyun.crm.service.member.ICrmMemberService;
import com.lframework.xingyun.crm.service.member.IMemberLevelConfigService;
import com.lframework.xingyun.crm.service.member.IMemberLevelService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrmMemberServiceImpl extends BaseMpServiceImpl<CrmMemberMapper, CrmMember> implements
    ICrmMemberService {

  @Autowired
  private IMemberLevelConfigService memberLevelConfigService;

  @Autowired
  private IMemberLevelService memberLevelService;

  @Transactional
  @Override
  public void addLevel(String memberId, BigDecimal amount) {

    MemberLevelConfig config = memberLevelConfigService.get();
    Integer exp = config.getExp();
    if (exp == 0) {
      return;
    }

    int addExp = NumberUtil.div(amount, exp).intValue();
    this.addLevel(memberId, addExp);
  }

  @Transactional
  @Override
  public void dropLevel(String memberId, BigDecimal amount) {
    MemberLevelConfig config = memberLevelConfigService.get();
    Integer exp = config.getExp();
    if (exp == 0) {
      return;
    }

    int subExp = NumberUtil.div(amount, exp).intValue();
    this.dropLevel(memberId, subExp);
  }

  @Transactional
  @Override
  public void addLevel(String memberId, Integer exp) {
    if (exp < 0) {
      throw new DefaultSysException("exp不能为负数");
    }

    if (exp == 0) {
      return;
    }

    this.getBaseMapper().addExp(memberId, exp);

    CrmMember member = this.getById(memberId);
    MemberLevel level = memberLevelService.match(member.getExp());

    Wrapper<CrmMember> updateWrapper = Wrappers.lambdaUpdate(CrmMember.class)
        .set(CrmMember::getLevelId, level.getId()).eq(CrmMember::getId, memberId);
    this.update(updateWrapper);
  }

  @Transactional
  @Override
  public void dropLevel(String memberId, Integer exp) {
    if (exp < 0) {
      throw new DefaultSysException("exp不能为负数");
    }

    if (exp == 0) {
      return;
    }

    this.getBaseMapper().subExp(memberId, exp);

    CrmMember member = this.getById(memberId);
    MemberLevel level = memberLevelService.match(member.getExp());

    Wrapper<CrmMember> updateWrapper = Wrappers.lambdaUpdate(CrmMember.class)
        .set(CrmMember::getLevelId, level.getId()).eq(CrmMember::getId, memberId);
    this.update(updateWrapper);
  }
}
