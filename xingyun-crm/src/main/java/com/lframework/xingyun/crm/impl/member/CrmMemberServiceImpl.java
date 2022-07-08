package com.lframework.xingyun.crm.impl.member;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.core.events.member.CreateMemberEvent;
import com.lframework.xingyun.core.events.member.UpdateMemberEvent;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import com.lframework.xingyun.crm.mappers.CrmMemberMapper;
import com.lframework.xingyun.crm.service.member.ICrmMemberService;
import com.lframework.xingyun.crm.service.member.IMemberLevelConfigService;
import com.lframework.xingyun.crm.service.member.IMemberLevelService;
import com.lframework.xingyun.crm.vo.member.QueryCrmMemberVo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrmMemberServiceImpl extends BaseMpServiceImpl<CrmMemberMapper, CrmMember> implements
    ICrmMemberService {

  @Autowired
  private IMemberLevelConfigService memberLevelConfigService;

  @Autowired
  private IMemberLevelService memberLevelService;

  @Override
  public PageResult<CrmMember> query(Integer pageIndex, Integer pageSize, QueryCrmMemberVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<CrmMember> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<CrmMember> query(QueryCrmMemberVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public CrmMember findById(String id) {
    return getBaseMapper().selectById(id);
  }

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

  /**
   * 新增会员监听器
   */
  @Component
  public static class CreateMemberListener implements ApplicationListener<CreateMemberEvent> {

    @Autowired
    private ICrmMemberService crmMemberService;

    @Autowired
    private IMemberLevelService memberLevelService;

    @Autowired
    private IMemberService memberService;

    @Transactional
    @Override
    public void onApplicationEvent(CreateMemberEvent createMemberEvent) {
      String id = createMemberEvent.getId();
      Member record = memberService.findById(id);

      MemberLevel level = memberLevelService.getDefaultLevel();

      CrmMember member = new CrmMember();
      member.setCode(record.getCode());
      member.setName(record.getName());
      member.setGender(record.getGender());
      member.setTelephone(record.getTelephone());
      member.setBirthday(record.getBirthday());
      member.setJoinDay(record.getJoinDay());
      member.setShopId(record.getShopId());
      member.setGuiderId(record.getGuiderId());

      member.setId(id);
      member.setLevelId(level.getId());

      crmMemberService.save(member);
    }
  }

  /**
   * 修改会员监听器
   */
  @Component
  public static class UpdateMemberListener implements ApplicationListener<UpdateMemberEvent> {

    @Autowired
    private ICrmMemberService crmMemberService;

    @Autowired
    private IMemberService memberService;

    @Transactional
    @Override
    public void onApplicationEvent(UpdateMemberEvent event) {
      String id = event.getId();
      Member record = memberService.findById(id);

      Wrapper<CrmMember> updateWrapper = Wrappers.lambdaUpdate(CrmMember.class)
          .set(CrmMember::getCode, record.getCode()).set(CrmMember::getName, record.getName())
          .set(CrmMember::getGender, record.getGender())
          .set(CrmMember::getTelephone, record.getTelephone())
          .set(CrmMember::getBirthday, record.getBirthday())
          .set(CrmMember::getJoinDay, record.getJoinDay())
          .set(CrmMember::getShopId, record.getShopId())
          .set(CrmMember::getGuiderId, record.getGuiderId()).eq(CrmMember::getId, id);

      crmMemberService.update(updateWrapper);
    }
  }
}
