package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.entity.GenCustomFormCategory;
import com.lframework.xingyun.template.gen.mappers.GenCustomFormCategoryMapper;
import com.lframework.xingyun.template.gen.service.GenCustomFormCategoryService;
import com.lframework.xingyun.template.gen.service.GenCustomFormService;
import com.lframework.xingyun.template.gen.vo.custom.form.category.CreateGenCustomFormCategoryVo;
import com.lframework.xingyun.template.gen.vo.custom.form.category.GenCustomFormCategorySelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.form.category.UpdateGenCustomFormCategoryVo;
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
public class GenCustomFormCategoryServiceImpl extends
    BaseMpServiceImpl<GenCustomFormCategoryMapper, GenCustomFormCategory> implements
    GenCustomFormCategoryService {

  @Autowired
  private GenCustomFormService genCustomFormService;

  @Cacheable(value = GenCustomFormCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + 'all'")
  @Override
  public List<GenCustomFormCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<GenCustomFormCategory> selector(Integer pageIndex, Integer pageSize,
      GenCustomFormCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<GenCustomFormCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomFormCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenCustomFormCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenCustomFormCategoryVo vo) {

    Wrapper<GenCustomFormCategory> checkWrapper = Wrappers
        .lambdaQuery(GenCustomFormCategory.class)
        .eq(GenCustomFormCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomFormCategory.class)
        .eq(GenCustomFormCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomFormCategory record = new GenCustomFormCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenCustomFormCategoryVo vo) {
    Wrapper<GenCustomFormCategory> checkWrapper = Wrappers
        .lambdaQuery(GenCustomFormCategory.class)
        .eq(GenCustomFormCategory::getCode, vo.getCode())
        .ne(GenCustomFormCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenCustomFormCategory.class)
        .eq(GenCustomFormCategory::getName, vo.getName())
        .ne(GenCustomFormCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenCustomFormCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("自定义列表分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<GenCustomForm> queryWrapper = Wrappers.lambdaQuery(GenCustomForm.class)
        .eq(GenCustomForm::getCategoryId, id);
    if (genCustomFormService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类下存在自定义表单，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = GenCustomFormCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
