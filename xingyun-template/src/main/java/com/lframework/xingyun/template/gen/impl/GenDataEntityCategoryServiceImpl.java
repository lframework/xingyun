package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.xingyun.template.gen.entity.GenDataEntity;
import com.lframework.xingyun.template.gen.entity.GenDataEntityCategory;
import com.lframework.xingyun.template.gen.mappers.GenDataEntityCategoryMapper;
import com.lframework.xingyun.template.gen.service.GenDataEntityCategoryService;
import com.lframework.xingyun.template.gen.service.GenDataEntityService;
import com.lframework.xingyun.template.gen.vo.data.entity.category.CreateGenDataEntityCategoryVo;
import com.lframework.xingyun.template.gen.vo.data.entity.category.GenDataEntityCategorySelectorVo;
import com.lframework.xingyun.template.gen.vo.data.entity.category.UpdateGenDataEntityCategoryVo;
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
public class GenDataEntityCategoryServiceImpl extends
    BaseMpServiceImpl<GenDataEntityCategoryMapper, GenDataEntityCategory> implements
    GenDataEntityCategoryService {

  @Autowired
  private GenDataEntityService genDataEntityService;

  @Cacheable(value = GenDataEntityCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + 'all'")
  @Override
  public List<GenDataEntityCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<GenDataEntityCategory> selector(Integer pageIndex, Integer pageSize,
      GenDataEntityCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<GenDataEntityCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenDataEntityCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenDataEntityCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenDataEntityCategoryVo vo) {

    Wrapper<GenDataEntityCategory> checkWrapper = Wrappers.lambdaQuery(GenDataEntityCategory.class)
        .eq(GenDataEntityCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenDataEntityCategory.class)
        .eq(GenDataEntityCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenDataEntityCategory record = new GenDataEntityCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenDataEntityCategoryVo vo) {
    Wrapper<GenDataEntityCategory> checkWrapper = Wrappers.lambdaQuery(GenDataEntityCategory.class)
        .eq(GenDataEntityCategory::getCode, vo.getCode())
        .ne(GenDataEntityCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenDataEntityCategory.class)
        .eq(GenDataEntityCategory::getName, vo.getName())
        .ne(GenDataEntityCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenDataEntityCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据实体分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<GenDataEntity> queryWrapper = Wrappers.lambdaQuery(GenDataEntity.class)
        .eq(GenDataEntity::getCategoryId, id);
    if (genDataEntityService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类下存在数据实体，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = GenDataEntityCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
