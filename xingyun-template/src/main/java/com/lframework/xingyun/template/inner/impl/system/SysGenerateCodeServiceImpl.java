package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.dto.GenerateCodeDto;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.inner.entity.SysGenerateCode;
import com.lframework.xingyun.template.inner.mappers.system.SysGenerateCodeMapper;
import com.lframework.xingyun.template.inner.service.system.SysGenerateCodeService;
import com.lframework.xingyun.template.inner.vo.system.generate.CreateSysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.QuerySysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.SettingSysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.UpdateSysGenerateCodeVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysGenerateCodeServiceImpl extends
    BaseMpServiceImpl<SysGenerateCodeMapper, SysGenerateCode> implements SysGenerateCodeService {

  @Override
  public PageResult<SysGenerateCode> query(Integer pageIndex, Integer pageSize,
      QuerySysGenerateCodeVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysGenerateCode> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysGenerateCode> query(QuerySysGenerateCodeVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysGenerateCode.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysGenerateCode findById(Integer id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "新增编号规则，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Integer create(CreateSysGenerateCodeVo vo) {

    Wrapper<SysGenerateCode> checkWrapper = Wrappers.lambdaQuery(SysGenerateCode.class)
        .eq(SysGenerateCode::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("规则ID重复，请重新输入！");
    }
    SysGenerateCode data = new SysGenerateCode();
    data.setId(vo.getId());
    data.setName(vo.getName());
    data.setConfigStr(StringPool.EMPTY_STR);

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改编号规则，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysGenerateCodeVo vo) {

    SysGenerateCode data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("编号规则不存在！");
    }

    LambdaUpdateWrapper<SysGenerateCode> updateWrapper = Wrappers.lambdaUpdate(
            SysGenerateCode.class)
        .set(SysGenerateCode::getName, vo.getName())
        .eq(SysGenerateCode::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "删除编号规则，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(Integer id) {

    getBaseMapper().deleteById(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "设置编号规则，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SettingSysGenerateCodeVo vo) {
    SysGenerateCode data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("编号规则不存在！");
    }

    LambdaUpdateWrapper<SysGenerateCode> updateWrapper = Wrappers.lambdaUpdate(
            SysGenerateCode.class)
        .set(SysGenerateCode::getConfigStr, vo.getConfigStr())
        .eq(SysGenerateCode::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = {SysGenerateCode.CACHE_NAME,
      GenerateCodeDto.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
