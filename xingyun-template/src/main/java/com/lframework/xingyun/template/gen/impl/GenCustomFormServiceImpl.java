package com.lframework.xingyun.template.gen.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.components.custom.form.CustomFormConfig;
import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.mappers.GenCustomFormMapper;
import com.lframework.xingyun.template.gen.service.GenCustomFormService;
import com.lframework.xingyun.template.gen.vo.custom.form.CreateGenCustomFormVo;
import com.lframework.xingyun.template.gen.vo.custom.form.GenCustomFormSelectorVo;
import com.lframework.xingyun.template.gen.vo.custom.form.QueryGenCustomFormVo;
import com.lframework.xingyun.template.gen.vo.custom.form.UpdateGenCustomFormVo;
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
public class GenCustomFormServiceImpl extends
    BaseMpServiceImpl<GenCustomFormMapper, GenCustomForm> implements
    GenCustomFormService {

  @Override
  public PageResult<GenCustomForm> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomFormVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomForm> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<GenCustomForm> query(QueryGenCustomFormVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<GenCustomForm> selector(Integer pageIndex, Integer pageSize,
      GenCustomFormSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<GenCustomForm> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = GenCustomForm.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public GenCustomForm findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateGenCustomFormVo data) {
    GenCustomForm record = new GenCustomForm();

    record.setId(IdUtil.getId());
    record.setName(data.getName());
    if (!StringUtil.isBlank(data.getCategoryId())) {
      record.setCategoryId(data.getCategoryId());
    }
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());
    record.setIsDialog(data.getIsDialog());
    if (data.getIsDialog()) {
      record.setDialogTittle(data.getDialogTittle());
      record.setDialogWidth(data.getDialogWidth());
    }
    record.setPrefixSubmit(data.getPrefixSubmit());
    record.setSuffixSubmit(data.getSuffixSubmit());
    record.setRequireQuery(data.getRequireQuery());
    record.setQueryBean(data.getQueryBean());
    record.setHandleBean(data.getHandleBean());
    record.setFormConfig(data.getFormConfig());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateGenCustomFormVo data) {
    GenCustomForm record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("自定义表单不存在！");
    }

    Wrapper<GenCustomForm> updateWrapper = Wrappers.lambdaUpdate(GenCustomForm.class)
        .eq(GenCustomForm::getId, data.getId()).set(GenCustomForm::getName, data.getName())
        .set(GenCustomForm::getCategoryId,
            StringUtil.isBlank(data.getCategoryId()) ? null : data.getCategoryId())
        .set(GenCustomForm::getIsDialog, data.getIsDialog())
        .set(GenCustomForm::getDialogTittle,
            StringUtil.isBlank(data.getDialogTittle()) ? StringPool.EMPTY_STR
                : data.getDialogTittle())
        .set(GenCustomForm::getDialogWidth,
            StringUtil.isBlank(data.getDialogWidth()) ? StringPool.EMPTY_STR
                : data.getDialogWidth())
        .set(GenCustomForm::getPrefixSubmit,
            StringUtil.isBlank(data.getPrefixSubmit()) ? StringPool.EMPTY_STR
                : data.getPrefixSubmit())
        .set(GenCustomForm::getSuffixSubmit,
            StringUtil.isBlank(data.getSuffixSubmit()) ? StringPool.EMPTY_STR
                : data.getSuffixSubmit())
        .set(GenCustomForm::getRequireQuery, data.getRequireQuery())
        .set(GenCustomForm::getQueryBean,
            StringUtil.isBlank(data.getQueryBean()) ? StringPool.EMPTY_STR : data.getQueryBean())
        .set(GenCustomForm::getHandleBean,
            StringUtil.isBlank(data.getHandleBean()) ? StringPool.EMPTY_STR : data.getHandleBean())
        .set(GenCustomForm::getDescription,
            StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR
                : data.getDescription())
        .set(GenCustomForm::getFormConfig, data.getFormConfig())
        .set(GenCustomForm::getAvailable, data.getAvailable());

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

    Wrapper<GenCustomForm> wrapper = Wrappers.lambdaUpdate(GenCustomForm.class)
        .set(GenCustomForm::getAvailable, Boolean.TRUE).in(GenCustomForm::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(List<String> ids) {
    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<GenCustomForm> wrapper = Wrappers.lambdaUpdate(GenCustomForm.class)
        .set(GenCustomForm::getAvailable, Boolean.FALSE).in(GenCustomForm::getId, ids);
    getBaseMapper().update(wrapper);
  }

  @CacheEvict(value = {GenCustomForm.CACHE_NAME,
      CustomFormConfig.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    super.cleanCacheByKey(key);
  }
}
