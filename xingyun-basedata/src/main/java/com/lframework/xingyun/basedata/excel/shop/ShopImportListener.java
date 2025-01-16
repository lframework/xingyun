package com.lframework.xingyun.basedata.excel.shop;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.excel.ExcelImportListener;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.xingyun.template.inner.service.system.SysDeptService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShopImportListener extends ExcelImportListener<ShopImportModel> {

  private List<String> checkList = new ArrayList<>();

  @Override
  protected void doInvoke(ShopImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex()
              + "行“编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (checkList.contains(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”与第" + (checkList.indexOf(data.getCode()) + 1) + "行重复");
    }
    checkList.add(data.getCode());
    Wrapper<Shop> checkWrapper = Wrappers.lambdaQuery(Shop.class)
        .eq(Shop::getCode, data.getCode());
    ShopService shopService = ApplicationUtil.getBean(ShopService.class);
    if (shopService.count(checkWrapper) > 0) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”已存在");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }
    if (!StringUtil.isBlank(data.getDeptCode())) {
      SysDeptService deptService = ApplicationUtil.getBean(SysDeptService.class);
      SysDept dept = deptService.findByCode(data.getDeptCode());
      if (dept == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“所属部门编号”不存在");
      }
      data.setDeptId(dept.getId());
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    ShopService shopService = ApplicationUtil.getBean(ShopService.class);

    List<ShopImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ShopImportModel data = datas.get(i);

      Shop record = new Shop();

      record.setId(IdUtil.getId());

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setDeptId(data.getDeptId());
      record.setDescription(
          StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());

      record.setAvailable(Boolean.TRUE);

      shopService.save(record);

      data.setId(record.getId());

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
  }
}
