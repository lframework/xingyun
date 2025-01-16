package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.xingyun.template.gen.entity.GenDataObj;
import com.lframework.xingyun.template.gen.entity.GenDataObjCategory;
import com.lframework.xingyun.template.gen.mappers.GenDataObjCategoryMapper;
import com.lframework.xingyun.template.gen.service.GenDataObjCategoryService;
import com.lframework.xingyun.template.gen.service.GenDataObjService;
import com.lframework.xingyun.template.gen.vo.data.obj.category.CreateGenDataObjCategoryVo;
import com.lframework.xingyun.template.gen.vo.data.obj.category.GenDataObjCategorySelectorVo;
import com.lframework.xingyun.template.gen.vo.data.obj.category.UpdateGenDataObjCategoryVo;
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
public class GenDataObjCategoryServiceImpl extends
    BaseMpServiceImpl<GenDataObjCategoryMapper, GenDataObjCategory> implements
    GenDataObjCategoryService {

  @Autowired
  private GenDataObjService genDataObjService;

  @Cacheable(value = GenDataObjCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + 'all'")
  @Override
  public List<GenDataObjCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<GenDataObjCategory> selector(Integer pageIndex, Integer pageSize,
      GenDataObjCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<GenDataObjCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenDataObjCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenDataObjCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenDataObjCategoryVo vo) {

    Wrapper<GenDataObjCategory> checkWrapper = Wrappers.lambdaQuery(GenDataObjCategory.class)
        .eq(GenDataObjCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenDataObjCategory.class)
        .eq(GenDataObjCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenDataObjCategory record = new GenDataObjCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenDataObjCategoryVo vo) {
    Wrapper<GenDataObjCategory> checkWrapper = Wrappers.lambdaQuery(GenDataObjCategory.class)
        .eq(GenDataObjCategory::getCode, vo.getCode())
        .ne(GenDataObjCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(GenDataObjCategory.class)
        .eq(GenDataObjCategory::getName, vo.getName())
        .ne(GenDataObjCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    GenDataObjCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("数据对象分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<GenDataObj> queryWrapper = Wrappers.lambdaQuery(GenDataObj.class)
        .eq(GenDataObj::getCategoryId, id);
    if (genDataObjService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类下存在数据对象，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = GenDataObjCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
