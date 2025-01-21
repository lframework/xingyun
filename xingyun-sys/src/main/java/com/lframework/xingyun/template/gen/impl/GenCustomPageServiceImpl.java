package com.lframework.xingyun.template.gen.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.gen.components.custom.page.CustomPageConfig;
import com.lframework.xingyun.template.gen.entity.GenCustomPage;
import com.lframework.xingyun.template.gen.mappers.GenCustomPageMapper;
import com.lframework.xingyun.template.gen.service.GenCustomPageService;
import com.lframework.xingyun.template.gen.vo.custom.page.CreateGenCustomPageVo;
import com.lframework.xingyun.template.gen.vo.custom.page.GenCustomPageSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.page.QueryGenCustomPageVo;
import com.lframework.xingyun.template.gen.vo.custom.page.UpdateGenCustomPageVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenCustomPageServiceImpl extends
    BaseMpServiceImpl<GenCustomPageMapper, GenCustomPage> implements
    GenCustomPageService {

  @Override
  public PageResult<GenCustomPage> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomPageVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomPage> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenCustomPage> query(QueryGenCustomPageVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<GenCustomPage> selector(Integer pageIndex, Integer pageSize,
      GenCustomPageSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomPage> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomPage.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenCustomPage findById(Integer id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Integer create(CreateGenCustomPageVo data) {
    GenCustomPage record = new GenCustomPage();

    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setPageCode(data.getPageCode());
    record.setScriptCode(data.getScriptCode());
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenCustomPageVo data) {
    GenCustomPage record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("自定义表单不存在！");
    }

    record.setPageCode(data.getPageCode());
    record.setScriptCode(data.getScriptCode());
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

    this.updateAllColumnById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void delete(Integer id) {
    this.removeById(id);
  }

  @CacheEvict(value = {GenCustomPage.CACHE_NAME,
      CustomPageConfig.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    super.cleanCacheByKey(key);
  }
}
