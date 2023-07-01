package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.xingyun.template.inner.entity.SysDataDic;
import com.lframework.xingyun.template.inner.service.system.SysDataDicCategoryService;
import com.lframework.xingyun.template.inner.service.system.SysDataDicService;
import com.lframework.xingyun.template.inner.vo.system.dic.category.CreateSysDataDicCategoryVo;
import com.lframework.xingyun.template.inner.vo.system.dic.category.SysDataDicCategorySelectorVo;
import com.lframework.xingyun.template.inner.vo.system.dic.category.UpdateSysDataDicCategoryVo;
import com.lframework.xingyun.template.inner.mappers.system.SysDataDicCategoryMapper;
import com.lframework.xingyun.template.inner.entity.SysDataDicCategory;
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
public class SysDataDicCategoryServiceImpl extends
    BaseMpServiceImpl<SysDataDicCategoryMapper, SysDataDicCategory> implements
    SysDataDicCategoryService {

  @Autowired
  private SysDataDicService sysDataDicService;

  @Cacheable(value = SysDataDicCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + 'all'")
  @Override
  public List<SysDataDicCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<SysDataDicCategory> selector(Integer pageIndex, Integer pageSize,
      SysDataDicCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysDataDicCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysDataDicCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysDataDicCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysDataDicCategoryVo vo) {

    Wrapper<SysDataDicCategory> checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicCategory record = new SysDataDicCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysDataDicCategoryVo vo) {
    Wrapper<SysDataDicCategory> checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getCode, vo.getCode()).ne(SysDataDicCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysDataDicCategory.class)
        .eq(SysDataDicCategory::getName, vo.getName()).ne(SysDataDicCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysDataDicCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据字典分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<SysDataDic> queryDicWrapper = Wrappers.lambdaQuery(SysDataDic.class)
        .eq(SysDataDic::getCategoryId, id);
    if (sysDataDicService.count(queryDicWrapper) > 0) {
      throw new DefaultClientException("此分类下存在数据字典，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = SysDataDicCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
