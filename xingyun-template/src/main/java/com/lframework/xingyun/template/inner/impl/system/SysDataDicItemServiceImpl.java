package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.inner.entity.SysDataDic;
import com.lframework.xingyun.template.inner.entity.SysDataDicItem;
import com.lframework.xingyun.template.inner.vo.system.dic.item.CreateSysDataDicItemVo;
import com.lframework.xingyun.template.inner.vo.system.dic.item.QuerySysDataDicItemVo;
import com.lframework.xingyun.template.inner.vo.system.dic.item.UpdateSysDataDicItemVo;
import com.lframework.xingyun.template.inner.mappers.system.SysDataDicItemMapper;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.inner.service.system.SysDataDicItemService;
import com.lframework.xingyun.template.inner.service.system.SysDataDicService;
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
public class SysDataDicItemServiceImpl extends
    BaseMpServiceImpl<SysDataDicItemMapper, SysDataDicItem> implements SysDataDicItemService {

  @Autowired
  private SysDataDicService sysDataDicService;

  @Override
  public PageResult<SysDataDicItem> query(Integer pageIndex, Integer pageSize,
      QuerySysDataDicItemVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysDataDicItem> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysDataDicItem> query(QuerySysDataDicItemVo vo) {
    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysDataDicItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysDataDicItem findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Override
  public SysDataDicItem findByCode(String dicCode, String code) {
    SysDataDicItemService thisService = getThis(getClass());
    List<SysDataDicItem> items = thisService.findByDicCode(dicCode);
    if (CollectionUtil.isEmpty(items)) {
      return null;
    }

    return items.stream().filter(t -> t.getCode().equals(code)).findFirst().orElse(null);
  }

  @Cacheable(value = SysDataDicItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #dicCode")
  @Override
  public List<SysDataDicItem> findByDicCode(String dicCode) {
    Wrapper<SysDataDic> queryDicWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getCode, dicCode);
    SysDataDic dic = sysDataDicService.getOne(queryDicWrapper);
    if (dic == null) {
      throw new DefaultClientException("数据字典不存在！");
    }

    Wrapper<SysDataDicItem> queryWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getDicId, dic.getId()).orderByAsc(SysDataDicItem::getOrderNo);
    List<SysDataDicItem> datas = this.list(queryWrapper);

    return datas;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysDataDicItemVo vo) {

    Wrapper<SysDataDicItem> checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getDicId, vo.getDicId())
        .eq(SysDataDicItem::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getDicId, vo.getDicId())
        .eq(SysDataDicItem::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicItem record = new SysDataDicItem();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    record.setDicId(vo.getDicId());
    record.setOrderNo(vo.getOrderNo());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysDataDicItemVo vo) {

    SysDataDicItem record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据字典值不存在！");
    }
    Wrapper<SysDataDicItem> checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getDicId, record.getDicId())
        .eq(SysDataDicItem::getCode, vo.getCode()).ne(SysDataDicItem::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicItem.class)
        .eq(SysDataDicItem::getDicId, record.getDicId())
        .eq(SysDataDicItem::getName, vo.getName()).ne(SysDataDicItem::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    Wrapper<SysDataDicItem> updateWrapper = Wrappers.lambdaUpdate(SysDataDicItem.class)
        .set(SysDataDicItem::getCode, vo.getCode()).set(SysDataDicItem::getName, vo.getName())
        .set(SysDataDicItem::getOrderNo, vo.getOrderNo()).eq(SysDataDicItem::getId, vo.getId());
    this.update(updateWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {
    this.removeById(id);
  }

  @CacheEvict(value = SysDataDicItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
