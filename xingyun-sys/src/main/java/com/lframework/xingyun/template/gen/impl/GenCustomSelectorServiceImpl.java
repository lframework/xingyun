package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.components.custom.selector.CustomSelectorConfig;
import com.lframework.xingyun.template.gen.entity.GenCustomSelector;
import com.lframework.xingyun.template.gen.mappers.GenCustomSelectorMapper;
import com.lframework.xingyun.template.gen.service.GenCustomSelectorService;
import com.lframework.xingyun.template.gen.vo.custom.selector.CreateGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.GenCustomSelectorSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.QueryGenCustomSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.selector.UpdateGenCustomSelectorVo;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomSelectorServiceImpl extends
    BaseMpServiceImpl<GenCustomSelectorMapper, GenCustomSelector> implements
    GenCustomSelectorService {

  @Override
  public PageResult<GenCustomSelector> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomSelector> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenCustomSelector> query(QueryGenCustomSelectorVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<GenCustomSelector> selector(Integer pageIndex, Integer pageSize,
      GenCustomSelectorSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomSelector> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomSelector.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenCustomSelector findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenCustomSelectorVo data) {
    GenCustomSelector record = new GenCustomSelector();

    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setCustomListId(data.getCustomListId());
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());
    record.setIdColumn(data.getIdColumn());
    record.setIdColumnRelaId(data.getIdColumnRelaId());
    record.setNameColumn(data.getNameColumn());
    record.setNameColumnRelaId(data.getNameColumnRelaId());
    record.setDialogTittle(
        StringUtil.isBlank(data.getDialogTittle()) ? StringPool.EMPTY_STR : data.getDialogTittle());
    record.setDialogWidth(data.getDialogWidth());
    record.setPlaceholder(
        StringUtil.isBlank(data.getPlaceholder()) ? StringPool.EMPTY_STR : data.getPlaceholder());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenCustomSelectorVo data) {
    GenCustomSelector record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表不存在！");
    }

    Wrapper<GenCustomSelector> updateWrapper = Wrappers.lambdaUpdate(GenCustomSelector.class)
        .eq(GenCustomSelector::getId, data.getId()).set(GenCustomSelector::getName, data.getName())
        .set(GenCustomSelector::getCategoryId,
            StringUtil.isBlank(data.getCategoryId()) ? null : data.getCategoryId())
        .set(GenCustomSelector::getDialogTittle,
            StringUtil.isBlank(data.getDialogTittle()) ? StringPool.EMPTY_STR
                : data.getDialogTittle())
        .set(GenCustomSelector::getDialogWidth, data.getDialogWidth())
        .set(GenCustomSelector::getPlaceholder,
            StringUtil.isBlank(data.getPlaceholder()) ? StringPool.EMPTY_STR
                : data.getPlaceholder())
        .set(GenCustomSelector::getIdColumn, data.getIdColumn())
        .set(GenCustomSelector::getIdColumnRelaId, data.getIdColumnRelaId())
        .set(GenCustomSelector::getNameColumn, data.getNameColumn())
        .set(GenCustomSelector::getNameColumnRelaId, data.getNameColumnRelaId())
        .set(GenCustomSelector::getDescription,
            StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR
                : data.getDescription()).set(GenCustomSelector::getAvailable, data.getAvailable());

    this.update(updateWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(String id) {
    this.removeById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchDelete(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    for (String id : ids) {
      this.delete(id);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomSelector> wrapper = Wrappers.lambdaUpdate(GenCustomSelector.class)
        .set(GenCustomSelector::getAvailable, Boolean.TRUE).in(GenCustomSelector::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomSelector> wrapper = Wrappers.lambdaUpdate(GenCustomSelector.class)
        .set(GenCustomSelector::getAvailable, Boolean.FALSE).in(GenCustomSelector::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Override
  public List<String> getRelaGenCustomListIds(String customListId) {
    return getBaseMapper().getRelaGenCustomListIds(customListId);
  }

  @CacheEvict(value = {GenCustomSelector.CACHE_NAME, CustomSelectorConfig.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    super.cleanCacheByKey(key);
  }
}
