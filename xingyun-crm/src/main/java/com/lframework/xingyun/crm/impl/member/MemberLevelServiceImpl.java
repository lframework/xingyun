package com.lframework.xingyun.crm.impl.member;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.core.events.member.CreateMemberEvent;
import com.lframework.xingyun.core.events.member.MemberConsumeEvent;
import com.lframework.xingyun.core.events.member.MemberReturnEvent;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.mappers.MemberLevelMapper;
import com.lframework.xingyun.crm.service.member.ICrmMemberService;
import com.lframework.xingyun.crm.service.member.IMemberLevelService;
import com.lframework.xingyun.crm.vo.member.level.CreateMemberLevelVo;
import com.lframework.xingyun.crm.vo.member.level.QueryMemberLevelVo;
import com.lframework.xingyun.crm.vo.member.level.UpdateMemberLevelVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberLevelServiceImpl extends
    BaseMpServiceImpl<MemberLevelMapper, MemberLevel> implements IMemberLevelService {

  @Override
  public PageResult<MemberLevel> query(Integer pageIndex, Integer pageSize, QueryMemberLevelVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<MemberLevel> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<MemberLevel> query(QueryMemberLevelVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = MemberLevel.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public MemberLevel findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增会员等级，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public String create(CreateMemberLevelVo vo) {

    MemberLevel data = new MemberLevel();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    Wrapper<MemberLevel> checkCodeWrapper = Wrappers.lambdaQuery(MemberLevel.class)
        .eq(MemberLevel::getCode, vo.getCode());
    if (this.count(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }
    data.setName(vo.getName());
    data.setExp(vo.getExp());
    Wrapper<MemberLevel> checkExpWrapper = Wrappers.lambdaQuery(MemberLevel.class)
        .eq(MemberLevel::getExp, vo.getExp());
    if (this.count(checkExpWrapper) > 0) {
      throw new DefaultClientException("经验值重复，请重新输入！");
    }
    data.setIsDefault(vo.getIsDefault());
    if (!StringUtil.isBlank(vo.getDescription())) {
      data.setDescription(vo.getDescription());
    }

    if (vo.getIsDefault()) {
      // 如果新增的是默认等级，那么将其他等级设置为非默认
      Wrapper<MemberLevel> updateDefaultWrapper = Wrappers.lambdaUpdate(MemberLevel.class)
          .set(MemberLevel::getIsDefault, Boolean.FALSE);
      this.update(updateDefaultWrapper);
    }

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改会员等级，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateMemberLevelVo vo) {

    MemberLevel data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("会员等级不存在！");
    }

    Wrapper<MemberLevel> checkCodeWrapper = Wrappers.lambdaQuery(MemberLevel.class)
        .eq(MemberLevel::getCode, vo.getCode()).ne(MemberLevel::getId, vo.getId());
    if (this.count(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<MemberLevel> checkExpWrapper = Wrappers.lambdaQuery(MemberLevel.class)
        .eq(MemberLevel::getExp, vo.getExp()).ne(MemberLevel::getId, vo.getId());
    if (this.count(checkExpWrapper) > 0) {
      throw new DefaultClientException("经验值重复，请重新输入！");
    }

    if (vo.getIsDefault()) {
      if (!vo.getAvailable()) {
        throw new DefaultClientException("默认等级不允许停用！");
      }
      // 如果修改的是默认等级，那么将其他等级设置为非默认
      Wrapper<MemberLevel> updateDefaultWrapper = Wrappers.lambdaUpdate(MemberLevel.class)
          .set(MemberLevel::getIsDefault, Boolean.FALSE);
      this.update(updateDefaultWrapper);
    } else {
      if (data.getIsDefault()) {
        // 从默认改为非默认
        throw new DefaultClientException("默认等级不允许改为非默认等级！");
      }
    }

    LambdaUpdateWrapper<MemberLevel> updateWrapper = Wrappers.lambdaUpdate(MemberLevel.class)
        .set(MemberLevel::getCode, vo.getCode()).set(MemberLevel::getName, vo.getName())
        .set(MemberLevel::getExp, vo.getExp()).set(MemberLevel::getIsDefault, vo.getIsDefault())
        .set(MemberLevel::getAvailable, vo.getAvailable()).set(MemberLevel::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? null : vo.getDescription())
        .eq(MemberLevel::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public MemberLevel getDefaultLevel() {
    Wrapper<MemberLevel> queryWrapper = Wrappers.lambdaQuery(MemberLevel.class)
        .eq(MemberLevel::getIsDefault, Boolean.TRUE);
    MemberLevel level = this.getOne(queryWrapper);

    return level;
  }

  @Override
  public MemberLevel match(Integer exp) {
    Wrapper<MemberLevel> queryWrapper = Wrappers.lambdaQuery(MemberLevel.class)
        .eq(MemberLevel::getAvailable, true).orderByAsc(MemberLevel::getExp);
    List<MemberLevel> levelList = this.list(queryWrapper);

    // 有默认等级，一定不会为空的
    if(levelList.size() == 1) {
      return levelList.get(0);
    }

    MemberLevel result = null;
    for (int i = 0; i < levelList.size() - 1; i++) {
      if (exp > levelList.get(i).getExp() && exp <= levelList.get(i + 1).getExp()) {
        result = result = levelList.get(i + 1);
      }
    }
    if (result == null) {
      result = levelList.get(0);
    }

    return result;
  }

  @CacheEvict(value = MemberLevel.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

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

    @Transactional
    @Override
    public void onApplicationEvent(CreateMemberEvent createMemberEvent) {
      String id = createMemberEvent.getId();

      MemberLevel level = memberLevelService.getDefaultLevel();

      CrmMember member = new CrmMember();
      member.setId(id);
      member.setLevelId(level.getId());

      crmMemberService.save(member);
    }
  }

  /**
   * 会员消费监听器
   */
  @Component
  public static class MemberConsumeListener implements ApplicationListener<MemberConsumeEvent> {

    @Autowired
    private ICrmMemberService crmMemberService;

    @Override
    public void onApplicationEvent(MemberConsumeEvent memberConsumeEvent) {
      crmMemberService.addLevel(memberConsumeEvent.getId(), memberConsumeEvent.getAmount());
    }
  }

  /**
   * 会员退货监听器
   */
  @Component
  public static class MemberReturnListener implements ApplicationListener<MemberReturnEvent> {

    @Autowired
    private ICrmMemberService crmMemberService;

    @Override
    public void onApplicationEvent(MemberReturnEvent memberReturnEvent) {
      crmMemberService.dropLevel(memberReturnEvent.getId(), memberReturnEvent.getAmount());
    }
  }
}
