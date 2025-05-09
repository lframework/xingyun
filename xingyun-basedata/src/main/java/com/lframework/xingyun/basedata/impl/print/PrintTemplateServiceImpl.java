package com.lframework.xingyun.basedata.impl.print;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.mappers.PrintTemplateMapper;
import com.lframework.xingyun.basedata.service.print.PrintTemplateService;
import com.lframework.xingyun.basedata.vo.print.CreatePrintTemplateVo;
import com.lframework.xingyun.basedata.vo.print.QueryPrintTemplateVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateDemoDataVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateSettingVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateVo;
import com.lframework.xingyun.core.annotations.OpLog;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrintTemplateServiceImpl extends
    BaseMpServiceImpl<PrintTemplateMapper, PrintTemplate> implements
    PrintTemplateService {

  @Override
  public PageResult<PrintTemplate> query(Integer pageIndex, Integer pageSize,
      QueryPrintTemplateVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<PrintTemplate> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<PrintTemplate> query(QueryPrintTemplateVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PrintTemplate findById(Integer id) {
    return getById(id);
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "新增打印模板，名称：{}", params = {"#vo.name"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Integer create(CreatePrintTemplateVo vo) {
    Wrapper<PrintTemplate> checkNameWrapper = Wrappers.lambdaQuery(PrintTemplate.class)
        .eq(PrintTemplate::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    PrintTemplate data = new PrintTemplate();
    data.setName(vo.getName());

    getBaseMapper().insert(data);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改打印模板，ID：{}，名称：{}", params = {
      "#vo.id", "#vo.name"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdatePrintTemplateVo vo) {
    Wrapper<PrintTemplate> checkNameWrapper = Wrappers.lambdaQuery(PrintTemplate.class)
        .eq(PrintTemplate::getName, vo.getName()).ne(PrintTemplate::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    PrintTemplate data = getById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("打印模板不存在！");
    }

    Wrapper<PrintTemplate> updateWrapper = Wrappers.lambdaUpdate(PrintTemplate.class)
        .eq(PrintTemplate::getId, vo.getId()).set(PrintTemplate::getName, vo.getName());

    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改打印模板设置，ID：{}", params = {
      "#vo.id"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateSetting(UpdatePrintTemplateSettingVo vo) {
    PrintTemplate data = getById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("打印模板不存在！");
    }

    Wrapper<PrintTemplate> updateWrapper = Wrappers.lambdaUpdate(PrintTemplate.class)
        .eq(PrintTemplate::getId, vo.getId())
        .set(PrintTemplate::getTemplateJson, vo.getTemplateJson());

    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = BaseDataOpLogType.BASE_DATA, name = "修改打印模板示例数据，ID：{}", params = {
      "#vo.id"}, autoSaveParams = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateDemoData(UpdatePrintTemplateDemoDataVo vo) {
    PrintTemplate data = getById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("打印模板不存在！");
    }

    Wrapper<PrintTemplate> updateWrapper = Wrappers.lambdaUpdate(PrintTemplate.class)
        .eq(PrintTemplate::getId, vo.getId())
        .set(PrintTemplate::getDemoData, vo.getDemoData());

    getBaseMapper().update(updateWrapper);
  }

  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
