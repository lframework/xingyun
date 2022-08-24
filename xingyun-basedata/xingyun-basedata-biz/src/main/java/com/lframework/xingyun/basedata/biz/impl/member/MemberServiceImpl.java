package com.lframework.xingyun.basedata.biz.impl.member;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.biz.mappers.MemberMapper;
import com.lframework.xingyun.basedata.biz.service.member.IMemberService;
import com.lframework.xingyun.basedata.facade.entity.Member;
import com.lframework.xingyun.basedata.facade.vo.member.CreateMemberVo;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.member.QueryMemberVo;
import com.lframework.xingyun.basedata.facade.vo.member.UpdateMemberVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl extends BaseMpServiceImpl<MemberMapper, Member> implements
    IMemberService {

  @Override
  public PageResult<Member> query(Integer pageIndex, Integer pageSize, QueryMemberVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Member> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<Member> query(QueryMemberVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = Member.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public Member findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用会员，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Member> updateWrapper = Wrappers.lambdaUpdate(Member.class)
        .set(Member::getAvailable, Boolean.FALSE)
        .in(Member::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用会员，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Member> updateWrapper = Wrappers.lambdaUpdate(Member.class)
        .set(Member::getAvailable, Boolean.TRUE)
        .in(Member::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增会员，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateMemberVo vo) {

    Wrapper<Member> checkWrapper = Wrappers.lambdaQuery(Member.class)
        .eq(Member::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      checkWrapper = Wrappers.lambdaQuery(Member.class)
          .eq(Member::getTelephone, vo.getTelephone());
      if (getBaseMapper().selectCount(checkWrapper) > 0) {
        throw new DefaultClientException("手机号重复，请重新输入！");
      }
    }

    Member data = new Member();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setGender(EnumUtil.getByCode(Gender.class, vo.getGender()));
    if (!StringUtil.isBlank(vo.getTelephone())) {
      data.setTelephone(vo.getTelephone());
    }
    if (!StringUtil.isBlank(vo.getEmail())) {
      data.setEmail(vo.getEmail());
    }
    if (vo.getBirthday() != null) {
      data.setBirthday(vo.getBirthday());
    }
    if (vo.getJoinDay() != null) {
      data.setJoinDay(vo.getJoinDay());
    }
    if (!StringUtil.isBlank(vo.getShopId())) {
      data.setShopId(vo.getShopId());
    }
    if (!StringUtil.isBlank(vo.getGuiderId())) {
      data.setGuiderId(vo.getGuiderId());
    }
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改会员，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateMemberVo vo) {

    Member data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("会员不存在！");
    }

    Wrapper<Member> checkWrapper = Wrappers.lambdaQuery(Member.class)
        .eq(Member::getCode, vo.getCode())
        .ne(Member::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      checkWrapper = Wrappers.lambdaQuery(Member.class)
          .eq(Member::getTelephone, vo.getTelephone())
          .ne(Member::getId, vo.getId());
      if (getBaseMapper().selectCount(checkWrapper) > 0) {
        throw new DefaultClientException("手机号重复，请重新输入！");
      }
    }

    LambdaUpdateWrapper<Member> updateWrapper = Wrappers.lambdaUpdate(Member.class)
        .set(Member::getCode, vo.getCode()).set(Member::getName, vo.getName())
        .set(Member::getGender, EnumUtil.getByCode(Gender.class, vo.getGender()))
        .set(Member::getTelephone,
            !StringUtil.isBlank(vo.getTelephone()) ? vo.getTelephone() : null)
        .set(Member::getEmail, !StringUtil.isBlank(vo.getEmail()) ? vo.getEmail() : null)
        .set(Member::getBirthday, vo.getBirthday() != null ? vo.getBirthday() : null)
        .set(Member::getJoinDay, vo.getJoinDay() != null ? vo.getJoinDay() : null)
        .set(Member::getShopId, !StringUtil.isBlank(vo.getShopId()) ? vo.getShopId() : null)
        .set(Member::getGuiderId,
            !StringUtil.isBlank(vo.getGuiderId()) ? vo.getGuiderId() : null)
        .set(Member::getAvailable, vo.getAvailable()).set(Member::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(Member::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public PageResult<Member> selector(Integer pageIndex, Integer pageSize,
      QueryMemberSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    List<Member> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @CacheEvict(value = Member.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
