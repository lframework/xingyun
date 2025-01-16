package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.inner.entity.SysDataDic;
import com.lframework.xingyun.template.inner.entity.SysDataDicItem;
import com.lframework.xingyun.template.inner.service.system.SysDataDicItemService;
import com.lframework.xingyun.template.inner.service.system.SysDataDicService;
import com.lframework.xingyun.template.inner.vo.system.dic.CreateSysDataDicVo;
import com.lframework.xingyun.template.inner.vo.system.dic.QuerySysDataDicVo;
import com.lframework.xingyun.template.inner.vo.system.dic.SysDataDicSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.dic.UpdateSysDataDicVo;
import com.lframework.xingyun.template.inner.mappers.system.SysDataDicMapper;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysDataDicServiceImpl extends
    BaseMpServiceImpl<SysDataDicMapper, SysDataDic> implements
    SysDataDicService {

  @Autowired
  private SysDataDicItemService sysDataDicItemService;

  @Override
  public PageResult<SysDataDic> query(Integer pageIndex, Integer pageSize, QuerySysDataDicVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysDataDic> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysDataDic> query(QuerySysDataDicVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<SysDataDic> selector(Integer pageIndex, Integer pageSize,
      SysDataDicSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysDataDic> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysDataDic.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysDataDic findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysDataDicVo vo) {

    Wrapper<SysDataDic> checkWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDic record = new SysDataDic();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    if (!StringUtil.isBlank(vo.getCategoryId())) {
      record.setCategoryId(vo.getCategoryId());
    }

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysDataDicVo vo) {
    Wrapper<SysDataDic> checkWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getCode, vo.getCode()).ne(SysDataDic::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getName, vo.getName()).ne(SysDataDic::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDic record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据字典不存在！");
    }

    Wrapper<SysDataDic> updateWrapper = Wrappers.lambdaUpdate(SysDataDic.class)
        .set(SysDataDic::getCode, vo.getCode()).set(SysDataDic::getName, vo.getName())
        .set(SysDataDic::getCategoryId,
            StringUtil.isBlank(vo.getCategoryId()) ? null : vo.getCategoryId())
        .eq(SysDataDic::getId, vo.getId());
    this.update(updateWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    this.removeById(id);

    Wrapper<SysDataDicItem> deleteItemWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getDicId, id);
    sysDataDicItemService.remove(deleteItemWrapper);
  }

  @CacheEvict(value = SysDataDic.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
